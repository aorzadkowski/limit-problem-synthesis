<<<<<<< HEAD
//Class which represents the sine of an expression. Requires only one expression to function.
package basic.hierarchy;

import java.util.Map;

public class UnarySin implements UnaryOperator
{
    private Expression _exp = null;
    
    public UnarySin(Expression e)
    {
        _exp = e;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This Sin has not been initialized");
        
        return Math.sin(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }
    @Override
	public String getExpression() {
		return "sin";
	}  
    
	@Override
	public Expression getNextExpression() {
		// TODO Auto-generated method stub
		return _exp;
	}  
}
=======
//Class which represents the sine of an expression. Requires only one expression to function.
package hierarchy;

import java.util.Map;

public class UnarySin implements UnaryOperator {

    private Expression _exp = null;
    
    public UnarySin(Expression e)
    {
        _exp = e;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This Sin has not been initialized");
        
        if((_exp.evaluate(variableMap) == 0) || ((_exp.evaluate(variableMap)%(Math.PI) == 0)))
        {
        	return 0.0;
        }
        
        return Math.sin(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }
    
    @Override
    public boolean isContinuousAt(Map<Variable,Double> variableMap)
    {
    	boolean result = true;
    	return (result && _exp.isContinuousAt(variableMap));
    }
    
    public String unParse()
    {
    	String str = "(sin(" + _exp.unParse()+ "))";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = "(Sin[" + _exp.toWolf()+ "])";
    	return str;
    }
}
>>>>>>> origin/master
