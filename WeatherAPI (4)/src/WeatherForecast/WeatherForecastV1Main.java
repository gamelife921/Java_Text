//pstmt.close();
//conn.close();
package WeatherForecast;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.mysql.cj.x.protobuf.MysqlxConnection.Close;

class DBSelector{
	// DB정보
	static String id = "root";
	static String pw = "1234";
	static String url = "jdbc:mysql://localhost:3306/weather";
	
	// JDBC참조변수
	static Connection DBcon = null; // DB연결용 참조변수
	static PreparedStatement DBprepar = null; // SQL쿼리 전송용 참조변수
	static ResultSet DBresult = null; // SQL쿼리 결과(SELECT결과) 수신용 참조변수
	

	
	//주소 검색 결과를 저장하는 리스트
	static Set<String> set1 = null;
	static Set<String> set2 = null;
	static Set<String> set3 = null;
	
	//좌표값을 저장하는 변수
	static String NX;
	static String NY;
	
	//데이터 베이스 연결
	public static void DBConnector() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver"); // 드라이브 적재
		System.out.println("Driver Loading Success..");
		DBcon = DriverManager.getConnection(url, id, pw);
		System.out.println("DB Connected..");
	}
	
	public static void DBclose() throws SQLException {
		DBprepar.close();
		DBresult.close();
	}
	
	//무슨 시 인지 검색
	public static Set<String> selectNamesLevel1() throws SQLException {

		DBprepar = DBcon.prepareStatement("select 1단계 from tbl_api group by 1단계");
		DBresult = DBprepar.executeQuery();
		set1 = new HashSet();
		if (DBresult != null) {
			while (DBresult.next()) {
				set1.add(DBresult.getString("1단계"));
			}
			System.out.println(set1);
			DBclose();
		}
		return set1;
	}
	
	
	//무슨 구 인지 검색
	public static Set<String> selectNamesLevel2(String a) throws SQLException {
		set2 = new HashSet();
		if (a != null&&!a.equals("")) {
			DBprepar = DBcon.prepareStatement("select 2단계 from tbl_api where 1단계 =?;");
			DBprepar.setString(1, a);
			DBresult = DBprepar.executeQuery();

			if (DBresult != null) {
				while (DBresult.next()) {
					set2.add(DBresult.getString("2단계"));
				}
				DBclose();
				System.out.println(set2);
			}
			return set2;
		} else {
			DBclose();
			set2.add("");
			return set2;
		}
	}
	
	
	//무슨 동 인지 검색
	public static Set<String> selectNamesLevel3(String a,String b) throws SQLException {
		set3 = new HashSet();
		if (a != null&&!a.equals("")) {
			DBprepar = DBcon.prepareStatement("select 3단계 from tbl_api where 1단계 =? and 2단계 = ?;");
			DBprepar.setString(1, a);
			DBprepar.setString(2, b);
			DBresult = DBprepar.executeQuery();

			if (DBresult != null) {
				while (DBresult.next()) {
					set3.add(DBresult.getString("3단계"));
				}
				System.out.println(set3);
				DBclose();
			}
			return set3;
		} else {
			DBclose();
			set3.add("");
			return set3;
		}

	}
	//좌표값 생성기
	public static void getcoordinate(String a,String b,String c) throws SQLException {
		DBprepar = DBcon.prepareStatement("select * from tbl_api where 1단계 =? and 2단계=? and 3단계 =?");
		DBprepar.setString(1, a);
		if(b!=null&&!b.equals("")) {
		DBprepar.setString(2, b);	
		}else {
		DBprepar.setString(2, "");
		}
		if(c!=null&&!c.equals("")) {
		DBprepar.setString(3, c);
		}else {
		DBprepar.setString(3, "");
		}
		DBresult = DBprepar.executeQuery();
		if(DBresult != null) {
			DBresult.next();
				NX=DBresult.getString("격자 X");
				NY=DBresult.getString("격자 Y");
				System.out.println(NX);
				System.out.println(NY);
				DBclose();
				
		}
		
	}
}



class MainView extends JFrame {
	private static final DBSelector DBS = null;

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

