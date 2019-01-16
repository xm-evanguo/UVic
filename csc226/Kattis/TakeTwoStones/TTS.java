import java.util.Scanner;

/**
 * Evan Guo
 * @ Uvic CSc
 */

public class TTS {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		if(n % 2 == 0) {
			System.out.println("Bob");
		}else {
			System.out.println("Alice");
		}
	}

}
