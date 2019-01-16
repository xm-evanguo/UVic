import java.util.HashMap;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class Arbitrage {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		HashMap<String, Integer> mapping;
		int[][] edgeList;
		double[] edgeListWeight;
		int nCurrency;
		int nEdge;
		double[] weight;
		int start;
		int end;
		String[] tmp;
		Double theWeight;
		boolean check = true;
		nCurrency =  io.getInt();
		while(nCurrency != 0) {
			mapping = new HashMap<String, Integer>();
			for(int i = 0; i < nCurrency; i++) {
				mapping.put(io.getWord(), i);
			}
			nEdge = io.getInt();
			if(nEdge==0) {
				System.out.println("Ok");
				nCurrency = io.getInt();
				continue;
			}
			weight = new double[nCurrency];
			edgeList = new int[nEdge][2];
			edgeListWeight = new double[nEdge];
			for(int j = 0; j < nCurrency; j++) {
				for(int i = 0; i < nEdge; i++) {
					if(j == 0) {
						if(i < nCurrency) {
							weight[i] = 101;
						}
						start = mapping.get(io.getWord());
						end = mapping.get(io.getWord());
						tmp = io.getWord().split(":");
						theWeight = -Math.log(Double.parseDouble(tmp[1]) / Double.parseDouble(tmp[0]));
						edgeList[i][0] = start;
						edgeList[i][1] = end;
						edgeListWeight[i] = theWeight;
					}
					check = true;
					start = edgeList[i][0];
					end = edgeList[i][1];
					theWeight = edgeListWeight[i];
					if(weight[start] + theWeight < weight[end]) {
						weight[end] = weight[start] + theWeight;
						check = false;
						if(j == nCurrency - 1) {
							break;
						}
					}
				}
			}
			if(check) {
				System.out.println("Ok");
			}else {
				System.out.println("Arbitrage");
			}
			nCurrency =  io.getInt();
		}
		
		io.close();
	}

}
