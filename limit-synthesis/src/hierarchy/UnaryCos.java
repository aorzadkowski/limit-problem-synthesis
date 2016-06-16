//Class which represents the cosine of an expression. Requires only one expression to function.
package hierarchy;

import java.util.Map;

public class UnaryCos implements UnaryOperator {

    private Expression _exp = null;
    
    public UnaryCos(Expression e)
    {
        _exp = e;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This Cos has not been initialized");
        
        if((_exp.evaluate(variableMap)%(Math.PI/2) == 0) && (_exp.evaluate(variableMap)%(Math.PI) != 0))
        {
        	return 0.0;
        }
        
        return Math.cos(_exp.evaluate(variableMap));
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
    	String str = "(cos(" + _exp.unParse() + "))";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = "(Cos[" + _exp.toWolf() + "])";
    	return str;
    }

	@Override
	public Expression getNextExpression() {
		// TODO Auto-generated method stub
		return _exp;
	}

}
