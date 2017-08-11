/*
 * 
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class UF
{	
	private int count; // number of components
	QuickFind qu;
	
	public UF(int N)
	{ 
		count = N;		
		qu = new QuickFind(N);		
	}
	
	public int count()
	{ return count; }
	
	public boolean connected(int p, int q)
	{ return find(p) == find(q); }
	
	public int find(int p)
	{
		return qu.find(p);
	}
	
	public void union(int p, int q)
	{
		qu.union(p, q);
	}
	
	public  void main(String[] args)
	{ 
		int sites = 10000;
		String fileName="";
		if (args.length == 1)
		{
			fileName = args[0];		
		}
		else
		{
		  System.out.println("Please enter input as <filename>");
		  System.exit(1);
		}
						
		UF uf = new UF(sites);
		Path filePath = Paths.get(fileName);
		Scanner inFile;
		try {
			inFile = new Scanner(filePath);
			long start = System.currentTimeMillis();
			while (inFile.hasNext())
			{
				int p = inFile.nextInt();
				int q = inFile.nextInt(); // Read pair to connect.
				if (uf.connected(p, q)) continue; // Ignore if connected.
				uf.union(p, q); // Combine components
				count--;
				//System.out.println(p + " " + q); // and print connection.
			}		
			long now = System.currentTimeMillis();
			double timeElapsed = (now - start) / 1000.0000;
			System.out.println("\nTime elapsed:"+timeElapsed);
		
			System.out.println(uf.count() + " components");
			inFile.close();
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
