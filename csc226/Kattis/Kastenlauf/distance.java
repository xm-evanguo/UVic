import java.util.Comparator;

public class distance implements Comparator<edgeNode>{

	public int compare(edgeNode x, edgeNode y) {
		if(x.distance == y.distance) {
			return 0;
		}else if(x.distance > y.distance) {
			return 1;
		}else
			return -1;
	}

}
