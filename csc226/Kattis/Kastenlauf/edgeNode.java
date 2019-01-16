
public class edgeNode {

	int distance;
	int index1;
	int index2;
	edgeNode next;
	
	public edgeNode(int x, int y, locationNode[] a) {
		if(x < y) {
			index1 = x;
			index2 = y;
		}else {
			index1 = y;
			index2 = x;
		}
		distance = Math.abs(a[index1].x - a[index2].x) + Math.abs(a[index1].y - a[index2].y);
		next = null;
	}
	
}
