package symbolicSets;

import java.util.ArrayList;

import hierarchy.Expression;

public class False extends LogicStatements {

	@Override
	public boolean contains(double value) 
	{
		return false;
	}

	@Override
	public String toString() 
	{
		return "False";
	}

	@Override
	public ArrayList<Expression> findInterestingPoints() 
	{	
		return new ArrayList<Expression>();
	}
	
	@Override
	public ArrayList<Expression> findLeftInterestingPoints() 
	{	
		return new ArrayList<Expression>();
	}
	
	@Override
	public ArrayList<Expression> findRightInterestingPoints() 
	{	
		return new ArrayList<Expression>();
	}

}
