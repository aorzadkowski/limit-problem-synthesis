<<<<<<< HEAD
//Class which represents an expression.
//Requires only 1 expression
package basic.hierarchy;

import java.util.Map;

public class UnaryPlus implements UnaryOperator
{
    private Expression _exp = null;
    
    public UnaryPlus(Expression e)
    {
        _exp = e;
    }
        
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This UnaryPlus has not been initialized");
        
        return (_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }    
    @Override
	public String getExpression() {
		return "(+)";
	}  
	@Override
	public Expression getNextExpression() {
		// TODO Auto-generated method stub
		return _exp;
	}  

}
=======
//Class which represents an expression.
//Requires only 1 expression
package hierarchy;

import java.util.Map;

public class UnaryPlus implements UnaryOperator {

    private Expression _exp = null;
    
    public UnaryPlus(Expression e)
    {
        _exp = e;
    }
        
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This UnaryPlus has not been initialized");
        
        return (_exp.evaluate(variableMap));
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
    	String str = "(" + _exp.unParse() + ")";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = _exp.toWolf();
    	return str;
    }
}
>>>>>>> origin/master
