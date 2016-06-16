//Most general interface of the hierarchy
package basic.hierarchy;

import java.util.Map;

public interface Expression 
{
    public abstract double evaluate(Map<Variable,Double> variableMap);
    public abstract void append(Expression e);
    public String toString();
    public String getExpression();
}
