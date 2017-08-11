import java.util.ArrayList;
import java.util.List;

public class Graph {


	public final int V; 			 // number of vertices
	public int E; 					 // number of edges
	public List<List<Integer>> adj; // adjacency lists

	public Graph(int V)
	{
		this.V = V; this.E = 0;
		adj =  new ArrayList<List<Integer>>(); // Create list of lists.
		for (int v = 0; v < V; v++) 		   // Initialize all lists
			adj.add(new ArrayList<Integer>()); // to empty.
	}

	public int V() { return V; }
	public int E() { return E; }

	public void addEdge(int v, int w)
	{			
		adj.get(v).add(w); // Add w to v’s list.
		adj.get(w).add(v); // Add v to w’s list.
		E++;
	}
	public Iterable<Integer> adj(int v)
	{ 
		return adj.get(v); 
	}
}

