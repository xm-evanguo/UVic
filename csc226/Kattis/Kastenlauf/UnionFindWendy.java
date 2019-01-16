/*
    The UnionFind class is provided to you for CSC 225
    by Wendy Myrvold.  You do not have permission to distribute
    this code or to use it for any other purpose.

    In order for your code to compile when marked, you
    should NOT modify this code.

    This flat union/find data structure maintains the property
    that parent[u] is the vertex number of the smallest numbered
    vertex in the same component as u.
*/
public class UnionFindWendy
{
    int n;
    int [] parent;

/*  
    Make a new union/find data structure.
    Each vertex is initially in a component with itself.
*/
    public UnionFindWendy(int n_vertex)
    {
       int i;

       n= n_vertex;
       parent= new int[n_vertex];

       for (i=0; i < n; i++)
           parent[i]=i;
    }

/*
    Returns true if u and v are in the same component
    and false otherwise.
*/

    public boolean same_component(int u, int v)
    {
        if (parent[u]== parent[v]) return(true);
        else return(false);
    }
 
/* 
    Update components to reflect the addition of edge (u, v).
*/

    public void union(int u, int v)
    {
        int i, min, max;

//      Just return if u and v are already in the same component.

        if (parent[u] ==  parent[v]) return;

/* 
        Otherwise, update the data structure.
*/

        if (parent[u] < parent[v])
        {
            min= parent[u];
            max= parent[v];
        }
        else
        {
            min= parent[v];
            max= parent[u];
        }
        for (i=0; i < n; i++)
        {
            if (parent[i]== max) parent[i]= min;
        }
    }
}
