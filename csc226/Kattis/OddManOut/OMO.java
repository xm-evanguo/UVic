import java.util.PriorityQueue;
import java.util.Scanner;

public class OMO {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int nCase = sc.nextInt();
		int nGuest;
		int tmp;
		PriorityQueue<Integer> pq;
		
		for(int i = 1; i < nCase + 1; i++) {
			nGuest = sc.nextInt();
			pq = new PriorityQueue<Integer>();
			pq.add(sc.nextInt());
			for(int j = 1; j < nGuest; j++) {
				tmp = sc.nextInt();
				if(pq.contains(tmp)) {
					pq.remove(tmp);
				}else {
					pq.add(tmp);
				}
			}
			System.out.println("Case #" + i + ": " + pq.poll());
		}

	}

}
