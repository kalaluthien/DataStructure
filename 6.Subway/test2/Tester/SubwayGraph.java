import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class SubwayGraph {

	private HashMap<String, ArrayList<SubwayVertex>> _1;	
	private TreeMap<String, SubwayVertex> _2;
	
	public SubwayGraph()
	{
		_2 = new TreeMap<String, SubwayVertex>();
		_1 = new HashMap<String, ArrayList<SubwayVertex>>();
	}
	
	public void addVertex(String id, String station)
	{
		SubwayVertex v = new SubwayVertex(station);
		_2.put(id, v);
		
		ArrayList<SubwayVertex> _3 = _1.get(v._16);
		if(_3 != null)
		{
			final int TRANSFER_TIME = 5;
			for(SubwayVertex col : _3)
			{
				v.createEdge(col, TRANSFER_TIME, 1);
				col.createEdge(v, TRANSFER_TIME, 1);
			}
			_3.add(v);
		}
		else
		{
			ArrayList<SubwayVertex> _4 = new ArrayList<SubwayVertex>();
			_4.add(v);
			_1.put(v._16, _4);
		}
	}
	
	public void addEdge(String srcID, String dstID, int weight)
	{
		SubwayVertex src = _2.get(srcID);
		SubwayVertex dst = _2.get(dstID);
		src.createEdge(dst, weight, 0);
	}
	
	public EdgeWeight findShortestPath(String srcStn, String dstStn, boolean minTransfer)
	{
		@SuppressWarnings("unchecked")
		Collection<DijkstraVertex> _5 = (Collection<DijkstraVertex>)(Collection<?>)_2.values();
		EdgeWeight _8 = new EdgeWeight(Integer.MAX_VALUE, Integer.MAX_VALUE);
		LinkedList<SubwayVertex> _6 = null;

		int _7 = 0;
		if(minTransfer == true)
			_7 = 1;
		
		for(SubwayVertex _9 : _1.get(srcStn))
		{
			DijkstraAlgorithm.execute(_9, _5, _7);
			for(SubwayVertex _10 : _1.get(dstStn))
				if(_8.compareTo(_10._17) > 0)
				{
					_8 = _10._17.clone();
					
					SubwayVertex v = _10;
					_6 = new LinkedList<SubwayVertex>();
					_6.addFirst(v);
					while(v != _9)
					{
						v = (SubwayVertex)v._31();
						_6.addFirst(v);
					}
				}
		}
		ArrayList<String> _11 = new ArrayList<String>();
		_11.add(String.valueOf(_8._20));
		
		Iterator<SubwayVertex> _13 = _6.iterator();
		String _12 = null;
		if(_13.hasNext())
			_12 = _13.next()._16;
		while(_13.hasNext())
		{
			String curr = _13.next()._16;
			if(_12 != null)
			{
				if(_12.compareTo(curr) == 0)
				{
					_11.add("[" + _12 + "]");
					_12 = null;
					continue;
				}
				else
					_11.add(_12);
			}
			_12 = curr;
		}
		_11.add(_12);
		return _8;
	}
	
	@SuppressWarnings("unchecked")
	public EdgeWeight checkPath(String[] stations) throws  Exception
	{
		ArrayList<SubwayVertex> prevList = (ArrayList<SubwayVertex>)_1.get(stations[0]).clone();
		String prevStn = prevList.get(0)._16;
		for(SubwayVertex v : prevList)
			v._33();
		for(int i = 1; i < stations.length; i++)
		{
			String stn = stations[i];
			boolean transfer = false;
			if(stn.charAt(0) == '[')
			{
				transfer = true;
				stn = stn.substring(1, stn.length() - 1);
			}
			ArrayList<SubwayVertex> currList = _1.get(stn);
			if(currList == null)
				throw new Exception("ERROR : invalid station name : " + stn + "\n");
			currList = (ArrayList<SubwayVertex>)currList.clone();
			for(SubwayVertex v : currList)
				v._32();
			for(SubwayVertex prev : prevList)
			{
				for(Edge e : prev._15)
				{
					boolean found = false;
					for(SubwayVertex curr : currList)
					{
						if(e.dst == curr)
						{
							prev._17.sum(e.weight);
							curr._17 = prev._17.clone();
							found = true;
							break;
						}
					}
					if(found)
						break;
				}
			}
			Iterator<SubwayVertex> it = currList.iterator();
			while(it.hasNext())
			{
				SubwayVertex v = it.next();
				if(v._17.compareTo(new EdgeWeight(Integer.MAX_VALUE, Integer.MAX_VALUE)) == 0)
				{
					if(transfer == false)
						it.remove();
					else
					{
						EdgeWeight min = new EdgeWeight(Integer.MAX_VALUE, Integer.MAX_VALUE);
						for(SubwayVertex vv : currList)
							if(vv._17.compareTo(min) < 0)
								min = vv._17.clone();
						min.sum(new EdgeWeight(5,1));
						v._17 = min;
					}
				}
					
			}
			prevList = currList;
			if(prevList.isEmpty())
				throw new Exception("ERROR : cannot find path : " + prevStn + " -> " + stn +"\n");
			prevStn = stn;
		}
		
		EdgeWeight min = new EdgeWeight(Integer.MAX_VALUE, Integer.MAX_VALUE);
		for(SubwayVertex vv : prevList)
			if(vv._17.compareTo(min) < 0)
				min = vv._17;
		return min;
	}
}