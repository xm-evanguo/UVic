import java.util.Scanner;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class midterm2A2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int nTeam = sc.nextInt();
		int nD = sc.nextInt();
		int nR = sc.nextInt();
		int count = 0;
		int[] list = new int[nTeam + 1];
		if(nR == 0) {
			System.out.println(nD);
			return;
		}
		if(nD == 0) {
			System.out.println("0");
			return;
		}
		for(int i = 0; i < nD; i++) {
			int tmp = sc.nextInt();
			list[tmp] = list[tmp] - 1;
		}
		for(int i = 0; i < nR; i++) {
			int tmp = sc.nextInt();
			list[tmp] = list[tmp] + 1;
		}
		for(int i = 1; i < nTeam + 1; i++) {
			if(list[i] == 1) {
				if(i != 1 && list[i - 1] == -1) {
					list[i - 1] = 0;
					list[i] = 0;
				}else if(i != nTeam && list[i + 1] == -1) {
					list[i + 1] = 0;
					list[i] = 0;
				}
			}
		}
		for(int i = 1; i < nTeam + 1; i++) {
			if(list[i] <= -1) {
				count++;
			}
		}
		System.out.println(count);
		sc.close();
	}

}
