import java.util.LinkedList;

public class Stick {
	
	public LinkedList<Integer> top;
	public boolean visited;
	
	public Stick(int x) {
		top = new LinkedList<Integer>();
		visited = false;
	}
	
	public void addTop(int x) {
		top.add(x);
	}
	
}
