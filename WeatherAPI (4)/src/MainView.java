import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainView extends JFrame {

	// 패널 (중복)
	JPanel panel;

	// 콤보박스
	static JComboBox Combo1; // 콤보박스 (시)
	static JComboBox Combo2; // 콤보박스 (군)
	static JComboBox Combo3; // 콤보박스 (동)

	// 선택된 지역 주소 저장
	static String name1 = null; // 콤보박스 (시)
	static String name2 = null; // 콤보박스 (군)
	static String name3 = null; // 콤보박스 (동)

	// 다음 버튼
	JButton btnNext;

//-----------------------(WeatherNcst)-------------------------//
	// 프레임
	JFrame weather;

	// 버튼
	JButton lbBak; // 날씨 버튼 (뒤로가기)
	JButton lbEnd; // 날씨 버튼 (닫기)

	// 이미지
	ImageIcon icn; // 아이콘
	
	// 레이블
	JLabel lbIcn; // 아이콘
	JLabel lbT1h; // 온도
	JLabel lbPty; // 현재 강수량
	JLabel lbRn1; // 1시간 강수량
	JLabel lbReh; // 습도
	JLabel lbMxy; // 지역

	// X , Y 좌표
	static String nx; // X 좌표
	static String ny; // Y 좌표

	// 날씨
	String PTY = null;// 강수상태
	String RN1 = null;// 1시간 강수량
	String REH = null;// 습도
	String T1H = null;// 기온

//-----------------------------------------------------------//	

	public MainView() throws Exception {

		super("기상예보");
//		DBSelector DBS = DBSelector.getinstanse();
		setBounds(500, 300, 500, 200);

		// 패널 생성
		panel = new JPanel();
		panel.setLayout(null);

		// 콤보 생성
//		DBSelector DBS = new DBSelector();
//		DBS.DBConnector();

		// 버튼 생성
		btnNext = new JButton("1번");
//		btnNext.addActionListener(this);

		// 지역
		Combo1 = new JComboBox(); // 시
		Combo2 = new JComboBox(); // 군
		Combo3 = new JComboBox(); // 동

		// 위치,사이즈
		// 지역
		Combo1.setBounds(70, 40, 100, 20);
		Combo2.setBounds(190, 40, 100, 20);
		Combo3.setBounds(310, 40, 100, 20);
		// 다음 버튼
		btnNext.setBounds(190, 100, 100, 30);
		// 배경색
		Color col = new Color(128, 255, 255);

		// 콤보박스 초기값 설정
//		Combo1.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel1().toArray()));
//		Combo1.setSelectedIndex(10);
//		name1 = "대구광역시";
//		Combo2.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel2(name1).toArray()));
//		Combo3.setModel(new DefaultComboBoxModel<>());

		// 실행
		add(panel);
		panel.setBackground(col);
		panel.add(btnNext);
		panel.add(Combo1);
		panel.add(Combo2);
		panel.add(Combo3);

		// 종료 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		//~=============================(액션)==============================~//
		Combo1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("콤보박스 1 동작");
				String a = (String) Combo1.getSelectedItem();
				name1 = a;
				System.out.println("name1 = " + name1);
				try {
					Combo2.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel2(name1).toArray()));
					Combo3.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel3(name1, name2).toArray()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				name2 = "";
				name3 = "";

			}

		});
		Combo2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("콤보박스 2 동작");
				String a = (String) Combo2.getSelectedItem();
				name2 = a;
				System.out.println("name2 = " + name2);
				if (a.equals("") || a == null) {
					System.out.println("lvl2 if 동작");
					System.out.println("a = " + a);
					try {
//						Combo3.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel3(name1, name2).toArray()));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					name2 = "";
					name3 = "";
				} else {
					System.out.println("lvl2 else 동작");
					System.out.println("a = " + a);
					try {
						Combo3.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel3(name1, name2).toArray()));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}

		});
		Combo3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String a = (String) Combo3.getSelectedItem();
				System.out.println("콤보박스 3 동작");
				name3 = a;
				System.out.println("name3 = " + name3);

			}

		});

		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WeatherNcst();

			}
		});

	}

	public void WeatherNcst() {
		// 추가 함수
		String a, b, c;
		a = name1;
		b = name2;
		c = name3;

		// 생성
		weather = new JFrame("현제 날씨"); // 날씨 창 프레임
		panel = new JPanel(); // 날씨 창 패널
		lbBak = new JButton("뒤로가기"); // 뒤로가기 버튼
		lbEnd = new JButton("닫기"); // 닫기 버튼
		lbIcn = new JLabel(); // 날씨 이미지
		lbMxy = new JLabel(); // 지역
		lbT1h = new JLabel(); // 기온
		lbPty = new JLabel(); // 강수량
		lbReh = new JLabel(); // 습도

		// 실행 코드
		// X Y 좌표 값
//		if(name1!=null) {
//			try {
//				DBS.getcoordinate(name1, name2, name3);
//				this.nx=DBSelector.NX;
//				this.ny=DBSelector.NY;
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//	}
//			//API에 좌표값 입력 코드
//
//		ApiExplorer weatherAPI=new ApiExplorer();
//		try {
//			weatherAPI.ApiExplorer(nx, ny);
//			PTY=weatherAPI.PTY;//강수상태
//			RN1=weatherAPI.RN1;//1시간 강수량
//			REH=weatherAPI.REH;//습도
//			T1H=weatherAPI.T1H;//기온
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//
//		// 날씨 코드
//		if (PTY.equals("0")) {
//			icn = new ImageIcon("0.png");
//		} else if (PTY.equals("1")) {
//			icn = new ImageIcon("1.png");
//		} else if (PTY.equals("2")) {
//			icn = new ImageIcon("2.png");
//		} else if (PTY.equals("3")) {
//			icn = new ImageIcon("3.png");
//		} else if (PTY.equals("4")) {
//			icn = new ImageIcon("4.png");
//		} else if (PTY.equals("5")) {
//			icn = new ImageIcon("5.png");
//		} else if (PTY.equals("6")) {
//			icn = new ImageIcon("6.png");
//		} else if (PTY.equals("7")) {
//			icn = new ImageIcon("7.png");
//		}
//		Image sunIcon = icn.getImage();

		// 지역 코드
		if (name2 == "") {
			b = "";
			c = "";
		} else if (b == null) {
			b = "";
		} else {
			b = name2;
		}

		if (name3 == "") {
			c = "";
		} else if (c == null) {
			c = "";
		} else {
			c = name3;
		}
		
	//Text
		//지역
		lbMxy.setText(a + " " + b + " " + c);
		// 기온
		lbT1h.setText("기온:" + T1H + " ℃");
		// 강수량
		lbPty.setText("강수량:" + RN1 + " mm");
		// 습도
		lbReh.setText("습도:" + REH + " %");

		// 위치,사이즈
		weather.setBounds(700, 200, 400, 550);
		lbBak.setBounds(80, 400, 100, 30);
		lbEnd.setBounds(210, 400, 100, 30);
		lbMxy.setBounds(52, -120, 300, 300);
		lbT1h.setBounds(100, 80, 200, 300);
		lbPty.setBounds(100, 160, 200, 300);
		lbReh.setBounds(100, 120, 200, 300);
		lbIcn.setBounds(150, 20, 200, 200);
		// 폰트
		lbMxy.setFont(new Font("NanumGothic", Font.BOLD, 20));
		lbT1h.setFont(new Font("NanumGothic", Font.BOLD, 30));
		lbPty.setFont(new Font("NanumGothic", Font.BOLD, 30));
		lbReh.setFont(new Font("NanumGothic", Font.BOLD, 30));
//		Image PxImge = sunIcon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		// 가운대 정렬
		lbMxy.setHorizontalAlignment(SwingConstants.CENTER);
		lbT1h.setHorizontalAlignment(SwingConstants.CENTER);
		lbPty.setHorizontalAlignment(SwingConstants.CENTER);
		lbReh.setHorizontalAlignment(SwingConstants.CENTER);

		// 실행
		weather.add(panel);
		weather.add(lbBak);
		weather.add(lbEnd);
		weather.add(lbIcn);
		weather.add(lbT1h, BorderLayout.CENTER);
		weather.add(lbPty, BorderLayout.CENTER);
		weather.add(lbReh, BorderLayout.CENTER);
		weather.add(lbMxy, BorderLayout.CENTER);
//		ImageIcon pxIcon = new  ImageIcon(PxImge);
//		lbIcn.setIcon(pxIcon);
		weather.add(lbIcn);
		
		// 종료 설정
		weather.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		weather.setVisible(true);
		
		// 날씨 창 ( 닫기, 뒤로가기) 이벤트	
	//~=============================(액션)==============================~//
		lbEnd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);

			}
		});
		lbBak.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setVisible(true);
				weather.setVisible(false); // 창 안보이게 하기

			}
		});

	}

	public static void main(String[] args) throws Exception {
		new MainView();
	}

}