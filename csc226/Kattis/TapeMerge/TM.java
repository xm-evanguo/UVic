import java.util.PriorityQueue;
import java.util.Scanner;

public class TM {
	
	public static void main(String[] arg) {
		Scanner sc = new Scanner(System.in);
		int nTape = sc.nextInt();
		while(nTape != 0) {
			PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
			for(int i = 0; i < nTape; i++) {
				pq.add(sc.nextInt());
			}
			long count = 0;
			while(pq.size() != 1) {
				int x = pq.poll();
				int y = pq.poll();
				count = x + y - 1 + count;
				pq.add(x+y);
			}
			System.out.println(count);
			nTape = sc.nextInt();
		}
		sc.close();
	}
	
}
