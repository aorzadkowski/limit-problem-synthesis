package symbolicSets;

import java.util.ArrayList;
import hierarchy.*;

public abstract class LogicStatements 
{
	public abstract boolean contains(double value);
	public abstract ArrayList<Expression> findInterestingPoints();
	public abstract ArrayList<Expression> findLeftInterestingPoints();
	public abstract ArrayList<Expression> findRightInterestingPoints();
	public abstract String toString();
}
