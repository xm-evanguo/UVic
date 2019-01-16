// Kattis problem "Kastenlauf" 3.5
// Type: Connectivity, uses UF, but could use DFS or BFS.
// Comment: on 226 hw #1

import java.util.*;

class NewKastenlauf {

	static int[] uf; // called id in the text
	static int[] sz;
	static int[] x, y;

	static int find ( int x ) {
		if (uf[x] != x) 
			uf[x] = find(uf[x]);
		return(uf[x]);
	}

	static void union ( int x, int y ) {
		if (sz[x] > sz[y]) { 
			uf[y] = x; 
			sz[x] += sz[y]; 
		}else { 
			uf[x] = y; 
			sz[y] += sz[x]; 
		}
	}

	static int dist( int v, int w ) {
		return( Math.abs( x[v]-x[w] ) + Math.abs( y[v]-y[w] ) );
	}

	static boolean checkAll( int N ) {
		for (int v=0; v<N+2; ++v)
			for (int w=0; w<v; ++w) 
				if (dist(v,w) <= 1000) { // 50*20 = 1000
					int fv = find( v );
					int	fw = find( w );
					if (fv != fw) 
						union( fv, fw );
				}
		int fS = find( 0 );
		int fT = find( N+1 );
		return( fS==fT );
	}

	public static void main ( String[] args ) {
		Scanner in = new Scanner( System.in );
		for (int t = in.nextInt(); t > 0; --t) {
			int N = in.nextInt();
			uf = new int[N+2];  
			sz = new int[N+2];
			x  = new int[N+2];   
			y = new int[N+2];
			for (int n = 0; n <= N + 1; ++n) {
				sz[n] = 1;  
				uf[n] = n;
				x[n] = in.nextInt();  
				y[n] = in.nextInt();
			}
			System.out.println( checkAll(N)? "happy" : "sad" );
		}
	}

}