package symbolicSets;

import java.util.HashMap;

import hierarchy.*;

public class ElementOf extends ElementStatements {

	private Expression _expr;	
	private NumberSystem _system;
	
	public ElementOf(Expression expr, NumberSystem system)
	{
		_expr = expr;
		_system = system;
	}
	
	@Override
	public boolean contains(double value) 
	{
		if(_expr.getVariable() != null)
		{
			HashMap map = new HashMap<Variable, Double>();
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
