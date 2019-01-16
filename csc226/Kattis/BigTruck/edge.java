
public class edge {
	
	public int start;
	public int end;
	public int distance;
	
	public edge(int s, int e, int w) {
		if(s < e) {
			start = s;
			end = e;
		}else {
			start = e;
			end = s;
		}
		distance = w;
	}
	
}
