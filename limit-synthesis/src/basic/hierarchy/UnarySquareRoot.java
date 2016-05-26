//Class which represents raising an expression to the 1/2 power. 
//Requires only one expression to function.
package hierarchy;

import java.util.Map;

public class UnarySquareRoot implements UnaryOperator {

    private Expression _exp = null;
    
    public UnarySquareRoot(Expression e)
    {
        _exp = e;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This SQRT has not been initialized");
        
        return Math.sqrt(_exp.evaluate(variableMap));
        
    }
    public void append(Expression e)
    {
        _exp = e;
    }
    
    @Override
    public boolean isContinuousAt(Map<Variable,Double> variableMap)
    {
    	boolean result = true;
    	if(_exp.evaluate(variableMap) < 0)
    	{
    		result = false;
    	}
    	return (result && _exp.isContinuousAt(variableMap));
    }
    
    public String unParse()
    {
    	String str = "(sqrt(" + _exp.unParse() + "))";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = "(Sqrt[" + _exp.toWolf() + "])";
    	return str;
    }
}
