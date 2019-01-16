/*
 * Name: Evan Guo
 * Student Number: V00866199
 * Assignment 3
 */

public class StackRefBased<T> implements Stack<T> {
	private int count;
	private StackNode<T> top;
	
    public StackRefBased() {
    	count = 0;
    }

    public int size() {
        return count;
    }


    public boolean isEmpty() {
        return count == 0;
    }


    public void push(T data) {
        if(count == 0){
        	top = new StackNode<T>(data);
        }else{
        	StackNode<T> tmp = new StackNode<T>(data);
        	tmp.next = top;
        	top = tmp;
        }
        count++;
    }


    public T pop() throws StackEmptyException {
    	if(top == null){
    		throw new StackEmptyException("cannot pop an empty stack");
    	}
    	StackNode<T> tmp = top;
    	if(top.next == null){
    		top = null;
    		count = 0;
    		return tmp.data;
    	}
        top = top.next;
        count--;
        return tmp.data;
    }


    public T peek(){
    	return top.data;
    }


    public void makeEmpty() {
    	top = null;
    	count = 0;
    }


    public String toString(){
		StringBuffer sb = new StringBuffer(60); 
		if(size() ==0){
			return "";
		}
		try {
			sb.append(pop());
			while(count != 0){
				sb.append(" "+ pop());
			}
		} catch (StackEmptyException e) {
			e.printStackTrace();
    	}
        return sb.toString();
    }
    
}