import java.util.Scanner;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class newCG {
	
	private static int min;
	private static int[] coloring;
	private static int[][] mapping;
	private static int nV;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		nV = sc.nextInt();
		mapping = new int [nV][nV];
		sc.nextLine();
		min = nV;
		for(int i = 0; i < nV; i++) {
			String[] tmp = sc.nextLine().split(" ");
			for(int j = 0; j < tmp.length; j++) {
				mapping[i][Integer.parseInt(tmp[j])] = 1;
			}
		}
		for(int j = 0; j < nV; j++) {
			for(int i = 2; i <= min; i++) {
				coloring = new int[nV];
				coloring[j] = 1;	
				if(color(0, i)) {
					for(int k = 0; k < nV; k++) {
						System.out.print(coloring[k] + " ");
					}
					System.out.println();
					if(i < min)
						min = i;
					break;
				}
			}
		}
		
		System.out.println(min);
		sc.close();
	}
	
	private static boolean color(int n, int k) {
		if(n == nV) 
			return true;
		for(int i = 0; i < nV; i++) {
			if(mapping[n][i] == 1) {
				if(coloring[n] == coloring[i]) {
					coloring[i]++;
					if(coloring[i] > k - 1) {
						return false;
					}
				}
			}
		}
		return color(n+1, k);
	}
	
}
