
public class EdgeWeight {
	public int _20;
	public int _21;
	
	public EdgeWeight(int distance, int transfer)
	{
		this._20 = distance;
		this._21 = transfer;
	}
	
	public void sumDistance(EdgeWeight other)
	{
		_20 += other._20;
	}
	
	public void sum(EdgeWeight other)
	{
		_20 += other._20;
		_21 += other._21;
	}
	
	public int compareTo(EdgeWeight other)
	{
		if(other == null)
			return 1;
		if(other._21 == _21)
			return _20 - other._20;
		else
			return _21 - other._21;
	}
	
	public int compareTo(EdgeWeight other, boolean noTransfer)
	{
		if(noTransfer)
			return _20 - other._20;
		else
			return compareTo(other);
	}
	
	public EdgeWeight clone()
	{
		return new EdgeWeight(_20, _21);
	}
}