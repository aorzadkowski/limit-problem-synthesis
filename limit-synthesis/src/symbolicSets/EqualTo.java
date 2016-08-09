package symbolicSets;

import hierarchy.*;
import java.util.HashMap;

public class EqualTo extends Inequality 
{
	private Expression _variable;
	private Expression _numericExpression;
	
	public EqualTo(Expression variable, Expression numericExpression)
	{
		_variable= variable;
		_numericExpression= numericExpression;
	}
	
	public boolean contains(double value)
	{
		HashMap map = new HashMap<Variable, Double>();
		map.put(_variable, value);
		
		return (((Double)value).equals(_numericExpression.evaluate(map)));
	}
	
	public String toString()
	{
		return "("+ _variable.unParse() + "==" + _numericExpression.toWolf() + ")";
	}
	
}
