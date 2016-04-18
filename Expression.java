//Most general interface of the hierarchy
package hierarchy;

import java.util.Map;

public interface Expression 
{
    public abstract double evaluate(Map<Variable,Double> variableMap);
    public abstract void append(Expression e);
    public abstract String toWolf(); //Translates this expression into a form understandable by Mathematica.
    public abstract String unParse(); // Provides a String representation of the Expression which is easy to parse. 
    								  //It is used for dedugging purposes.
}
