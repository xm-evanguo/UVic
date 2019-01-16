/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class MMW {

	public static void main(String[] args) {
		int nRooms;
		int[][] onceMoney;
		int nTimes;
		int[][] twiceMoney;
		int onceMax;
		int twiceMax;
		int max;
		int[] maxXY;
		int onceMin;
		int twiceMin;
		int secondMax;
		int secondMin;
		int min;
		int[] minXY;
		int[] twiceMaxXY;
		int[] twiceMinXY;
		
		Kattio io = new Kattio(System.in);
		nRooms = io.getInt();
		while(nRooms != 0) {
			maxXY = new int[2];
			minXY = new int[2];
			onceMax = 0;
			onceMin = 2000;
			secondMax = 0;
			secondMin = 2000;
			max = 0;
			min = 2000;
			onceMoney = new int[nRooms][nRooms];
			for(int i = 0; i < nRooms; i++) {
				for(int j = 0; j < nRooms; j++) {
					onceMoney[i][j] = io.getInt();
					if(onceMoney[i][j] > max) {
						max = onceMoney[i][j];
						maxXY[0] = i;
						maxXY[1] = j;
					}
					if(onceMoney[i][j] < min) {
						min = onceMoney[i][j];
						minXY[0] = i;
						minXY[1] = j;
					}
					if(onceMoney[i][j] > onceMax && i == 0)
						onceMax = onceMoney[i][j];
					if(onceMoney[i][j] < onceMin && i == 0)
						onceMin = onceMoney[i][j];
				}
			}
			nTimes = io.getInt();
			if(nTimes == 1) {
				System.out.println(onceMax + " " + onceMin);
				nRooms = io.getInt();
				continue;
			}
			
			secondMax = 0;
			secondMin = 2000;
			int theX = maxXY[1];
			for(int j = 0; j < nRooms; j++) {
				if(onceMoney[theX][j] > secondMax) {
					secondMax = onceMoney[theX][j];
				}
			}
			theX = minXY[1];
			for(int j = 0; j < nRooms; j++) {
				if(onceMoney[theX][j] < secondMin) {
					secondMin = onceMoney[theX][j];
				}
			}
			
			twiceMaxXY = new int[2];
			twiceMinXY = new int[2];
			twiceMoney = new int[nRooms][nRooms];
			twiceMax = 0;
			twiceMin = 2000;
			for(int i = 0; i < nRooms; i++) {
				for(int j = 0; j < nRooms; j++) {
					twiceMoney[i][j] = onceMoney[0][i] + onceMoney[i][j];
					if(twiceMoney[i][j] > twiceMax) {
						twiceMax = twiceMoney[i][j];
						twiceMaxXY[0] = i;
						twiceMaxXY[1] = j;
					}
					if(twiceMoney[i][j] < twiceMin) {
						twiceMin = twiceMoney[i][j];
						twiceMinXY[0] = i;
						twiceMinXY[1] = j;
					}
				}
			}
			
			if(twiceMaxXY[0] != maxXY[1]) {
				
			}
			
			
			
			
			if(nTimes % 2 == 0) {
				max = twiceMax * (nTimes/2);
				min = twiceMin * (nTimes/2);
			}else {
				max = twiceMax * (nTimes/2) + secondMax;
				min = twiceMin * (nTimes/2) + secondMin;
			}
			System.out.println(max + " " + min);
			nRooms = io.getInt();
		}
		io.close();
	}
	
	
	

}
