// Kattis problem "Odd Man Out" 1.3
// Type: Easy, check for duplicates.
// Comment: Could also brute-force. 

import java.util.*;

class OddOutA {

	public static void main ( String[] args ) {
		Scanner in = new Scanner( System.in );
		int C = in.nextInt();
		for (int c=1; c<=C; ++c) {
			HashMap<String,Integer> hash = new HashMap<String,Integer>();
			int G = in.nextInt();
			in.nextLine();
			String[] S = in.nextLine().split(" ");
			for (int g=0; g<G; ++g) 
				hash.put( S[g], hash.containsKey(S[g])? 2 : 1 );
			for (int g=0; g<G; ++g) 
				if (hash.get(S[g])==1) // loner 
					System.out.println( "Case #"+c+": "+S[g] );
		}
	}
}