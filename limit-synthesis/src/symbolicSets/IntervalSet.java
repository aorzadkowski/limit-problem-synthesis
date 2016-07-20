package symbolicSets;

public abstract class IntervalSet 
{
	public abstract boolean contains(double aDouble); //returns true if aDouble lies on this interval.
	public abstract double getFrom();				//returns the double value _from, the starting point of every IntervalSet.
	public abstract boolean equals(IntervalSet intSet);	//returns true if intSet has the same _from and _to and is the same IntervalSet type.
}
