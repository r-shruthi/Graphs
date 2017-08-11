import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrimsLazyMST {

	private double weight;       // total weight of MST
	private boolean[] marked; // MST vertices
	private List<Edge> mst; // MST edges
	private MinPQ pq; // crossing (and ineligible) edges
	
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
			
			//set no. of edges
			if(inFile.hasNext())
				G.E = inFile.nextInt();

			while(inFile.hasNext())
			{
				int v = inFile.nextInt(); // Read a vertex,
				int w = inFile.nextInt(); // read another vertex,
				double weight = inFile.nextDouble();
				Edge e = new Edge(v,w,weight);
				G.addEdge(e); 		  // and add edge connecting them.
							  //ignore edge weights
			}
			inFile.close();	
			PrimsLazyMST prim = new PrimsLazyMST(G);
			
			for (Edge e : prim.edges()) {
	            System.out.println(e);
	        }
	        System.out.printf("%.5f\n", prim.weight());
			
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	
	public PrimsLazyMST(EdgeWeightedGraph G)
	{
		pq = new MinPQ();
		marked = new boolean[G.V()];
		mst = new ArrayList<Edge>();
		for (int v1 = 0; v1 < G.V(); v1++)     // run Prim from all vertices to
            if (!marked[v1])
            {
            	visit(G, v1); // assumes G is connected (see Exercise 4.3.22)
				while (!pq.isEmpty())
				{
					Edge e = pq.delMin(); // Get lowest-weight
					int v = e.either(), w = e.other(v); // edge from pq.
					if (marked[v] && marked[w]) continue; // Skip if ineligible.
					mst.add(e); // Add edge to tree.
					weight += e.weight();
					if (!marked[v]) visit(G, v); // Add vertex to tree
					if (!marked[w]) visit(G, w); // (either v or w).
				}
            }
	}
	private void visit(EdgeWeightedGraph G, int v)
	{ // Mark v and add to pq all edges from v to unmarked vertices.
		marked[v] = true;
		for (Edge e : G.adj(v))
			if (!marked[e.other(v)]) pq.insert(e);
	}
	public Iterable<Edge> edges()
	{ return mst; }
	
	public double weight()
	{
        return weight;
    }
}
