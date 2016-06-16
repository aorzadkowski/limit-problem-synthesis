<<<<<<< HEAD
//Class which represents log base e of an expression.
//Requires only 1 expression
package basic.hierarchy;

import java.util.Map;

public class UnaryNaturalLog implements UnaryOperator
{
    private Expression _exp = null;
    
    public UnaryNaturalLog(Expression e)
    {
        _exp = e;
    }
        
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This NaturalLog has not been initialized");
        
        return Math.log(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }    
    @Override
	public String getExpression() {
		return "ln";
	}  
    
	@Override
	public Expression getNextExpression() {
		// TODO Auto-generated method stub
		return _exp;
	}  
}
=======
//Class which represents log base e of an expression.
//Requires only 1 expression
package hierarchy;

import java.util.Map;

public class UnaryNaturalLog implements UnaryOperator {

    private Expression _exp = null;
    
    public UnaryNaturalLog(Expression e)
    {
        _exp = e;
    }
        
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This NaturalLog has not been initialized");
        
        return Math.log(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }
    
    @Override
    public boolean isContinuousAt(Map<Variable,Double> variableMap)
    {
    	boolean result = true;
    	if(_exp.evaluate(variableMap) <= 0)
    	{
    		result = false;
    	}
    	return (result && _exp.isContinuousAt(variableMap));
    }
    
    public String unParse()
    {
    	String str = "(ln(" + _exp.unParse() + "))";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = "(Log[" + _exp.toWolf() + "])";
    	return str;
    }
}
>>>>>>> origin/master
