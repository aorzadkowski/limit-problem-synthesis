//Class which represents Multiplication. Requires two expressions to evaluate.
package hierarchy;

import java.util.Map;

public class BinaryMult implements BinaryOperator
{
    private Expression _exp1 = null;
    private Expression _exp2 = null;
    
    public BinaryMult(Expression e1, Expression e2)
    {
        _exp1= e1;
        _exp2= e2;
        
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp1 == null || _exp2 == null) throw new IllegalArgumentException("This Mult has not been initialized");
        
        return _exp1.evaluate(variableMap) * _exp2.evaluate(variableMap);
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
    	return (result && _exp1.isContinuousAt(variableMap) && _exp2.isContinuousAt(variableMap));
    }
    
    public String unParse()
    {
    	String str = "(" + _exp1.unParse() + "*" + _exp2.unParse() + ")";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = "(" + _exp1.toWolf() + "*" + _exp2.toWolf() + ")";
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