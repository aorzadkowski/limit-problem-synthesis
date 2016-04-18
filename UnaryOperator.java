//interface for basic functions that require one expression
package hierarchy;

import java.util.Map;

public interface UnaryOperator extends Expression
{
    public double evaluate(Map<Variable,Double> variableMap);
    public void append(Expression e);
}
