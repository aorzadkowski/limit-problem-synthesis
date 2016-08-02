package symbolicSets;

import hierarchy.*;
import java.util.ArrayList;

public class Domain 
{
	private ArrayList<IntervalSet> _domainSet;
	public static final Domain ALL_REALS = new Domain(new IntervalOpen(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
	
	public Domain()
	{
		_domainSet = new ArrayList<IntervalSet>();
	}
	
	public Domain(IntervalSet intSet)
	{
		_domainSet = new ArrayList<IntervalSet>();
		_domainSet.add(intSet);
	}
	
	public Domain(LimitExpression limExp)
	{
		determineDomain(limExp);
	}
	
	public void determineDomain(LimitExpression limExp)
	{
		
	}
	
	public void add(IntervalSet set)
	{
		if(_domainSet.size() ==0)
		{
			_domainSet.add(set);
		}
		else if(_domainSet.get(0).getFrom() >= set.getFrom())
		{
			_domainSet.add(0, set);
		}
		else if(set.getFrom() > _domainSet.get(_domainSet.size()-1).getFrom())
		{
			_domainSet.add(set);
		}
		else
		{
			for(int i = 0; i < _domainSet.size(); i++)
			{
				if(_domainSet.get(i).getFrom() >= set.getFrom())
				{
					_domainSet.ensureCapacity(_domainSet.size()+1);
					_domainSet.add(i, set);
					break;
				}
			}
		}	
	}
	
	public Domain intersection(Domain dom1, Domain dom2)
	{
		if(dom1 == ALL_REALS)
		{
			return dom2;
		}
		else if(dom2 == ALL_REALS)
		{
			return dom1;
		}
		else
		{
			
		}
		
		
		return null;
	}
	
	public String toString()
	{
		String str = "{";
		
		for(IntervalSet intSet: _domainSet)
		{
			str+= intSet.toString() + ",";
		}
		str+= "}";
		
		return str;
	}

}
