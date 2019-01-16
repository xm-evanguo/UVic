import java.util.Scanner;

public class Sibice {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int x = sc.nextInt();
		int y = sc.nextInt();
		int max = (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		for(int i = 0; i < n; i++) {
			if(sc.nextInt() <= max) {
				System.out.println("DA");
			}else {
				System.out.println("NE");
			}
		}
	}

}
