package symbolicSets;

import hierarchy.*;
import hierarchy.Number;

import java.util.Random;
import java.util.ArrayList;

public class Domain 
{
	private LogicStatements _conditions;
	private ArrayList<hierarchy.Expression> _interestingPoints;
	private ArrayList<hierarchy.Expression> _LeftSidedInterestingPoints;
	private ArrayList<hierarchy.Expression> _RightSidedInterestingPoints;

	public Domain()
	{
		_conditions = new True();
		_interestingPoints = _conditions.findInterestingPoints();
		_LeftSidedInterestingPoints = _conditions.findLeftInterestingPoints();
		_RightSidedInterestingPoints = _conditions.findRightInterestingPoints();
		
	}
	
	public Domain(LogicStatements conditions)
	{
		_conditions = conditions;
		_interestingPoints = _conditions.findInterestingPoints();
		_LeftSidedInterestingPoints = _conditions.findLeftInterestingPoints();
		_RightSidedInterestingPoints = _conditions.findRightInterestingPoints();
	}
	
	//do we need this
	public Domain(LimitExpression limExp)
	{
		determineDomain(limExp);
	}
	
	//DO WE NEED THIS?
	public void determineDomain(LimitExpression limExp)
	{
		System.err.println("METHOD NOT YET SUPPORTED: DETERMINEDOMAIN--------------------");
	}
	
	public boolean isOnDomain(double value)
	{
		return _conditions.contains(value);
	}
	
	public boolean validate()
	{
		if(_conditions instanceof True)
		{
			return false;
		}
		if(_conditions instanceof False)
		{
			return false;
		}
		
		return true;
	}
	
	public String toString()
	{
		String str = "{";
		str+= _conditions.toString();
		str+= "}";
		
		return str;
	}
	
	public Expression getAnInterestingPoint()
	{
		Random gen = new Random();
		if(!_interestingPoints.isEmpty())
		{
			//If interesting points is already filled with values, just pick one at random
			return _interestingPoints.get(gen.nextInt(_interestingPoints.size()-1));
		}
		
		_interestingPoints = _conditions.findInterestingPoints();
		if(_interestingPoints.size() == 1)
		{
			return _interestingPoints.get(0);
		}
		else if(_interestingPoints.size() == 0)
		{
			return new Number(-1);
		}
		else
		{
			return _interestingPoints.get(gen.nextInt(_interestingPoints.size()-1));
		}
	}
	
	public ArrayList<hierarchy.Expression> getAllInterestingPoints()
	{
		if(_interestingPoints.isEmpty())
		{
			_interestingPoints = _conditions.findInterestingPoints();
		}
		
		return _interestingPoints;
	}
	
	public ArrayList<hierarchy.Expression> getAllLeftInterestingPoints()
	{
		if(_interestingPoints.isEmpty())
		{
			_LeftSidedInterestingPoints = _conditions.findLeftInterestingPoints();
			_RightSidedInterestingPoints = _conditions.findRightInterestingPoints();
		}
			
		return _LeftSidedInterestingPoints;
	}
	
	public ArrayList<hierarchy.Expression> getAllRightInterestingPoints()
	{
		if(_interestingPoints.isEmpty())
		{
			_LeftSidedInterestingPoints = _conditions.findLeftInterestingPoints();
			_RightSidedInterestingPoints = _conditions.findRightInterestingPoints();
		}
			
		return _RightSidedInterestingPoints;
	}
	
	//I don't believe we need this if we use Mathematica.
//	public Domain intersection(Domain dom1, Domain dom2)
//	{
//		if(dom1 == ALL_REALS)
//		{
//			return dom2;
//		}
//		else if(dom2 == ALL_REALS)
//		{
//			return dom1;
//		}
//		else
//		{
//			
//		}
//		
//		
//		return null;
//	}
//	public void add(IntervalSet set)
//	{
//		if(_domainSet.size() ==0)
//		{
//			_domainSet.add(set);
//		}
//		else if(_domainSet.get(0).getFrom() >= set.getFrom())
//		{
//			_domainSet.add(0, set);
//		}
//		else if(set.getFrom() > _domainSet.get(_domainSet.size()-1).getFrom())
//		{
//			_domainSet.add(set);
//		}
//		else
//		{
//			for(int i = 0; i < _domainSet.size(); i++)
//			{
//				if(_domainSet.get(i).getFrom() >= set.getFrom())
//				{
//					_domainSet.ensureCapacity(_domainSet.size()+1);
//					_domainSet.add(i, set);
//					break;
//				}
//			}
//		}	
//	}
	
//	public IntervalSet getIntervalSetAt(int index)
//	{
//		return _domainSet.get(index);
//	}
	
//	public void clearDomainSet()
//	{
//		_domainSet = new ArrayList<IntervalSet>();
//	}
}
