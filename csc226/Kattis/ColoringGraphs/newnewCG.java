import java.util.Scanner;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class newnewCG {
	
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
		for(int i = 2; i <= nV; i++) {
			coloring = new int[nV];
			coloring[0] = 1;
			if(color(1, i)) {
				min = i;
				break;
			}
		}
		System.out.println(min);
		sc.close();
	}
	
	private static boolean color(int cur, int max) {
		if(cur == nV) {
			return true;
		}
		
		for(int i = 1; i <= max; i++) {
			coloring[cur] = i;
			for(int j = 0; j < nV; j++) {
				if(mapping[cur][j] == 1) {
					if(coloring[cur] == coloring[j]) {
						coloring[cur] = 0;
						break;
					}
				}
			}
			
			if(coloring[cur] == 0)
				continue;
			
			if(color(cur+1, max)) {
				return true;
			}
			coloring[cur] = 0;
		}
		
		return false;
	}
	
}
