import java.util.HashMap;
import java.util.Scanner;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class FunctionalFun {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String [] domain;
		String [] codomain;
		String [] tmp;
		HashMap<String, String> mapping;
		while(sc.hasNext()) {
			boolean function = true;
			boolean surjective = true;
			boolean injective = true;
			mapping  = new HashMap<String, String>();
			domain = sc.nextLine().split(" ");
			codomain = sc.nextLine().split(" ");
			int n = sc.nextInt();
			sc.nextLine();
			for(int i = 0; i < n; i++) {
				tmp = sc.nextLine().split(" -> ");
				if(mapping.containsKey(tmp[0])) {
					function = false;
				}
				if(mapping.containsValue(tmp[1])) {
					injective = false;
				}
				mapping.put(tmp[0], tmp[1]);
			}
			for(int i = 1; i < codomain.length; i++) {
				if(!mapping.containsValue(codomain[i])){
					surjective = false;
					break;
				}
			}
			
			if(!function) {
				System.out.println("not a function");
			}else if(surjective && !injective) {
				System.out.println("surjective");
			}else if(!surjective && injective) {
				System.out.println("injective");
			}else if(surjective && injective) {
				System.out.println("bijective");
			}else {
				System.out.println("neither injective nor surjective");
			}
		}
		sc.close();
	}

}