	MainView() throws Exception {
		super("기상예보");
		setBounds(500, 300, 500, 200);

		
		// 패널
		panel = new JPanel();
		panel.setLayout(null);

			
		//콤버 박스 생성 
		DBSelector DBS = new DBSelector();
		DBS.DBConnector();

		// 버튼 생성
		btnNext = new JButton("1번");
		
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
		
		//초기값 설정
		Combo1.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel1().toArray()));
		Combo1.setSelectedIndex(10);
		name1 = "대구광역시";
		Combo2.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel2(name1).toArray()));
		Combo3.setModel(new DefaultComboBoxModel<>());

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

		//=========================액션===================================
		Combo1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("콤보박스 1 동작");
				String a = (String) Combo1.getSelectedItem();
				name1 = a;
				System.out.println("name1 = "+name1);
				try {
					Combo2.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel2(name1).toArray()));
					Combo3.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel3(name1,name2).toArray()));
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
				// TODO Auto-generated method stub
				System.out.println("콤보박스 2 동작");
				String a = (String) Combo2.getSelectedItem();
				name2 = a;
				System.out.println("name2 = "+name2);
				if(a.equals("")||a==null) {
					System.out.println("lvl2 if 동작");
					System.out.println("a = "+a);
					try {
						Combo3.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel3(name1,name2).toArray()));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					name2 = "";
					name3 = "";
				}else {
					System.out.println("lvl2 else 동작");
					System.out.println("a = "+a);
				try {
					Combo3.setModel(new DefaultComboBoxModel<>(DBS.selectNamesLevel3(name1,name2).toArray()));
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
				// TODO Auto-generated method stub
				String a = (String) Combo3.getSelectedItem();
				System.out.println("콤보박스 3 동작");
				name3 = a;
				System.out.println("name3 = "+name3);
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
		if(name1!=null) {
			try {
				DBS.getcoordinate(name1, name2, name3);
				this.nx=DBSelector.NX;
				this.ny=DBSelector.NY;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
			//API에 좌표값 입력 코드

		ApiExplorer weatherAPI=new ApiExplorer();
		try {
			weatherAPI.ApiExplorer(nx, ny);
			PTY=weatherAPI.PTY;//강수상태
			RN1=weatherAPI.RN1;//1시간 강수량
			REH=weatherAPI.REH;//습도
			T1H=weatherAPI.T1H;//기온
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		
		// 날씨 코드
		if (PTY.equals("0")) {
			icn = new ImageIcon("0.png");
		} else if (PTY.equals("1")) {
			icn = new ImageIcon("1.png");
		} else if (PTY.equals("2")) {
			icn = new ImageIcon("2.png");
		} else if (PTY.equals("3")) {
			icn = new ImageIcon("3.png");
		} else if (PTY.equals("4")) {
			icn = new ImageIcon("4.png");
		} else if (PTY.equals("5")) {
			icn = new ImageIcon("5.png");
		} else if (PTY.equals("6")) {
			icn = new ImageIcon("6.png");
		} else if (PTY.equals("7")) {
			icn = new ImageIcon("7.png");
		}
		
		Image sunIcon = icn.getImage();
//		Image PxImge = sunIcon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//		ImageIcon pxIcon = new  ImageIcon(PxImge);
//		lbIcn.setIcon(pxIcon);
//		lbIcn.setBounds(150, 10, 200, 200);
//		weather.add(lbIcn);
		
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
		lbIcn.setBounds(150, -100, 200, 200);
		// 폰트
		lbMxy.setFont(new Font("NanumGothic", Font.BOLD, 20));
		lbT1h.setFont(new Font("NanumGothic", Font.BOLD, 30));
		lbPty.setFont(new Font("NanumGothic", Font.BOLD, 30));
		lbReh.setFont(new Font("NanumGothic", Font.BOLD, 30));
		Image PxImge = sunIcon.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		
		// 가운대 정렬
		lbMxy.setHorizontalAlignment(SwingConstants.CENTER);
		lbT1h.setHorizontalAlignment(SwingConstants.CENTER);
		lbPty.setHorizontalAlignment(SwingConstants.CENTER);
		lbReh.setHorizontalAlignment(SwingConstants.CENTER);
		lbIcn.setHorizontalAlignment(SwingConstants.CENTER);
		
		// 실행
		weather.add(panel);
		weather.add(lbBak);
		weather.add(lbEnd);
		weather.add(lbT1h, BorderLayout.CENTER);
		weather.add(lbPty, BorderLayout.CENTER);
		weather.add(lbReh, BorderLayout.CENTER);
		weather.add(lbMxy, BorderLayout.CENTER);
		ImageIcon pxIcon = new  ImageIcon(PxImge);
		lbIcn.setIcon(pxIcon);
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

}

public class WeatherForecastV1Main {

	public static void main(String[] args) throws Exception {
		
		
		new MainView();

	}

}