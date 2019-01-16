import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class a2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int nCase = 1;
		int n = sc.nextInt();
		sc.nextLine();
		String[] s;
		String name;
		while(n != 0) {
			TreeMap<String, Integer> tm = new TreeMap<String, Integer>();
			for(int i = 0; i < n; i++) {
				s = sc.nextLine().split(" ");
				name = s[s.length - 1].toLowerCase();
				if(tm.containsKey(name)) {
					int tmp = tm.get(name);
					tm.put(name, tmp + 1);
				}else {
					tm.put(name, 1);
				}
			}
			System.out.println("List " + nCase + ":");
			for(Entry<String, Integer> entry : tm.entrySet()) {
		     	System.out.println(entry.getKey() + " | " + entry.getValue());
			}
			n = sc.nextInt();
			sc.nextLine();
			nCase++;
		}
		
		sc.close();
	}

}
