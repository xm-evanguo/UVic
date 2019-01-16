import java.util.*;
public class ScannerTester {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i<5; i++) {
			int k = sc.nextInt();
			System.out.print(k + " ");

		}
		System.out.print("the end");
		sc.close();
	}
}
