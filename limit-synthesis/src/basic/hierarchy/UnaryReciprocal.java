//Class which represents an multiplicative inverse value of an expression.
//Requires only 1 expression
package basic.hierarchy;

import java.util.Map;

public class UnaryReciprocal implements UnaryOperator
{
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
}