//Class which represents raising an expression to the 1/2 power. 
//Requires only one expression to function.
package basic.hierarchy;

import java.util.Map;

public class UnarySquareRoot implements UnaryOperator
{
    private Expression _exp = null;
    
    public UnarySquareRoot(Expression e)
    {
        _exp = e;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This SQRT has not been initialized");
        
        return Math.pow(_exp.evaluate(variableMap),1/2);
    }
    public void append(Expression e)
    {
        _exp = e;
    }
}