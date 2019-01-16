import java.util.Random;
public class SortingTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList list = buildnewlist();
		ListNode tmp = list.start;
		System.out.println("Before sorting: ");
		for(int i = 0; i < list.n - 1; i++) {
			System.out.print(tmp.data + " ");
			tmp = tmp.next;
		}
		System.out.println(tmp.data);
		list.sort();
		
		System.out.println("After sorting: ");
		tmp = list.start;
		for(int i = 0; i < list.n - 1; i++) {
			System.out.print(tmp.data + " ");
			tmp = tmp.next;
		}
		System.out.println(tmp.data);
	}
	
	public static LinkedList buildnewlist() {
		Random rd = new Random();
		LinkedList list = new LinkedList();
		ListNode tmp;
		for(int i = 0; i < 10; i++) {
			tmp = new ListNode(rd.nextInt(20), null);
			if(i == 0) {
				list.start = tmp;
				list.rear = list.start;
			}else {
				list.rear.next = tmp;
				list.rear = tmp;
			}
			list.n++;
		}
		return list;
	}
}
