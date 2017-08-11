import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class KruskalMST {    
	
    private double weight;                        // weight of MST
    private List<Edge> mst = new ArrayList<Edge>();  // edges in MST

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     */
    public KruskalMST(EdgeWeightedGraph G) {
        // more efficient to build heap by passing array of edges
        MinPQ pq = new MinPQ();
        for (Edge e : G.edges()) {
            pq.insert(e);
        }

        // run greedy algorithm
        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) { // v-w does not create a cycle
                uf.union(v, w);  // merge v and w components
                mst.add(e);  // add edge e to mst
                weight += e.weight();
            }
        }

    }

    /**
     * Returns the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     */
    public double weight() {
        return weight;
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
	        KruskalMST mst = new KruskalMST(G);
	        for (Edge e : mst.edges()) {
	            System.out.println(e);
	        }
	        System.out.printf("%.5f\n", mst.weight());
    	} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
    }

}