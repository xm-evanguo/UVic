/*
 * Name: Evan Guo
 * Student Number: V00866199
 * CSC 226 Spring 2018
*/

import java.util.Stack;
public class PUS {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		System.out.println("abc");
		int nStick = io.getInt();
		int nLine = io.getInt();
		graph g = new graph(nStick);
		for(int i = 0; i < nLine; i++){
			g.add(io.getInt(), io.getInt());
		}
		Stack<Integer> s = new Stack<Integer>();
		g.topologicalSort(s);
		while(!s.isEmpty()) {
			io.println(s.pop());
		}
		io.close();
	}
	
}
