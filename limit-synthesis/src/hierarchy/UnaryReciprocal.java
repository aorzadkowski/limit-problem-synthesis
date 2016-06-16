//Class which represents an multiplicative inverse value of an expression.
//Requires only 1 expression
package hierarchy;

import java.util.Map;

public class UnaryReciprocal implements UnaryOperator {

    private Expression _exp = null;
    
    public UnaryReciprocal(Expression e)
    {
        _exp = e;
    }
        
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This Reciprocal has not been initialized");
        
        return 1/(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }
    
    @Override
    public boolean isContinuousAt(Map<Variable,Double> variableMap)
    {
    	boolean result = true;
    	if(_exp.evaluate(variableMap) == 0)
    	{
    		result = false;
    	}
    	return (result && _exp.isContinuousAt(variableMap));
    }
    
    public String unParse()
    {
    	String str = "(reciprocal("+  _exp.unParse() + "))";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = "(1/" + _exp.toWolf() + ")";
    	return str;
    }

	@Override
	public Expression getNextExpression() {
		// TODO Auto-generated method stub
		return _exp;
	}
}
