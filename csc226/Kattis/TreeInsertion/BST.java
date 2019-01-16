/*
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class BST {
	public Node root;
	public int size;
	
	public BST(){
		root = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void insert(int value) {
		if(size == 0) {
			root = new Node(value);
		}else {
			Node tmp = root;
			for(;;) {
				if(value < tmp.value) {
					tmp.nLChild++;
					if(tmp.left != null) {
						tmp = tmp.left;
					}else {
						tmp.left = new Node(value);
						break;
					}
				}else {
					tmp.nRChild++;
					if(tmp.right != null) {
						tmp = tmp.right;
					}else {
						tmp.right = new Node(value);
						break;
					}
				}
			}
		}
		size++;
	}
	
}
