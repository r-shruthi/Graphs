import java.util.ArrayList;
import java.util.List;

public class EdgeWeightedGraph {

	public final int V; // number of vertices
	public int E; // number of edges
	public List<List<Edge>> adj; // adjacency lists
	
	public EdgeWeightedGraph(int V)
	{
		this.V = V;
		this.E = 0;
		adj = new ArrayList<List<Edge>>();
		for (int v = 0; v < V; v++)
			adj.add(new ArrayList<Edge>());
	}
	
	public int V() { return V; }
	public int E() { return E; }
	
	public void addEdge(Edge e)
	{
		int v = e.either();
		int w = e.other(v);
		adj.get(v).add(e);
		adj.get(w).add(e);
		E++;
	}
	public Iterable<Edge> adj(int v)
	{ return adj.get(v); }
	
	public Iterable<Edge> edges()
	{
		List<Edge> b = new ArrayList<Edge>();
		for (int v = 0; v < V; v++)
		for (Edge e : adj.get(v))
			if (e.other(v) > v) b.add(e);
				return b;
	}
}
