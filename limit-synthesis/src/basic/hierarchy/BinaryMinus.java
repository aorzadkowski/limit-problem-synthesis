<<<<<<< HEAD
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
    
	@Override
	public Expression getLeftExpression() {
		// TODO Auto-generated method stub
		return _exp1;
	}

	@Override
	public Expression getRightExpression() {
		// TODO Auto-generated method stub
		return _exp2;
	} 
	
	@Override
	public String getExpression() {
		return "-";
	}  
}
=======
//Class which represents subtraction. Requires two expressions to function.
package hierarchy;

import java.util.Map;

public class BinaryMinus implements BinaryOperator {

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
    
    @Override
    public boolean isContinuousAt(Map<Variable,Double> variableMap)
    {
    	boolean result = true;
    	return (result && _exp1.isContinuousAt(variableMap) && _exp2.isContinuousAt(variableMap));
    }
    
    public String unParse()
    {
    	String str = "(" + _exp1.unParse() + "-" + _exp2.unParse() + ")";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = "(" + _exp1.toWolf() + "-" + _exp2.toWolf() + ")";
    	return str;
    }
}
>>>>>>> origin/master
