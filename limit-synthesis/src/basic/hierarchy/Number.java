//One of two possible ends to a syntax tree of an expression
//represents a double
package basic.hierarchy;

import java.util.Map;

public class Number implements Expression
{
    private double _num = 0;
    private boolean _init = false;

    public Number(double aNum)
    {
        _num = aNum;
        _init = true;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_init == false) throw new IllegalArgumentException("This Number has not been initialized");
        return _num;
    }
    
    public void append(Expression e)
    {
        
    }
    @Override
	public String getExpression() {
		return "" + _num;
	}  
}
