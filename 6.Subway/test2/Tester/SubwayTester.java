import java.io.*;
import java.util.Iterator;

///////////////////////////////////////////////////////
/// made by SSH : Do not copy code					///
///													///
/// Execution : java SubwayTester data input output	///
///////////////////////////////////////////////////////

public class SubwayTester {
	
	private static SubwayGraph graph = new SubwayGraph();
	
	public static void main(String args[])
	{
		if(args.length < 3)
		{
			print("Wrong format : use \"java SubwayTester data input output\"\n");
			return;
		}
		try
		{
			inputData(args[0]);	
		}
		catch(Exception e) { print("Wrong data file\n"); return; }

		try{
			BufferedReader ir = new BufferedReader(new FileReader(args[1]));
			BufferedReader or = new BufferedReader(new FileReader(args[2]));
			int cnt = 0;
			while (true)
			{
				cnt++;
				try
				{
					String input = ir.readLine();
					String output = or.readLine();
					if (input == null || input.compareTo("") == 0 || output == null || output.compareTo("") == 0)
						break;
					int time = 0;
					try{
						time = Integer.parseInt(or.readLine());
					}
					catch(Exception e) { print("ERROR : invalid time\n"); continue;}
					String[] inputStations = input.split("\\s");
					String[] outputStations = output.split(" ");
					EdgeWeight answer = calculatedWeight(input);
					
					
					
					boolean transfer = false;
					if(inputStations.length == 3 && inputStations[2].compareTo("!") == 0)
					{
						transfer = true;
						print("Case " + cnt + " (!) \t: ");
					}
					else
						print("Case " + cnt + " (N)  \t: ");
					
					if(time != answer._20)
					{
						print("ERROR : incorrect time\n");
						continue;
					}
					if(inputStations[0].compareTo(outputStations[0]) != 0 || inputStations[1].compareTo(outputStations[outputStations.length-1]) != 0)
					{
						print("ERROR : incorrect start & end station\n");
						continue;
					}
					
					
					
					EdgeWeight outputAnswer = null;
					try{
						outputAnswer = graph.checkPath(outputStations);
					}
					catch(Exception e)
					{
						print(e.getMessage());
						continue;
					}
					if(outputAnswer.compareTo(answer, !transfer) != 0)
					{
						if(outputAnswer._20 != answer._20)
							print("ERROR : incorrect time\n");
						else if (transfer == true && outputAnswer._21 != answer._21)
							print("ERROR : incorrect transfer count\n");
					}
					else
						print("passed\n");
					
					
				}
				catch (Exception e) {print("ERROR : Input size != Output size\n");}
			}
			ir.close();
			or.close();
		}
		catch(Exception e) { print("Wrong format : use \"java SubwayTester data input output\"\n"); return; }
	}
	
	private static void print(String str)
	{
		System.out.print(str);
	}
	
	private static void inputData(String path) throws FileNotFoundException, IOException, NumberFormatException
	{
		BufferedReader fr = new BufferedReader(new FileReader(path));
		String input;
		String[] str;
		while (true)
		{
			input = fr.readLine();
			if (input == null || input.compareTo("") == 0)
				break;
			str = input.split("\\s");
			if(str.length < 3)
			{
				fr.close();
				throw new IllegalArgumentException();
			}
			graph.addVertex(str[0], str[1]);
		}
		while (true)
		{
			input = fr.readLine();
			if (input == null || input.compareTo("") == 0)
				break;
			str = input.split("\\s");
			if(str.length < 3)
			{
				fr.close();
				throw new IllegalArgumentException();
			}
			graph.addEdge(str[0], str[1], Integer.parseInt(str[2]));
		}
		
		fr.close();
	}
	
	private static EdgeWeight calculatedWeight(String com)
	{
		String[] str = com.split("\\s");
		if(str.length < 2)
			throw new IllegalArgumentException();
		
		boolean minTransfer = false;
		if(str.length == 3 && str[2].compareTo("!") == 0)
			minTransfer = true;

		return graph.findShortestPath(str[0], str[1], minTransfer);
	}
}
