import java.util.Collections;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class BF {

	public static void main(String[] args) {
		TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		for(int i = 1; i <= n; i++) {
			int tmp = sc.nextInt();
			if(!tm.containsKey(tmp)) {
				tm.put(tmp, i);
			}else {
				tm.replace(tmp, 0);
			}
		}
		for(Entry<Integer, Integer> entry: tm.entrySet()) {
		     if(entry.getValue() != 0) {
		    	 System.out.println(entry.getValue());
		    	 return;
		     }
		}
		System.out.println("none");
		sc.close();
	}

}
