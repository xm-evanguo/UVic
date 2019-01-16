import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class graph {
	private int n;
	private Stick[] list;
	private int[] bot;
	
	public graph(int x){
		n = x + 1;
		list = new Stick[n];
		bot = new int[n];
	}
	
	public void add(int x, int y) {
		if(list[x] == null) {
			list[x] = new Stick(x);
		}
		if(list[y] == null) {
			list[y] = new Stick(y);
		}
		list[y].addTop(x);
		bot[x]++;
	}
	
	private int findRoot() {
		for(int i = 1; i < n; i++) {
			if(bot[i] == 0 && !list[i].top.isEmpty()) {
				return i;
			}
		}
		return 0;
	}
	
	public void topologicalSort(Stack<Integer> finish) {
		Queue<Integer> q = new LinkedList<Integer>();
		int tmp;
		int cur = findRoot();
		if(cur == 0) {
			System.out.println("IMPOSSIBLE");
			return;
		}else {
			q.add(cur);
			while(!q.isEmpty()) {
				cur = q.peek();
				while(!list[cur].top.isEmpty()) {
					tmp = list[cur].top.poll();
					if(list[tmp].visited || bot[tmp] < 1) {
						System.out.println("IMPOSSIBLE");
						return;
					}else if(bot[tmp] == 1) {
						q.add(tmp);
					}
					bot[tmp]--;
				}
				tmp = q.poll();
				list[tmp].visited = true;
				finish.add(tmp);
			}
		}
	}
	
}
