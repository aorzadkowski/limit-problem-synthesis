//Class which represents raising an expression to the nth power.
//where n is a positive integer
//Requires only one expression to function.
package basic.hierarchy;

import java.util.Map;

public class UnaryPosIntPowers implements UnaryOperator
{
    private Expression _exp = null;
    private int _n;
    
    
    public UnaryPosIntPowers(Expression e, int n)
    {
        _exp = e;
        _n = n;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This PosIntPow has not been initialized");
        
        return Math.pow(_exp.evaluate(variableMap),_n);
    }
    public void append(Expression e)
    {
        _exp = e;
    }
}