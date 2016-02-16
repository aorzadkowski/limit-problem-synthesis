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
}