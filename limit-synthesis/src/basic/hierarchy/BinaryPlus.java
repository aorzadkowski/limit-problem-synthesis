//Class which represents addition. Requires two expressions to function.
package basic.hierarchy;

import java.util.Map;


public class BinaryPlus implements BinaryOperator
{
    private Expression _exp1 = null;
    private Expression _exp2 = null;
    
    public BinaryPlus(Expression e1, Expression e2)
    {
        _exp1= e1;
        _exp2= e2;
        
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp1 == null || _exp2 == null) throw new IllegalArgumentException("This Plus has not been initialized");
        
        return _exp1.evaluate(variableMap) + _exp2.evaluate(variableMap);
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
