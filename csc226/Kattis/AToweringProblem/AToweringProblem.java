import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class AToweringProblem {

	public static void main(String[] args) {
		int[] a = new int[8];
		int tmp = 0;
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i < 8; i++) {
			a[i] = sc.nextInt();
		}
		sc.close();
		PriorityQueue<Integer> pq1 = new PriorityQueue<>(Collections.reverseOrder());
		PriorityQueue<Integer> pq2 = new PriorityQueue<>(Collections.reverseOrder());
		for(int i = 0; i < 6; i++) {
			for(int j = i + 1; j < 6; j++) {
				for(int k = j + 1; k < 6; k++) {
					tmp = a[i] + a[j] + a[k];
					if(tmp == a[6]) {
						pq1.add(a[i]);
						pq1.add(a[j]);
						pq1.add(a[k]);
					}else if(tmp == a[7]) {
						pq2.add(a[i]);
						pq2.add(a[j]);
						pq2.add(a[k]);
					}
					if(!pq1.isEmpty() && !pq2.isEmpty()) {
						break;
					}
				}
			}
		}
		System.out.print(pq1.poll());
		while(!pq1.isEmpty()) {
			System.out.print(" " + pq1.poll());
		}
		while(!pq2.isEmpty()) {
			System.out.print(" " + pq2.poll());
		}
	}

}
