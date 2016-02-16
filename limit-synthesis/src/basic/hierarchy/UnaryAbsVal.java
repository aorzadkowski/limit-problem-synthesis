//Class which represents the absolute value of an expression. Requires only 1 expression
package basic.hierarchy;

import java.util.Map;

public class UnaryAbsVal implements UnaryOperator
{
    private Expression _exp = null;
    
    public UnaryAbsVal(Expression e)
    {
        _exp = e;
    }
        
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This AbsoluteVal has not been initialized");
        
        return Math.abs(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }    
}
