package symbolicSets;

import java.util.ArrayList;

public abstract class ElementStatements extends RestrictionStatements 
{
	public abstract boolean contains(double value);
	public abstract String toString();
}
