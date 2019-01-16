//V00866199 Guo, Evan

import java.util.LinkedList;
import java.util.Queue;

public class Kastenlauf {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int[][] xy;
		int numOfLocation;		
		boolean[] check;
		int tmp;
		Queue<Integer> q = new LinkedList<Integer>();
		int numOfCase = io.getInt();
		for(int i = 0; i < numOfCase; i++) {
			numOfLocation = io.getInt() + 2;
			xy = new int [numOfLocation][2];
			check = new boolean [numOfLocation];
			for(int j = 0; j < numOfLocation; j++) {
				xy[j][0] = io.getInt();
				xy[j][1] = io.getInt();
			}
			q.add(0);
			while(!q.isEmpty()) {
				tmp = q.remove();
				check[tmp] = true;
				for(int k = 0; k < numOfLocation; k++) {
					if(k != tmp && !check[k]) {
						if(Math.abs(xy[tmp][0] - xy[k][0]) + Math.abs(xy[tmp][1] - xy[k][1]) <= 1000) {
							q.add(k);
						}
					}
				}	
			}
			if(check[numOfLocation - 1]) {
				io.println("happy");
			}else {
				io.println("sad");
			}
		}
		io.close();
	}
}
