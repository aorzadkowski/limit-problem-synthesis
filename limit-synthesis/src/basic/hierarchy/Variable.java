//The other of two possible ends to a syntax tree of an expression
//A class which represents a variable. 
//(Provide a given value by providing it in the constructor
// or by mapping this variable to a given value)
package hierarchy;

import java.util.Map;

public class Variable implements Expression
{
    private double _given;
    private String _name;

    public Variable()
    {
        _given = 1.0;
        _name = "x";
    }
    public Variable(double aGiven)
    {
        _given = aGiven;
        _name = "x";
    }
    public Variable(String name)
    {
    	_given = 1.0;
    	_name = name;
    }
    
    public String getName()
    {
    	return _name;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) 
    {
        return variableMap.get(this);
    }
    
    public void append(Expression e)
    {

    }
    
    @Override
    public boolean isContinuousAt(Map<Variable,Double> variableMap)
    {
    	return true;
    }

    public String unParse()
    {
    	return _name;
    }
    
    public String toWolf()
    {
    	return _name;
    }
}
