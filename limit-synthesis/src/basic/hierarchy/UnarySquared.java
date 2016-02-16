//Class which represents raising an expression to the second power. 
//Requires only one expression to function.
package basic.hierarchy;

import java.util.Map;

public class UnarySquared implements UnaryOperator
{
    private Expression _exp = null;
    
    public UnarySquared(Expression e)
    {
        _exp = e;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This Squared has not been initialized");
        
        return (_exp.evaluate(variableMap))*(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }
}