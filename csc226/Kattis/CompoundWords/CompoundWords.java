import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class CompoundWords {

	public static void main(String[] args) {
		String tmp;
		int count;
		LinkedList<String> list = new LinkedList<String>();
		Scanner sc = new Scanner(System.in);
		PriorityQueue<String> pq = new PriorityQueue<String>();
		while(sc.hasNext()) {
			list.add(sc.next());
		}
		count = list.size();
		for(int i = 0; i < count; i++) {
			for(int j = 0; j < count; j++) {
				if(i != j) {
					tmp = list.get(i) + list.get(j);
					pq.add(tmp);
				}
			}
		}
		while(!pq.isEmpty()) {
			tmp = pq.poll();
			while(tmp.equals(pq.peek())) {
				pq.poll();
			}
			System.out.println(tmp);
		}
		sc.close();
	}

}
