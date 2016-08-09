package symbolicSets;

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
}
