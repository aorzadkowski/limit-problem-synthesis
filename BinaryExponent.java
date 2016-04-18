//Class which represents raising the _exp1 to the power of _exp2. Requires two expressions to function.
package hierarchy;

import java.util.Map;

public class BinaryExponent implements Expression, BinaryOperator 
{
	private Expression _exp1 = null;
    private Expression _exp2 = null;
    
    public BinaryExponent(Expression e1, Expression e2)
    {
        _exp1= e1;
        _exp2= e2;
        
    }
    
	@Override
	public double evaluate(Map<Variable, Double> variableMap) throws IllegalArgumentException
	{
		 if(_exp1 == null || _exp2 == null) throw new IllegalArgumentException("This Plus has not been initialized");
	        
	     return Math.pow(_exp1.evaluate(variableMap), _exp2.evaluate(variableMap));
	}

	@Override
	public void append(Expression e) 
	{
		

	}

	@Override
	public String unParse() 
	{
		String str = "(" + _exp1.unParse() + "^" + _exp2.unParse() + ")";
		return str;
	}

	public String toWolf() 
	{
		String str = "(" + _exp1.toWolf() + "^" + _exp2.toWolf() + ")";
		return str;
	}

}
