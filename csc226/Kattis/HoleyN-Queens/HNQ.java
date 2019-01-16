/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class HNQ {
	
	private static int count;
	private static boolean[] row = new boolean[132];
	private static boolean[] rowPlus = new boolean[132];
	private static boolean[] rowMinus = new boolean[132];
	private static int[] place = new int[132];
	private static int size;
	private static boolean[][] hole = new boolean[132][132];
	private static int node;
	
	public static void main(String[] args) {
		int nHole;
		Kattio io = new Kattio(System.in);
		size = io.getInt();
		node = 0;
		while(size != 0) {
			for(int i = 0; i < 132; i++) {
				for(int j = 0; j < 132; j++) {
					hole[i][j] = false;
				}
				row[i] = true;
				rowPlus[i] = true;
				rowMinus[i] = true;
			}
			nHole = io.getInt();
			for(int i = 0; i < nHole; i ++) {
				hole[io.getInt()][io.getInt()] = true;
			}
			find(0);
			System.out.println(count + " " + node);
			count = 0;
			size = io.getInt();
		}
		io.close();
	}
	
	private static void find(int col) {
		for(int x = 0; x < size; x++) {
			if(row[x] && rowPlus[x+col] && rowMinus[x-col+size] && !hole[x][col]) {
				node++;
				place[col] = x;
				row[x] = false;
				rowPlus[x+col] = false;
				rowMinus[x-col+size] = false;
				if(col == size - 1) {
					solution();
				}else {
					find(col + 1);
				}
				row[x] = true;
				rowPlus[x+col] = true;
				rowMinus[x - col + size] = true;
			}
		}
		
	}
	
	private static void solution() {
		boolean oneZero = false;
		for(int i = 0; i < size; i++) {
			if(place[i] == 0) {
				if(oneZero) {
					break;
				}else {
					oneZero = true;
				}
			}
			if(i == size - 1) {
				count++;
			}
		}
	}

}
