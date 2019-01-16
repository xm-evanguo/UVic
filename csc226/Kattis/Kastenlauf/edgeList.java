import java.util.PriorityQueue;

public class edgeList {
	
	edgeNode start;
	edgeNode rear;
	int n;
	
	public edgeList() {
		n = 0;
		start = null;
		rear = null;
	}
	
	public void edgeSort() {
		distance d = new distance();
		PriorityQueue<edgeNode> pq = new PriorityQueue<edgeNode>(n, d);
		while(n != 0) {
			pq.add(start);
			start.next = start;
			n--;
		}
		while(pq.peek() != null) {
			if(n == 0) {
				start = pq.poll();
				rear = start;
			}else {
				rear.next = pq.poll();
				rear = rear.next;
			}
			n++;
			rear.next = null;
		}
	}
}
