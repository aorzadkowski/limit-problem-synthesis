package symbolicSets;

import java.util.ArrayList;
import java.util.HashMap;

import hierarchy.*;
import hierarchy.Number;

public class ElementOf extends ElementStatements {

	private Expression _expr;	
	private NumberSystem _system;
	
	public ElementOf(Expression expr, NumberSystem system)
	{
		_expr = expr;
		_system = system;
	}
	
	@Override
	public ArrayList<Expression> findInterestingPoints() 
	{
		ArrayList<Expression> result = new ArrayList<Expression>();
		
		if(_system instanceof Reals)
		{
			result.add(new Number(0));
		}
		else
		{
			HashMap<Variable, Double> variableMap = new HashMap<Variable, Double>();
			variableMap.put(_expr.getVariable(), 0.0);
			if(_expr.evaluate(variableMap) == (int)_expr.evaluate(variableMap))
			{
				result.add(new Number(0));
			}
			else
			{
				variableMap.clear();
				variableMap.put(_expr.getVariable(), 1.0);
				if(_expr.evaluate(variableMap) == (int)_expr.evaluate(variableMap))
				{
					result.add(new Number(1));
				}
			}
		}
		return result;
	}
	
	@Override
	public ArrayList<Expression> findLeftInterestingPoints() 
	{
		ArrayList<Expression> result = new ArrayList<Expression>();
		
		if(_system instanceof Reals)
		{
			result.add(new Number(0));
		}
		else
		{
			HashMap<Variable, Double> variableMap = new HashMap<Variable, Double>();
			variableMap.put(_expr.getVariable(), 0.0);
			if(_expr.evaluate(variableMap) == (int)_expr.evaluate(variableMap))
			{
				result.add(new Number(0));
			}
			else
			{
				variableMap.clear();
				variableMap.put(_expr.getVariable(), 1.0);
				if(_expr.evaluate(variableMap) == (int)_expr.evaluate(variableMap))
				{
					result.add(new Number(1));
				}
			}
		}
		return result;
	}
	
	@Override
	public ArrayList<Expression> findRightInterestingPoints() 
	{
		ArrayList<Expression> result = new ArrayList<Expression>();
		
		if(_system instanceof Reals)
		{
			result.add(new Number(0));
		}
		else
		{
			HashMap<Variable, Double> variableMap = new HashMap<Variable, Double>();
			variableMap.put(_expr.getVariable(), 0.0);
			if(_expr.evaluate(variableMap) == (int)_expr.evaluate(variableMap))
			{
				result.add(new Number(0));
			}
			else
			{
				variableMap.clear();
				variableMap.put(_expr.getVariable(), 1.0);
				if(_expr.evaluate(variableMap) == (int)_expr.evaluate(variableMap))
				{
					result.add(new Number(1));
				}
			}
		}
		return result;
	}
	
	@Override
	public boolean contains(double value) 
	{
		if(_expr.getVariable() != null)
		{
			HashMap<Variable, Double> map = new HashMap<Variable, Double>();
			map.put(_expr.getVariable(), value);
			
			return _system.contains(_expr.evaluate(map));
		}

		return false;
	}

	@Override
	public String toString() 
	{
		return "("+ _expr.toWolf() + " ? " + _system.toString() + ")";
	}

}
