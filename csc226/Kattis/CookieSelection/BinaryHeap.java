
public class BinaryHeap {
	
	int[] holding;
	int count;
	
	public BinaryHeap() {
		holding = new int[300000];
		count = 0;
	}
	
	public void insert(int k) {
		holding[count] = k;
		int tmp = count;
		if(tmp % 2 == 0 && tmp != 2) {
			while(tmp != 0 && holding[tmp] < holding[(tmp - 2) / 2]) {
				swap(tmp, (tmp - 2) / 2);
				tmp = (tmp - 2) / 2;
			}
		}else if(tmp % 2 == 1 && tmp != 1) {
			while(tmp != 0 && holding[tmp] < holding[(tmp - 1) / 2]) {
				swap(tmp, (tmp - 1) / 2);
				tmp = (tmp - 1) / 2;
			}
		}else if(tmp == 1 || tmp == 2) {
			if(holding[tmp] < holding[0]) {
				swap(tmp, 0);
			}
		}
		count++;
	}
	
	public int delete(int k) {
		int curIndex= k - 1;
		int maxIndex = count - 1;
		swap(curIndex, maxIndex);
		maxIndex--;
		while(2*curIndex + 1 <= maxIndex){
			if(2*curIndex + 2 > maxIndex) {
				if(holding[2*curIndex + 1] < holding[curIndex]) {
					swap(curIndex, 2*curIndex+1);
					curIndex = 2*curIndex + 1;
				}else {
					break;
				}
			}else{
				if(holding[2*curIndex + 1] < holding[2*curIndex + 2] && holding[curIndex] > holding[2*curIndex + 1]){
					swap(curIndex, 2*curIndex + 1);
					curIndex = 2*curIndex +1;
				}else if(holding[2*curIndex + 2] < holding[curIndex]){
					swap(curIndex, 2*curIndex + 2);
					curIndex = 2*curIndex +2;
				}else {
					break;
				}
			}
		}
		count--;
		return(holding[count]);
	}
	
	public void swap(int x, int y) {
		int tmp = holding[x];
		holding[x] = holding[y];
		holding[y] = tmp;
	}
	
	
}
