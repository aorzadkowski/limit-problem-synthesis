package symbolicSets;

import java.util.ArrayList;

import hierarchy.Expression;

public class And extends LogicStatements 
{
	private LogicStatements _condition1;
	private LogicStatements _condition2;
	
	public And(LogicStatements condition1, LogicStatements condition2)
	{
		_condition1 = condition1;
		_condition2 = condition2;
	}

	@Override
	public boolean contains(double value) 
	{
		return _condition1.contains(value) && _condition2.contains(value);
	}
	
	public String toString()
	{
		return "(" + _condition1.toString() + "&&" + _condition2.toString() + ")";
	}

	@Override
	public ArrayList<Expression> findInterestingPoints() 
	{
		ArrayList<Expression> result = new ArrayList<Expression>();
		result.addAll(_condition1.findInterestingPoints());
		
		ArrayList<Expression> con2 =_condition2.findInterestingPoints();
		
		//make sure there are no duplicates
		for(Expression exp: con2)
		{
			boolean contained = false;
			for(Expression exp2: result)
			{
				if(exp.equals(exp2))
				{
					contained = true;
					break;
				}
			}
			if(!contained)
			{
				result.add(exp);
			}
			
		}
		return result;
	}

	@Override
	public ArrayList<Expression> findLeftInterestingPoints() 
	{
		ArrayList<Expression> result = new ArrayList<Expression>();
		result.addAll(_condition1.findLeftInterestingPoints());
		
		ArrayList<Expression> con2 =_condition2.findLeftInterestingPoints();
		
		//make sure there are no duplicates
		for(Expression exp: con2)
		{
			boolean contained = false;
			for(Expression exp2: result)
			{
				if(exp.equals(exp2))
				{
					contained = true;
					break;
				}
			}
			if(!contained)
			{
				result.add(exp);
			}
			
		}
		return result;
	}

	@Override
	public ArrayList<Expression> findRightInterestingPoints() 
	{
		ArrayList<Expression> result = new ArrayList<Expression>();
		result.addAll(_condition1.findRightInterestingPoints());
		
		ArrayList<Expression> con2 =_condition2.findRightInterestingPoints();
		
		//make sure there are no duplicates
		for(Expression exp: con2)
		{
			boolean contained = false;
			for(Expression exp2: result)
			{
				if(exp.equals(exp2))
				{
					contained = true;
					break;
				}
			}
			if(!contained)
			{
				result.add(exp);
			}
			
		}
		return result;
	}
	
	
}
