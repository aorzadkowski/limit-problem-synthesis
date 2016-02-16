//Class which represents the cosine of an expression. Requires only one expression to function.
package basic.hierarchy;

import java.util.Map;

public class UnaryCos implements UnaryOperator
{
    private Expression _exp = null;
    
    public UnaryCos(Expression e)
    {
        _exp = e;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This Cos has not been initialized");
        
        return Math.cos(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }
}