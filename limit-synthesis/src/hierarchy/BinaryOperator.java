//an interface for basic operations that require two expressions
package hierarchy;

import java.util.Map;

public interface BinaryOperator extends Expression    
{
    public double evaluate(Map<Variable,Double> variableMap);
    public Expression getExp1();//these 4 methods set/return the member Expressions of this BinaryOperator.
    public Expression getExp2();
    public void setExp1(Expression anExp);
    public void setExp2(Expression anExp);
}