//Class which represents division. Requires two expressions to function.
package basic.hierarchy;

import java.util.Map;

public class BinaryDivideBy implements BinaryOperator
{
    private Expression _exp1 = null;
    private Expression _exp2 = null;
    
    public BinaryDivideBy(Expression dividend, Expression divisor)
    {
        _exp1= dividend;
        _exp2= divisor;
        
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp1 == null || _exp2 == null) throw new IllegalArgumentException("This BinaryDivision has not been initialized");
                
        return _exp1.evaluate(variableMap) / _exp2.evaluate(variableMap);
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
}
