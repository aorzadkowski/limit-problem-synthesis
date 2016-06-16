//Class which represents division. Requires two expressions to function.
package hierarchy;

import java.util.*;

public class BinaryDivideBy implements BinaryOperator
{
    private Expression _exp1 = null;
    private Expression _exp2 = null;
    
    public BinaryDivideBy(Expression dividend, Expression divisor)
    {
        _exp1= dividend;
        _exp2= divisor;
        
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp1 == null || _exp2 == null) throw new IllegalArgumentException("This BinaryDivision has not been initialized");
                
        return _exp1.evaluate(variableMap) / _exp2.evaluate(variableMap);
    }
    public void append(Expression e)
    {
        if(_exp1 != null)
        {
            _exp2 = e;
        }
        else
        {
            _exp1 = e;
        }
    }
    
    @Override
    public boolean isContinuousAt(Map<Variable,Double> variableMap)
    {
    	boolean result = true;
    	if(_exp2.evaluate(variableMap) == 0)
    	{
    		result = false;
    	}
    	return (result && _exp1.isContinuousAt(variableMap) && _exp2.isContinuousAt(variableMap));
    }
    
    public String unParse()
    {
    	String str = "(" + _exp1.unParse() + "/" + _exp2.unParse() + ")";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = "(" + _exp1.toWolf() + "/" + _exp2.toWolf() + ")";
    	return str;
    }

	@Override
	public Expression getLeftExpression() {
		return _exp1;
	}

	@Override
	public Expression getRightExpression() {
		return _exp2;
	}
}
