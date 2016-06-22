//interface for basic functions that require one expression
package hierarchy;

import java.util.Map;

public interface UnaryOperator extends Expression
{
    public double evaluate(Map<Variable,Double> variableMap);
    public Expression getExp();//these 2 methods set/get the member expressions of this Unary Operator.
    public void setExp(Expression anExp);
}
