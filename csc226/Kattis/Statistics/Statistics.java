import java.util.Arrays;
import java.util.Scanner;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class Statistics {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[] line;
		int[] array;
		int count = 1;
		int range;
		while(sc.hasNext()) {
			line = sc.nextLine().split(" ");
			array = new int[line.length - 1];
			for(int i = 1; i < line.length; i++) {
				array[i-1] = Integer.parseInt(line[i]);
			}
			Arrays.sort(array);
			
			range = array[array.length - 1] - array[0];
			System.out.print("Case " + count + ": " + array[0] + " " + array[array.length - 1]);
			System.out.println(" " + range);
			count++;
		}
		sc.close();
		
	}

}
