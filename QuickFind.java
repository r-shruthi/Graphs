/*This program implements the QuickFind algorithm 
 * for use with the dynamic connectivity client.
 * 
 * No of sites is fixed as 10000 in the program
 * 
 * To execute:
 javac QuickFind.java
 java QuickFind <input_filename>
 * 
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class QuickFind 
{	
	private int[] id;
	
	public QuickFind(int N)
	{
		id = new int[N];
		for (int i = 0; i < N; i++)
		id[i] = i;
	}
	
	//This method checks if two sites are connected
	//by comparing the id array
	public boolean connected(int p, int q)
	{ return find(p) == find(q); }
	
	//This method returns the id of the component
	//in which the site exists
	public int find(int p)
	{ 
		return id[p]; 
	}
	
	//This method implements union of two components
	//by setting the id of all sites in which p exists to id of 
	//component in which q exists
	public void union(int p, int q)
	{
		int pid = id[p];
		int qid = id[q];
		for (int i = 0; i < id.length; i++)
			if (id[i] == pid) 
				id[i] = qid;		
	}
	
	public static void main(String[] args)
	{ 
		int sites = 16384*2*2*2*2*2;
		
		// number of components
		int count = sites;
		String fileName="";
		//read the filename
		if (args.length == 1)
		{
			fileName = args[0];		
		}
		else
		{
		  System.out.println("Please enter input as <filename>");
		  System.exit(1);
		}
		QuickFind uf = new QuickFind(sites);
		Path filePath = Paths.get(fileName);
		Scanner inFile;
		try {
			inFile = new Scanner(filePath);
			//read pairs of sites from the input array and check if they are connected
			long start = System.currentTimeMillis();
			while (inFile.hasNext())
			{
				int p = inFile.nextInt();
				int q = inFile.nextInt(); // Read pair to connect.
				if (uf.connected(p, q)) continue; // Ignore if connected.
				uf.union(p, q); // Combine components
				count--;
			}	
			long now = System.currentTimeMillis();
			//calculate elapsed time
			double timeElapsed = (now - start) / 1000.0000;
			System.out.println("\nTime elapsed:"+timeElapsed);
		
			System.out.println(count + " components");
			inFile.close();
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
