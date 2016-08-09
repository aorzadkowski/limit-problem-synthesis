package symbolicSets;

import hierarchy.*;
import oldSymbolicSets.Interval;
import oldSymbolicSets.IntervalOpen;
import oldSymbolicSets.IntervalSet;
import oldSymbolicSets.Singleton;

import java.util.Random;
import java.util.ArrayList;

public class Domain 
{
	private LogicStatements _conditions;
	//public static final Domain ALL_REALS = new Domain(new IntervalOpen(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
	//public static final Domain ALL_INTEGERS = new Domain(new IntervalOpen(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
	
	public Domain()
	{
		_conditions = null;
	}
	
	public Domain(LogicStatements conditions)
	{
		_conditions = conditions;
	}
	
	public Domain(LimitExpression limExp)
	{
		determineDomain(limExp);
	}
	
	public void determineDomain(LimitExpression limExp)
	{
		
	}
	
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
	
	public boolean isOnDomain(double value)
	{
		return _conditions.contains(value);
	}
	
//	public double getAnInterestingPoint()
//	{
//		Random gen = new Random();
//		
//		int index = gen.nextInt(_domainSet.size());
//		
//		IntervalSet interestingSet = _domainSet.get(index);
//		
//		if(interestingSet instanceof Singleton)
//		{
//			return interestingSet.getFrom();
//		}
//		else
//		{
//			int leftOrRight = gen.nextInt(2);
//			
//			if(leftOrRight == 0)
//			{
//				return ((Interval)interestingSet).getFrom();
//			}
//			else
//			{
//				return ((Interval)interestingSet).getTo();
//			}
//		}
//	}
	
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
	
	public String toString()
	{
		String str = "{";
		str+= _conditions.toString();
		str+= "}";
		
		return str;
	}

}
