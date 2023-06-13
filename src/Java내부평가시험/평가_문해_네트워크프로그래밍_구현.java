package Java내부평가시험;

//구매자
class Buyer {
	//속성
	private int MyMeso;
	private int TracesOfTheSpell;

	//생성자
	public Buyer(int myMoney, int tracesOfTheSpell) {
		super();
		//Buyer클래스를 myMoney,tracesOfTheSpell를 매개벼 변수로 초가화
		MyMeso = myMoney;
		TracesOfTheSpell = tracesOfTheSpell;
	}
	
	//기능
	public void Pay(Seller seller01, int monery) {
		//MyMeso 의 금액 차감
		MyMeso -= monery;
		
		//seller01 의 Receive(monery) 전달
		int cnt = seller01.Receive(monery);

		//리턴되는 주문의흔적 개수를 TracesOfTheSpell에 누적
		TracesOfTheSpell += cnt;
	}

	public void ShowInfo() {
		
		System.out.println("보유 메소 금액 : "+MyMeso);
		System.out.println("주문의흔적 개수 : "+TracesOfTheSpell);
		
	}
}

//판매자
class Seller {
	//속성
	private int MyMeso;
	private int TracesOfTheSpell;
	public int price;
	
	//생성자
	public Seller(int myMeso, int appleCnt, int price) {
		super();
		MyMeso = myMeso;
		TracesOfTheSpell = appleCnt;
		this.price = price;
	}
	
	//기능
	public int Receive(int myMeso) {
		//MyMeso의 금액 누적증가
		MyMeso += myMeso;
		
		//TracesOfTheSpell 에 받음 금액 만큼의 주문의흔적 차감
		int revcnt = myMeso / price;
		
		//주문의흔적 리턴
		TracesOfTheSpell -= revcnt;
		return revcnt;
	}

	public void ShowInfo() {
		System.out.println("보유 메소 금액 : "+MyMeso);
		System.out.println("주문의흔적 개수 : "+TracesOfTheSpell);
		System.out.println("개당 가격 : "+price);
	}
}

public class 평가_문해_네트워크프로그래밍_구현 {
	public static void main(String[] args) {
		
		//보유 메소 금액, 주문의흔적 개수 , 개당가격
		Seller seller = new Seller(100000,100,1000); 
		//유저이름 ,보유 메소 금액 , 주문의흔적 개수
		Buyer zl존히어로 = new Buyer(10000,0); 
		Buyer 타락한파워전사 = new Buyer(20000,0); 
		Buyer 번개의신vv = new Buyer(30000,0); 
		//유저 구매 할 금액
		zl존히어로.Pay(seller, 2000);
		타락한파워전사.Pay(seller, 5000);
		번개의신vv.Pay(seller, 3000);
		
		System.out.println("---경매자 정보----");
		seller.ShowInfo();
		
		System.out.println("---구매자 유저 정보----");
		System.out.println("-zl존히어로-");
		zl존히어로.ShowInfo();
		
		System.out.println("-타락한파워전사-");
		타락한파워전사.ShowInfo();
		
		System.out.println("-번개의신vv-");
		번개의신vv.ShowInfo();
	}
}
