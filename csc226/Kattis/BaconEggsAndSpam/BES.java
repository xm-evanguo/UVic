import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

public class BES {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeMap<String, PriorityQueue<String>> tm;
		PriorityQueue<String> pq;
		String[] s;
		int nLines;
		nLines = sc.nextInt();
		sc.nextLine();
		while(nLines != 0) {
			tm = new TreeMap<String, PriorityQueue<String>>();
			for(int i = 0; i < nLines; i++) {
				s = sc.nextLine().split(" ");
				int length = s.length;
				for(;length > 1; length--) {
					if(tm.containsKey(s[length - 1])) {
						tm.get(s[length - 1]).add(s[0]);
					}else {
						pq = new PriorityQueue<String>();
						pq.add(s[0]);
						tm.put(s[length - 1], pq);
					}
				}
			}
			
			for(Entry<String, PriorityQueue<String>> entry: tm.entrySet()) {
			     System.out.print(entry.getKey());
			     while(!entry.getValue().isEmpty()) {
			    	 System.out.print(" " + entry.getValue().poll());
			     }
			     System.out.println();
			}
			
			System.out.println();
			nLines = sc.nextInt();
			sc.nextLine();
		}
		sc.close();
	}

}
