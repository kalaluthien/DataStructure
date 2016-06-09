import java.util.ArrayList;
import java.util.Collection;

public class SubwayVertex implements DijkstraVertex {
	public String _16;
	public ArrayList<Edge> _15;
	
	
	public SubwayVertex()
	{
		_16 = null;
		_15 = new ArrayList<Edge>();
	}

	public SubwayVertex(String station)
	{
		this._16 = station;
		_15 = new ArrayList<Edge>();
	} 
	
	public void createEdge(SubwayVertex dst, int weight, int transfer)
	{
		Edge e = new Edge(dst, new EdgeWeight(weight, transfer));
		_15.add(e);
	}

	
	public EdgeWeight _17 = new EdgeWeight(0,0);
	private SubwayVertex _18 = null;
	
	@Override
	public boolean _30(DijkstraVertex factor, int option) {
		boolean _19 = false;
		if(option == 1)
			_19 = true;
		SubwayVertex v = (SubwayVertex)factor;
		EdgeWeight w = null;
		for(Edge e : v._15)
			if(e.dst == this)
			{
				w = e.weight.clone();
				break;
			}
		if(_19)	// minimum transfer
			w.sum(v._17);
		else			// if not, don't consider transfer value
			w.sumDistance(v._17);
		if(_17.compareTo(w, !_19) > 0)
		{
			_17 = w;
			_18 = v;
			return true;
		}
		else
			return false;
	}

	@Override
	public DijkstraVertex _31() {
		return _18;
	}

	@Override
	public void _32() {
		_17._20 = _17._21 = Integer.MAX_VALUE;
	}

	@Override
	public void _33() {
		_17._20 = _17._21 = 0;
	}

	@Override
	public Collection<DijkstraVertex> _34() {
		ArrayList<DijkstraVertex> ret = new ArrayList<DijkstraVertex>();
		for(Edge e : _15)
			ret.add(e.dst);
		return ret;
	}

	@Override
	public int compareTo(DijkstraVertex o) {
		return _17.compareTo(((SubwayVertex)o)._17);
	}
}

class Edge {
	public SubwayVertex dst;
	public EdgeWeight weight;
	
	public Edge(SubwayVertex dst, EdgeWeight weight)
	{
		this.dst = dst;
		this.weight = weight;
	}
}




