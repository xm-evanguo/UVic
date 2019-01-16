import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class BT {
	//Big Truck with Dijkstra's algorithm
	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int nVertices = io.getInt();
		int[] nItem = new int[nVertices];
		ArrayList<LinkedList> list = new ArrayList<LinkedList>();
		LinkedList<Integer> tmp = new LinkedList<Integer>();
		for(int i = 0; i < nVertices; i++) {
			list.add(tmp);
		}
		edge cur;
		for(int i = 0; i < nVertices; i++) {
			nItem[i] = io.getInt();
		}
		int n = io.getInt();
		for(int i = 0; i < n; i++) {
			cur = new edge(io.getInt(), io.getInt(), io.getInt());
			pq.add(tmp);
		}
		Dijkstra(pq, nVertices, nItem);
		io.close();
	}
	
	public static void Dijkstra(PriorityQueue<edge> pq, int nVertices, int[] nItem) {
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
		for(int j = 0; j < pq.size() - 1; j++) {
			for(int i = 0; i < pq.size(); i++) {
				cur = pq.poll();
				if(distance[cur.start - 1] > distance[cur.end-1]) {
					bigger = cur.start - 1;
					smaller = cur.end - 1;
				}else {
					bigger = cur.end - 1;
					smaller = cur.start - 1;
				}
				if(distance[smaller] + cur.distance <= distance[bigger]) {
					distance[bigger] = distance[smaller] + cur.distance;
					if(totalItem[smaller] + nItem[bigger] > totalItem[bigger])
						totalItem[bigger] = totalItem[smaller] + nItem[bigger];
				}
			}
		}
		for(int i = 0; i < nVertices; i++) {
			System.out.print(totalItem[i] + " ");
		}
		System.out.println();
		if(distance[nVertices - 1] != Integer.MAX_VALUE)
			System.out.println(distance[nVertices - 1] + " " + totalItem[nVertices - 1]);
		else
			System.out.println("impossible");
	}

}