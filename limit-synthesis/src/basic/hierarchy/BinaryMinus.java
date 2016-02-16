//Class which represents subtraction. Requires two expressions to function.
package basic.hierarchy;

import java.util.*;

public class BinaryMinus implements BinaryOperator 
{
    private Expression _exp1 = null;
    private Expression _exp2 = null;
    
    public BinaryMinus(Expression subtractFrom, Expression subtractWhat)
    {
        _exp1= subtractFrom;
        _exp2= subtractWhat;
        
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp1 == null || _exp2 == null) throw new IllegalArgumentException("This Minus has not been initialized");
        
        return _exp1.evaluate(variableMap) - _exp2.evaluate(variableMap);
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
