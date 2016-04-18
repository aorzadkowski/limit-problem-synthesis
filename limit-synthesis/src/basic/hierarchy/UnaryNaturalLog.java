//Class which represents log base e of an expression.
//Requires only 1 expression
package hierarchy;

import java.util.Map;

public class UnaryNaturalLog implements UnaryOperator {

    private Expression _exp = null;
    
    public UnaryNaturalLog(Expression e)
    {
        _exp = e;
    }
        
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp == null) throw new IllegalArgumentException("This NaturalLog has not been initialized");
        
        return Math.log(_exp.evaluate(variableMap));
    }
    public void append(Expression e)
    {
        _exp = e;
    }
    
    public String unParse()
    {
    	String str = "(ln(" + _exp.unParse() + "))";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = "(Log[" + _exp.toWolf() + "])";
    	return str;
    }
}
