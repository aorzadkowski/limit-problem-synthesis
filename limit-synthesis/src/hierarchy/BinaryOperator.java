//an interface for basic operations that require two expressions
package hierarchy;

import java.util.Map;

public interface BinaryOperator extends Expression    
{
    public double evaluate(Map<Variable,Double> variableMap);
    public void append(Expression e);
	public Expression getLeftExpression();
	public Expression getRightExpression();
}