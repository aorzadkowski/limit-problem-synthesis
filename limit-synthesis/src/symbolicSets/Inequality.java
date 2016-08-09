package symbolicSets;

public abstract class Inequality extends RestrictionStatements
{
	public abstract boolean contains(double value);
	public abstract String toString();
}
