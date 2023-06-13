package Java내부평가시험;

import java.util.Scanner;

public class 평가_서술형_네트워크프로그래밍_구현 {
	public static void main(String[] args) {
		int a, b, c, d;
		Scanner st = new Scanner(System.in);
		System.out.printf("높이 입력 : ");
		a = st.nextInt();
		
		//페이지
		for(b=0;b<a+1;b++){
			//공맥
			for(c=0;c<a-b;c++){
				System.out.print(" "); 
			}
			//별
			for(d=0;d<2*b-1;d++){
				System.out.print("*"); 
			}
			System.out.println("");
		} 
	}
}
