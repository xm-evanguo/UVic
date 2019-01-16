import java.util.LinkedList;
import java.util.PriorityQueue;

public class newBT {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int nVertices = io.getInt();
		int[] nItem = new int[nVertices];
		LinkedList<edge> list = new LinkedList<edge>();
		compare c = new compare();
		PriorityQueue<edge> pq = new PriorityQueue<edge>(c);
		edge tmp;
		for(int i = 0; i < nVertices; i++) {
			nItem[i] = io.getInt();
		}
		int n = io.getInt();
		for(int i = 0; i < n; i++) {
			tmp = new edge(io.getInt(), io.getInt(), io.getInt());
			pq.add(tmp);
		}
		while(!pq.isEmpty()) {
			list.add(pq.poll());
			//System.out.println(list.getLast().start + " " + list.getLast().end);
		}
		BellmanFord(list, nVertices, nItem);
		io.close();
	}
	
	public static void BellmanFord(LinkedList<edge> list, int nVertices, int[] nItem) {
		int bigger;
		int smaller;
		int[] distance = new int[nVertices];
		int[] totalItem = new int[nVertices];
		for(int i = 0; i < nVertices; i++) {
			totalItem[i] = nItem[i];
		}
		for(int i = 1; i < nVertices; i++) {
			distance[i] = Integer.MAX_VALUE - 100;
		}
		edge cur;
		for(int j = 0; j < 5; j++) {
			for(int i = 0; i < list.size(); i++) {
				cur = list.get(i);
				if(distance[cur.start - 1] > distance[cur.end-1]) {
					bigger = cur.start - 1;
					smaller = cur.end - 1;
				}else {
					bigger = cur.end - 1;
					smaller = cur.start - 1;
				}
				if(distance[smaller] + cur.distance <= distance[bigger]) {
					if(distance[smaller] + cur.distance < distance[bigger]){
						totalItem[bigger] = totalItem[smaller] + nItem[bigger];
					}else {
						if(totalItem[smaller] + nItem[bigger] > totalItem[bigger])
							totalItem[bigger] = totalItem[smaller] + nItem[bigger];
					}
	
					distance[bigger] = distance[smaller] + cur.distance;
				}
			}
		}
		if(distance[nVertices - 1] != Integer.MAX_VALUE - 100)
			System.out.println(distance[nVertices - 1] + " " + totalItem[nVertices - 1]);
		else
			System.out.println("impossible");
	}
	

}