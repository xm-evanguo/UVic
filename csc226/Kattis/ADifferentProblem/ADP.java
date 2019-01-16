import java.math.BigInteger;
import java.util.Scanner;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class ADP {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			int x1 = -1;
			int x2 = -1;
			BigInteger y1 = BigInteger.valueOf(-1);
			BigInteger y2 = BigInteger.valueOf(-1);
			for(int i = 0; i < 2; i++) {
				if(sc.hasNextBigInteger()) {
					if(y1.equals(BigInteger.valueOf(-1))) {
						y1 = sc.nextBigInteger();
					}else {
						y2 = sc.nextBigInteger();
					}
				}else {
					if(x1 == -1) {
						x1 = sc.nextInt();
					}else {
						x2 = sc.nextInt();
					}
				}	
			}
			if(x1 != -1 && x2 != -1) {
				System.out.println(Math.max(x1, x2) - Math.min(x1, x2));
			}else if(x1 != -1) {
				System.out.println(y1.subtract(BigInteger.valueOf(x1)));
			}else {
				System.out.println(y1.max(y2).subtract(y1.min(y2)));
			}
		}
		sc.close();
	}

}
