/*
 * Name: Evan Guo
 * Student Number: V00866199
 * CSC 226 Spring 2018
 */

import java.math.BigInteger;
public class TreeInsertion {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int tmp = io.getInt();
		while(tmp != 0) {
			BST Tree = new BST();
			for(int i = 0; i < tmp; i++) {
				Tree.insert(io.getInt());
			}
			if(Tree.isEmpty()) {
				io.println(0);
			}else {
				io.println(calculation(Tree.root));
			}
			tmp = io.getInt();
		}
		io.close();
	}
	
	public static BigInteger calculation(Node cur) {
		if(cur == null) {
			BigInteger bi = BigInteger.valueOf(1);
			return bi;
		}
		int left = cur.nLChild;
		int right = cur.nRChild;
		return combination(left+right, 	right).multiply(calculation(cur.left).multiply(calculation(cur.right)));
	}
	
	public static BigInteger combination(int n, int r) {
		return(factorial(n).divide(factorial(r).multiply(factorial(n-r))));
	}
	
	public static BigInteger factorial(int x) {
		BigInteger bi = BigInteger.valueOf(x);
		if(x == 0) {
			bi = BigInteger.valueOf(1);
			return bi;
		}
		for(int i = x - 1; i > 1; i--) {
			BigInteger tmp = BigInteger.valueOf(i);
			bi = bi.multiply(tmp);
		}
		return bi;
	}
	
}
