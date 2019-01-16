import java.util.PriorityQueue;

public class CookieWithPriorityQueue {

	public static void main(String[] args) {
		Reverse r = new Reverse();
		PriorityQueue<Integer> largeFirst = new PriorityQueue<Integer>(150001, r);
		PriorityQueue<Integer> smallFirst = new PriorityQueue<Integer>(150000);
		Kattio io = new Kattio(System.in);
		String nxt;
		int tmp;
		while(io.hasMoreTokens()) {
			nxt = io.getWord();
			if(!nxt.equals("#")) {
				tmp = Integer.parseInt(nxt);
				if(smallFirst.peek() == null) {
					smallFirst.add(tmp);
				}else if(tmp > smallFirst.peek()){
					smallFirst.add(tmp);
					if(smallFirst.size() > largeFirst.size() + 1) {
						largeFirst.add(smallFirst.poll());
					}
				}else {
					largeFirst.add(tmp);
					if(largeFirst.size() > smallFirst.size()) {
						smallFirst.add(largeFirst.poll());
					}
				}
			}else {
				io.println(smallFirst.poll());
				if(smallFirst.size() != largeFirst.size()) {
					smallFirst.add(largeFirst.poll());
				}
			}
		}
		io.close();
	}

}
