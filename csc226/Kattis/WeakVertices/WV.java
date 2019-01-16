/**
 * Name: Evan Guo
 * Student#: V00866199
 * Uvic CSC 226 Spring 2018
 */
public class WV {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int[][] list;
		boolean[] check;
		for(;;) {
			int n = io.getInt();
			if(n == -1)
				break;
			list = new int[n][n];
			check = new boolean[n];
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					list[i][j] = io.getInt();
				}
			}
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					for(int k = 0; k < n; k++) {
						if(list[i][j]==1 && list[j][k]==1 && list[k][i]==1) {
							check[i] = true;
							check[j] = true;
							check[k] = true;
						}
					}
				}
			}
			for(int i = 0; i < n; i++) {
				if(!check[i]) {
					io.print(i + " ");
				}
			}
			io.println();
		}
		io.close();
	}
}
