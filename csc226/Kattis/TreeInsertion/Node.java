/*
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class Node {
	public int value;
	public Node left;
	public Node right;
	public int nRChild;
	public int nLChild;
	
	public Node(int value) {
		nRChild = 0;
		nLChild = 0;
		this.value = value;
		left = null;
		right = null;
	}
}
