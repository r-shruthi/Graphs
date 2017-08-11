import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Acyclic {

	private static boolean[] marked;
	private int[] edgeTo;
	private Stack cycle;

	public Acyclic(Graph G)
	{
		if (hasSelfLoop(G)) return;
		if (hasParallelEdges(G)) return;
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		for (int s = 0; s < G.V(); s++)
			if (!marked[s])
				dfs(G, -1, s);
	}

	// does this graph have a self loop
	private boolean hasSelfLoop(Graph G) {
		for (int v = 0; v < G.V(); v++) {
			for (int w : G.adj(v)) {
				if (v == w) {
					cycle = new Stack();
					cycle.push(v);
					cycle.push(v);
					return true;
				}
			}
		}
		return false;
	}

	// does this graph have two parallel edges
	private boolean hasParallelEdges(Graph G) {
		marked = new boolean[G.V()];

		for (int v = 0; v < G.V(); v++) {

			// check for parallel edges incident to v
			for (int w : G.adj(v)) {
				if (marked[w]) {
					cycle = new Stack();
					cycle.push(v);
					cycle.push(w);
					cycle.push(v);
					return true;
				}
				marked[w] = true;
			}

			// reset so marked[v] = false for all v
			for (int w : G.adj(v)) {
				marked[w] = false;
			}
		}
		return false;
	}  

	public boolean hasCycle() {
		return cycle != null;
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

			Graph G = new Graph(V);

			//set no. of edges
			if(inFile.hasNext())
				G.E = inFile.nextInt();

			while(inFile.hasNext())
			{
				int v = inFile.nextInt(); // Read a vertex,
				int w = inFile.nextInt(); // read another vertex,
				G.addEdge(v, w); 		  // and add edge connecting them.
				inFile.next(); 			  //ignore edge weights
			}
			inFile.close();			

			Acyclic finder = new Acyclic(G);
			if (finder.hasCycle()) {
				System.out.println("Graph is cyclic");
				while(!finder.cycle.isEmpty())
					System.out.print(finder.cycle.pop()+ " ");

				System.out.println();
			}
			else {
				System.out.println("Graph is acyclic");
			}
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	//depth first search
	private void dfs(Graph G, int u, int v)
	{
		marked[v] = true;
		for (int w : G.adj.get(v))
		{
			// short circuit if cycle already found
			if (cycle != null) return;

			if (!marked[w])
			{
				edgeTo[w] = v;
				dfs(G, v, w);
			}
			// check for cycle (but disregard reverse of edge leading to v)
			else if (w != u) {
				cycle = new Stack();
				for (int x = v; x != w; x = edgeTo[x]) {
					cycle.push(x);
				}
				cycle.push(w);
				cycle.push(v);
			}
		}
	}
}
