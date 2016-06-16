//Class which represents raising an expression to the nth power.
//where n is a positive integer
//Requires only one expression to function.

package hierarchy;

import java.util.Map;

public class UnaryPosIntPowers implements UnaryOperator {

	//USE BINARY EXPONENT INSTEAD.
    private Expression _exp = null;
    private int _n;
    
    public UnaryPosIntPowers(Expression e)
    {
    	_exp = e;
    	_n = 3;
    }
    
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
    public String unParse()
    {
    	String str = "(" + _exp.unParse() + "^" + _n + ")";
    	return str;
    }
    public String toWolf()
    {
    	String str = "(" + _exp.toWolf() + "^" + _n + ")";
    	return str;
    }

	@Override
	public boolean isContinuousAt(Map<Variable, Double> variableMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Expression getNextExpression() {
		// TODO Auto-generated method stub
		return _exp;
	}
    
}
