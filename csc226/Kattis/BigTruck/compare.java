import java.util.Comparator;

public class compare implements Comparator<edge>{

	public int compare(edge x, edge y) {
		if(x.start < y.start) {
			return -1;
		}else if(x.start > y.start) {
			return 1;
		}else {
			if(x.end > y.end) {
				return 1;
			}else if(x.end < y.end) {
				return -1;
			}else {
				return 0;
			}
		}
	}
}
