//an interface for basic operations that require two expressions
package basic.hierarchy;

import java.util.Map;

public interface BinaryOperator extends Expression    
{
    public double evaluate(Map<Variable,Double> variableMap);
    public void append(Expression e);
}
