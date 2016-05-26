//Most general interface of the hierarchy
package hierarchy;

import java.util.Map;

public interface Expression 
{
    public abstract double evaluate(Map<Variable,Double> variableMap);//given a value for a variable, this method returns 
    																	//the value of the evaluated expression.
    public abstract void append(Expression e);//used for testing purposes.
    public abstract boolean isContinuousAt(Map<Variable,Double> variableMap);//Returns true if
    																			//an Expression is continuous at a given point.
    																			//Also checks to see if the member expressions are
    																			//continuous at that point.
    public abstract String toWolf(); //Translates this expression into a form understandable by Mathematica.
    public abstract String unParse(); // Provides a String representation of the Expression which is easy to parse. 
    								  //It is used for dedugging purposes.
}
