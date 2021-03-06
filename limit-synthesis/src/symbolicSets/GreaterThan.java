package symbolicSets;

import java.util.ArrayList;
import java.util.HashMap;

import hierarchy.Expression;
import hierarchy.Variable;

public class GreaterThan extends Inequality 
{
	private Expression _variable;
	private Expression _numericExpression;
	
	public GreaterThan(Expression variable, Expression numericExpression)
	{
		_variable= variable;
		_numericExpression= numericExpression;
	}
	
	public boolean contains(double value)
	{
		HashMap map = new HashMap<Variable, Double>();
		map.put(_variable, _numericExpression);
		
		return value > _numericExpression.evaluate(map);
	}
	
	@Override
	public ArrayList<Expression> findInterestingPoints() 
	{
		ArrayList<Expression> result = new ArrayList<Expression>();
		result.add(_numericExpression);
		return result;
	}
	
	public String toString()
	{
		return "("+ _variable.unParse() + ">" + _numericExpression.toWolf() + ")";
	}

	@Override
	public ArrayList<Expression> findLeftInterestingPoints() 
	{
		ArrayList<Expression> result = new ArrayList<Expression>();
		return result;
	}

	@Override
	public ArrayList<Expression> findRightInterestingPoints() 
	{
		ArrayList<Expression> result = new ArrayList<Expression>();
		result.add(_numericExpression);
		return result;
	}
}
