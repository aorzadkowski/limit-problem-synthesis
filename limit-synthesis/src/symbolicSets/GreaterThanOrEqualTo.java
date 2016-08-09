package symbolicSets;

import java.util.HashMap;

import hierarchy.Expression;
import hierarchy.Variable;

public class GreaterThanOrEqualTo extends Inequality 
{
	private Expression _variable;
	private Expression _numericExpression;
	
	public GreaterThanOrEqualTo(Expression variable, Expression numericExpression)
	{
		_variable= variable;
		_numericExpression= numericExpression;
	}
	
	public boolean contains(double value)
	{
		HashMap map = new HashMap<Variable, Double>();
		map.put(_variable, _numericExpression);
		
		return value >= _numericExpression.evaluate(map);
	}
	
	public String toString()
	{
		return "("+ _variable.unParse() + ">=" + _numericExpression.toWolf() + ")";
	}
}
