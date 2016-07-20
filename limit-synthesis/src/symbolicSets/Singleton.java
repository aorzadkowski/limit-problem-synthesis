package symbolicSets;

public class Singleton extends IntervalSet 
{
	private double _from;
	
	public Singleton(double from)
	{
		_from = from;
	}
	
	public String toString()
	{
		return "{"+_from+"}";
	}
	
	public boolean contains(double aDouble)
	{
		Double newDouble = aDouble;
		return (newDouble.equals(_from));
	}
	
	public double getFrom()
	{
		return _from;
	}
	
	public boolean equals(IntervalSet intSet)
	{
		if(intSet instanceof Singleton)
		{
			if(intSet.getFrom() == _from)
			{
				return true;
			}
		}
		
		return false;
	}
}
