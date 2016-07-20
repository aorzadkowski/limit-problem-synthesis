package symbolicSets;

public class IntervalOpen extends Interval 
{
	private double _from;
	private double _to;
	
	public IntervalOpen(double from, double to)
	{
		_from = from;
		_to = to;
	}
	
	public String toString()
	{
		return "(" + _from + "," + _to + ")";
	}
	
	public boolean contains(double aDouble)
	{
		return (_from < aDouble && aDouble < _to);
	}
	
	public double getFrom()
	{
		return _from;
	}
	
	public double getTo()
	{
		return _to;
	}
}
