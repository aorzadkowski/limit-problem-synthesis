package symbolicSets;

public class Integers extends NumberSystem 
{
	private final double MIN = .000000001;
	
	public boolean contains(double value)
	{
		if(Math.abs(((Double)value).intValue()-value) <= MIN)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String toString()
	{
		return "Integers";
	}
}
