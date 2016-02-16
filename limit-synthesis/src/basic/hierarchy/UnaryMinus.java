//Class which represents an additive opposite value of an expression.
//Requires only 1 expression
package basic.hierarchy;

import java.util.Map;

public class UnaryMinus implements UnaryOperator
{
    private Expression _exp = null;
    
    public UnaryMinus(Expression e)
    {
        _exp = e;
    }
        
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This UnaryMinus has not been initialized");
        
        return 0-(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }    
}
