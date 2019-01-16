import java.util.Scanner;

public class Bishops {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextInt()) {
			int tmp = sc.nextInt();
			if(tmp == 0) {
				System.out.println(0);
			}else if(tmp == 1){
				System.out.println(1);
			}else {
				System.out.println(tmp * 2 - 2);
			}
		}
		sc.close();
	}
}
