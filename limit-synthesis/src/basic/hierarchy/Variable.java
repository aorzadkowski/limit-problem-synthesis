//The other of two possible ends to a syntax tree of an expression
//A class which represents a variable. 
//(Provide a given value by providing it in the constructor
// or by mapping this variable to a given value)
package basic.hierarchy;

import java.util.Map;

public class Variable implements Expression
{
    private double _given;

    public Variable()
    {
        _given = 1.0;
    }
    public Variable(double aGiven)
    {
        _given = aGiven;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) 
    {
        return variableMap.get(this);
    }
    
    public void append(Expression e)
    {

    }
}
