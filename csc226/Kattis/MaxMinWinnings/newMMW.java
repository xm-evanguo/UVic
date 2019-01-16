import java.util.Arrays;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class newMMW {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int nRooms;
		int[][] list;
		int[] max;
		int[] min;
		int[] tmpMax;
		int[] tmpMin;
		int[] realMax;
		int[] realMin;
		int nTimes;
		int nMax;
		int nMin;
		
		nRooms = io.getInt();
		while(nRooms != 0) {
			list = new int[nRooms][nRooms];
			max = new int[nRooms];
			min = new int[nRooms];
			tmpMax = new int[nRooms];
			tmpMin = new int[nRooms];
			realMax = new int[nRooms];
			realMin = new int[nRooms];
			for(int i = 0; i < nRooms; i++) {
				for(int j = 0; j < nRooms; j++) {
					list[i][j] = io.getInt();
					if(i == 0) {
						realMax[j] = list[0][j];
						realMin[j] = list[0][j];
					}
				}
			}
			nTimes = io.getInt();
			for(int i = 1; i < nTimes; i++) {
				for(int j = 0; j < nRooms; j++) {
					for(int k = 0; k < nRooms; k++) {
						tmpMax[k] = list[k][j] + realMax[k];
						tmpMin[k] = list[k][j] + realMin[k];
						nMax = getMax(tmpMax);
						nMin = getMin(tmpMin);
						max[j] = nMax;
						min[j] = nMin;
					}
				}
				for(int j = 0; j < nRooms; j++) {
					realMax[j] = max[j];
					realMin[j] = min[j];
				}
				
			}
			nMax = getMax(realMax);
			nMin = getMin(realMin);
			io.println(nMax + " " + nMin);
			nRooms = io.getInt();
		}
		io.close();
	}

	public static int getMax(int[] tmp) {
		int[] a = tmp.clone();
		Arrays.sort(a);
		return a[a.length - 1];
	}
	
	public static int getMin(int[] tmp) {
		int[] a = tmp.clone();
		Arrays.sort(a);
		return a[0];
	}
}
