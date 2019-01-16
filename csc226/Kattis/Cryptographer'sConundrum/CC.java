import java.util.Scanner;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class CC {

	public static void main(String[] args) {
		int count = 0;
		Scanner sc = new Scanner(System.in);
		String s = sc.next();
		
		for(int i = 0; i < s.length(); i++) {
			if(i % 3 == 0) {
				if(s.charAt(i) != 'P') {
					count++;
				}
			}else if(i % 3 == 1) {
				if(s.charAt(i) != 'E') {
					count++;
				}
			}else {
				if(s.charAt(i) != 'R') {
					count++;
				}
			}
		}
		System.out.println(count);
		sc.close();
	}

}
