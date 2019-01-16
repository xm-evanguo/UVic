import java.util.Scanner;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class G {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int ntopping = sc.nextInt();
		int nLine = sc.nextInt();
		int count = 0;
		boolean[][] noMix = new boolean[ntopping + 1][ntopping + 1];
		for(int i = 0; i < nLine; i++) {
			noMix[sc.nextInt()][sc.nextInt()] = true;
		}
		for(int i = 0; i < ntopping + 1; i++) {
			for(int j = 0; j < ntopping + 1; j++) {
				if(!noMix[i][j]) {
					noMix[j][i] = true;
					count++;
				}
			}
		}
		for(int i = 0; i < ntopping + 1; i++) {
			for(int j = 0; j < ntopping + 1; j++) {
				System.out.print(noMix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println(count);
	}

}
