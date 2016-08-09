package symbolicSets;

import java.util.HashMap;

import hierarchy.Expression;
import hierarchy.Variable;

public class GreaterThanGreaterThan extends Inequality {

	private Expression _numericExpression1;
	private Expression _variable;
	private Expression _numericExpression2;
	
	public GreaterThanGreaterThan(Expression numericExpression1, Expression variable, Expression numericExpression2)
	{
		_variable = variable;
		_numericExpression1 = numericExpression1;
		_numericExpression2 = numericExpression2;
	}
	
	public boolean contains(double value)
	{
		HashMap map = new HashMap<Variable, Double>();
		map.put(_variable, value);
		
		return _numericExpression1.evaluate(map) > value && value > _numericExpression2.evaluate(map);
	}
	
	public String toString()
	{
		return "("+ _numericExpression1.toWolf()+ ">" +  _variable.toWolf() + ">" + _numericExpression2.toWolf() + ")";
	}
}
