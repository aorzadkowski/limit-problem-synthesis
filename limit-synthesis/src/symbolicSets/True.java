package symbolicSets;

import java.util.ArrayList;

import hierarchy.Expression;

public class True extends LogicStatements {

	@Override
	public boolean contains(double value) 
	{	
		return true;
	}

	@Override
	public String toString() 
	{
		return "True";
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
