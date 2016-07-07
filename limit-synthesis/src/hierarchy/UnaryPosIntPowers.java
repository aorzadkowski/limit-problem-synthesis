//Class which represents raising an expression to the nth power.
//where n is a positive integer
//Requires only one expression to function.

package hierarchy;

import java.util.ArrayList;
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
	public String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getExpType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Expression> toPreOrderAL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPreviousOperator(Expression e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Expression getPreviousOperator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocationRelativeToPreviousOperator(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLocationRelativeToPreviousOperator() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void mutateExpressionOf(LimitExpression limExp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Expression getExp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExp(Expression anExp) {
		// TODO Auto-generated method stub
		
	}

    
    public boolean equals(Expression other) {
    	if (other == null) return false;
    	if (!(other instanceof UnaryPosIntPowers)) return false;
    	if (other.equals(_exp)) return true;
    	return false;
    }
}
