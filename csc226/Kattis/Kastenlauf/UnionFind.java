
public class UnionFind {
	
	int n;
	int[] parent;
	
	public UnionFind(int x) {
		n = x;
		parent = new int[n];
		for(int i = 0; i < n; i++) {
			parent[i] = i;
		}
	}
	
	public boolean sameParent(int x, int y) {
		return parent[x] == parent[y];
	}
	
	public void union(int x, int y) {
		int i, min, max;


        if (parent[x] ==  parent[y]) return;


        if (parent[x] < parent[y])
        {
            min = parent[x];
            max = parent[y];
        }
        else
        {
            min = parent[x];
            max = parent[y];
        }
        for (i=0; i < n; i++)
        {
            if (parent[i]== max) parent[i]= min;
        }
	}
	
}
