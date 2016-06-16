<<<<<<< HEAD
//Class which represents the tangent of an expression. Requires only one expression to function.
package basic.hierarchy;

import java.util.Map;

public class UnaryTan implements UnaryOperator
{
    private Expression _exp = null;
    
    public UnaryTan(Expression e)
    {
        _exp = e;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This Tan has not been initialized");
        
        return Math.tan(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }
    @Override
	public String getExpression() {
		return "tan";
	}  
    
	@Override
	public Expression getNextExpression() {
		// TODO Auto-generated method stub
		return _exp;
	}  
}
=======
//Class which represents the tangent of an expression. Requires only one expression to function.
package hierarchy;

import java.util.Map;

public class UnaryTan implements UnaryOperator {

    private Expression _exp = null;
    
    public UnaryTan(Expression e)
    {
        _exp = e;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This Tan has not been initialized");
        
        if((_exp.evaluate(variableMap) == 0) || ((_exp.evaluate(variableMap)%(Math.PI) == 0)))
        {
        	return 0.0;
        }
        
        return Math.tan(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }
    
    @Override
    public boolean isContinuousAt(Map<Variable,Double> variableMap)
    {
    	
    	boolean result = true;
    	if(_exp.evaluate(variableMap)%(Math.PI/2) == 0 && _exp.evaluate(variableMap)%(Math.PI) != 0) //this method is more reliable than Math.cos(exp...) == 0
    	{
    		result = false;
    	}
    	return (result && _exp.isContinuousAt(variableMap));
    }
    
    public String unParse()
    {
    	String str = "(tan(" + _exp.unParse()  + "))";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = "(Tan[" + _exp.toWolf()  + "])";
    	return str;
    }

}
>>>>>>> origin/master
