import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class a3 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int nInter = sc.nextInt();
		int nDung = sc.nextInt();
		DecimalFormat df = new DecimalFormat("0.0000");
		sc.nextLine();
		while(nInter != 0) {
			int[] from = new int[2 * nDung + 2];
			int[] to = new int[2 * nDung + 2];
			double[] distance = new double[2 * nDung + 2];
			double[] curSize = new double[nInter];
			for(int i = 0; i < nDung; i++) {
				String[] line = sc.nextLine().split(" ");
				from[i] = Integer.parseInt(line[0]);
				to[i + nDung] = from[i];
				to[i] = Integer.parseInt(line[1]);
				from[i + nDung] = to[i];
				distance[i] = Double.parseDouble((line[2]));
				distance[i + nDung] = distance[i];
			}
			curSize[0] = 1;
			for(int i = 0; i < nDung/10 + 1; i++) {
				for(int j = 0; j < nDung; j++) {
					if(curSize[to[j]] < curSize[from[j]] * distance[j]) {
						curSize[to[j]] = curSize[from[j]] * distance[j];
					}
				}
				for(int j = 0; j < nDung; j++) {
					if(curSize[to[j + nDung]] < curSize[from[j + nDung]] * distance[j]) {
						curSize[to[j + nDung]] = curSize[from[j + nDung]] * distance[j];
					}
				}
			}

			double tmp= curSize[nInter - 1];
			System.out.println(df.format(tmp));
			nInter = sc.nextInt();
			nDung = sc.nextInt();
			sc.nextLine();
		}
	}

}
