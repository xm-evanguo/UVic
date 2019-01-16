import java.util.Arrays;
import java.util.Scanner;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class midterm2a1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[] edge = sc.nextLine().split(" ");
		int[] list = new int[edge.length];
		for(int i = 0; i < edge.length; i ++) {
			list[i] = Integer.parseInt(edge[i]);
		}
		Arrays.sort(list);
		System.out.println(list[0] * list[2]);
	}

}
