import java.util.Collection;
import java.util.PriorityQueue;

public class DijkstraAlgorithm {

	public static void execute(DijkstraVertex root, Collection<DijkstraVertex> vertices, int option)
	{
		PriorityQueue<DijkstraVertex> pq = new PriorityQueue<DijkstraVertex>();
		for(DijkstraVertex v : vertices)
			v._32();
		root._33();
		pq.add(root);
		while(pq.isEmpty() == false)
		{
			DijkstraVertex v = pq.poll();
			for(DijkstraVertex neighbor : v._34())
			{
				if(neighbor._30(v, option))
				{
					pq.remove(neighbor);
					pq.add(neighbor);
				}
			}
		}
	}
}

interface DijkstraVertex extends Comparable<DijkstraVertex> {
	public boolean _30(DijkstraVertex factor, int option);
	public DijkstraVertex _31();
	public void _32();
	public void _33();
	public Collection<DijkstraVertex> _34();
}