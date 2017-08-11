import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class DFS {
	private boolean[] marked;    // marked[v] = is there an s-v path?
	private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
	private double[] distTo;
	private final int s;         // source vertex
	private static int count = 0;

	/**
	 * Computes a path between s and every other vertex in graph iteratively
	 */
	public DFS(EdgeWeightedGraph G, int s) {        
		this.s = s;
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		distTo = new double[G.V()];
		validateVertex(s);
		System.out.println("First 50 visited vertices in order of visiting:");
		System.out.printf("Vertex: %d, Distance to Source: %f\n", s, 0.0);
		count++;

		// to be able to iterate over each adjacency list, keeping track of which
		// vertex in each adjacency list needs to be explored next
		@SuppressWarnings("unchecked")
		Iterator<Edge>[] adj = (Iterator<Edge>[]) new Iterator[G.V()];
		for (int v = 0; v < G.V(); v++)
			adj[v] = G.adj(v).iterator();

		// depth-first search using an explicit stack
		Stack stack = new Stack();
		marked[s] = true;
		stack.push(s);
		while (!stack.isEmpty()) {
			int v = stack.peek();
			if (adj[v].hasNext()) {
				Edge e = adj[v].next();
				int w = e.other(v);
				if (!marked[w]) {
					// discovered vertex w for the first time
					marked[w] = true;
					distTo[w] = distTo[v] + e.weight();
					edgeTo[w] = v;
					if(count<50)
					{
						count++;
						System.out.printf("Vertex: %d, Distance to Source: %f, Edge to: %d\n", w, distTo[w],edgeTo[w] );
					}
					stack.push(w);
				}
			}
			else {
				stack.pop();
			}
		}
	}
	/**
	 * Is there a path between the source vertex s and vertex v
	 */
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return marked[v];
	}

	/**
	 * Returns a path between the source vertex s and vertex v, or
	 * null if no such path.
	 */
	public List<Integer> pathTo(int v) {
		validateVertex(v);
		if (!hasPathTo(v)) return null;
		List<Integer> path = new ArrayList<Integer>();
		for (int x = v; x != s; x = edgeTo[x])
			path.add(x);
		path.add(s);
		return path;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}

	/**
	 * Returns the number of edges in a shortest path between the source vertex and vertex v
	 */
	public double distTo(int v) {
		validateVertex(v);
		return distTo[v];
	}

	public static void main(String[] args)
	{
		String fileInput = "";
		if(args.length > 0)
		{
			fileInput = args[0];
		}
		else
		{
			System.out.println("Please input the file name");
			System.exit(-1);		
		}
		Scanner inFile;		 
		Path filePath;

		//construct graph
		try {
			filePath = Paths.get(fileInput);	
			inFile = new Scanner(filePath);	
			int V=0;
			//read vertices count from file and initialize graph
			if(inFile.hasNext())
				V = inFile.nextInt();
			EdgeWeightedGraph G = new EdgeWeightedGraph(V);
			//set no. of edges
			if(inFile.hasNext())
				G.E = inFile.nextInt();

			while(inFile.hasNext())
			{
				int v = inFile.nextInt(); // Read a vertex,
				int w = inFile.nextInt(); // read another vertex,
				double weight = inFile.nextDouble();
				G.addEdge(new Edge(v, w, weight)); 		  // and add edge connecting them.
				 			  
			}
			inFile.close();	

			int s = Integer.parseInt(args[1]);
			DFS dfs = new DFS(G, s);

		}			
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}