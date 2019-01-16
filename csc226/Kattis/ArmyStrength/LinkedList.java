
public class LinkedList
{
   int n;
   ListNode start;
   ListNode rear;

   public LinkedList()
   {
       n= 0;
       start= null;
       rear= null;
   }
   
   public LinkedList(int size, ListNode first, ListNode last)
   {
       n= size;
       start= first;
       rear= last;
   }
   
   //quick sort
   public void sort()
   {
	   
	   // Base case
	   if(n <= 1) {
		   return;
	   }
	   
	   // Initialization
	   LinkedList list1 = new LinkedList();
	   LinkedList list2 = new LinkedList();
	   LinkedList list3 = new LinkedList();
	   ListNode pivot = rear;
	   ListNode cur = start;
	   
	   // Put pivot into list2
	   list2.start = pivot;
	   list2.rear = list2.start;
	   list2.n = 1;
	   
	   // Divide the list into three list
	   // Smaller than pivot goes to list1
	   // Equal to pivot goes to list2
	   // Larger than pivot goes to list3
	   while(cur != rear){
		   if(pivot.data > cur.data) {
			   if(list1.n == 0) {
				   list1.start = cur;
				   list1.rear = list1.start;
			   }else {
				   list1.rear.next = cur;
				   list1.rear = list1.rear.next;
			   }
			   list1.n++;
		   }else if(pivot.data == cur.data) {
			   list2.rear.next = cur;
			   list2.rear = list2.rear.next;
			   list2.n++;
		   }else {
			   if(list3.n == 0) {
				   list3.start = cur;
				   list3.rear = list3.start;
			   }else {
				   list3.rear.next = cur;
				   list3.rear = list3.rear.next;
			   }
			   list3.n++;
		   }
		   cur = cur.next;
	   }
	   
	   // Make sure the rear.next of each list is null
	   if(list1.rear != null)
		   list1.rear.next = null;
	   list2.rear.next = null;
	   if(list3.rear != null)
		   list3.rear.next = null;
	   
	   // Testing part
	   /*System.out.println("debug list 2");
	   list2.printBigIntegerList();
	   System.out.println("");*/
	   // Testing part
	   
	   // Recursion
	   list1.sort();
	   list3.sort();
	   
	   // Merge
	   if(list1.rear != null) {
		   list1.rear.next = list2.start;
		   start = list1.start;
	   }else {
		   start = list2.start;
	   }
	   if(list3.start != null) {
		   list2.rear.next = list3.start;
		   rear = list3.rear;
	   }else {
		   rear = list2.rear;
	   }
   }
   
}


