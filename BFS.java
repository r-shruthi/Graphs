import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BFS {
	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] marked;  // marked[v] = is there an s-v path
	private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
	private double[] distTo;      // distTo[v] = number of edges shortest s-v path
	private static int count = 0;

	/**
	 * Computes the shortest path between the source vertex 
	 * and every other vertex in the graph
	 */
	public BFS(EdgeWeightedGraph G, int s) {
		marked = new boolean[G.V()];
		distTo = new double[G.V()];
		edgeTo = new int[G.V()];
		validateVertex(s);
		bfs(G, s);
		assert check(G, s);
	}

	
	// breadth-first search from a single source
	private void bfs(EdgeWeightedGraph G, int s) {
		List<Integer> q = new ArrayList<Integer>();
		for (int v = 0; v < G.V(); v++)
			distTo[v] = INFINITY;
		distTo[s] = 0.0;
		marked[s] = true;
		q.add(s);
		if(count<50)
		{
			System.out.println("First 50 visited vertices in order of visiting:");
			System.out.printf("Vertex: %d, Distance to Source: %f\n", s, distTo[s]);
			count++;
		}
		while (!q.isEmpty()) {
			int v = q.remove(0);
			//add child node to queue
			for (Edge e : G.adj(v)) {
				int w = e.other(v);
				if (!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + e.weight();
					marked[w] = true;
					q.add(w);
					if(count<50)
					{
						count++;
						System.out.printf("Vertex: %d, Distance to Source: %f, Edge to: %d\n", w, distTo[w],edgeTo[w] );
					}
				}
			}
		}
	}

	
	/**
	 * Is there a path between the source vertex  and vertex v   
	 */
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return marked[v];
	}

	/**
	 * Returns the number of edges in a shortest path between the source vertex and vertex v
	 */
	public double distTo(int v) {
		validateVertex(v);
		return distTo[v];
	}

	/**
	 * Returns a shortest path between the source vertex 
	 * and v, or null if no such path.
	 */
	public List<Integer> pathTo(int v) {
		validateVertex(v);
		if (!hasPathTo(v)) return null;
		List<Integer> path = new ArrayList<Integer>();
		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x])
			path.add(x);
		path.add(x);
		return path;
	}


	// check optimality conditions for single source
	private boolean check(EdgeWeightedGraph G, int s) {

		// check that the distance of s = 0
		if (distTo[s] != 0) {
			System.out.println("distance of source " + s + " to itself = " + distTo[s]);
			return false;
		}

		// check that for each edge v-w dist[w] <= dist[v] + 1
		// provided v is reachable from s
		for (int v = 0; v < G.V(); v++) {
			for (Edge e : G.adj(v)) {
				int w = e.other(v);
				if (hasPathTo(v) != hasPathTo(w)) {
					System.out.println("edge " + v + "-" + w);
					System.out.println("hasPathTo(" + v + ") = " + hasPathTo(v));
					System.out.println("hasPathTo(" + w + ") = " + hasPathTo(w));
					return false;
				}
				if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
					System.out.println("edge " + v + "-" + w);
					System.out.println("distTo[" + v + "] = " + distTo[v]);
					System.out.println("distTo[" + w + "] = " + distTo[w]);
					return false;
				}
			}
		}

		// check that v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
		// provided v is reachable from s
		for (int w = 0; w < G.V(); w++) {
			if (!hasPathTo(w) || w == s) continue;
			int v = edgeTo[w];
			if (distTo[w] != distTo[v] + 1) {
				System.out.println("shortest path edge " + v + "-" + w);
				System.out.println("distTo[" + v + "] = " + distTo[v]);
				System.out.println("distTo[" + w + "] = " + distTo[w]);
				return false;
			}
		}

		return true;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}

	public static void main(String[] args) {
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
			if(inFile.hasNext())
				G.E = inFile.nextInt();

			while(inFile.hasNext())
			{
				int v = inFile.nextInt(); // Read a vertex,
				int w = inFile.nextInt(); // read another vertex,
				double weight = inFile.nextDouble();
				G.addEdge(new Edge(v, w, weight)); 	
			}
			inFile.close();	
			// StdOut.println(G);

			int s = Integer.parseInt(args[1]);
			BFS bfs = new BFS(G, s);			
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}