//Most general interface of the hierarchy
package hierarchy;

import java.util.Map;
import symbolicSets.*;
import java.util.*;

public interface Expression 
{
    public abstract double evaluate(Map<Variable,Double> variableMap);//given a value for a variable, this method returns 
    																	//the value of the evaluated expression.
    public abstract boolean isContinuousAt(Map<Variable,Double> variableMap);//Returns true if
    																			//an Expression is continuous at a given point.
    																			//Also checks to see if the member expressions are
    																			//continuous at that point.
    public abstract String toWolf(); //Translates this expression into a form understandable by Mathematica.
    public abstract String unParse(); // Provides a String representation of the Expression which is easy to parse. 
    								  //It is used for dedugging purposes.
    
    public abstract String getClassName();//returns the name of the class. For example, when used on
    										//the BinaryDivideBy class, this method returns "BinaryDivideBy".
    
    public abstract int size(); //returns the number of nodes contained in this expression. (the sum of operators and operands)
    
    public abstract String getExpType(); //returns Binary, Unary, Number, or Variable, the Expression Type of this object.
    
    public abstract void reReference();//calling this method makes all PreviousOperator methods more accurate.
    
    public abstract void setPreviousOperator(Expression e);//PreviousOperator methods allow us to more easily traverse the tree.
    
    public abstract Expression getPreviousOperator();	//returns the Operator to which "this" is a member Expression.
    
    public abstract void setLocationRelativeToPreviousOperator(int i); 	//sets the location relative to the previousOperator.
    public abstract int getLocationRelativeToPreviousOperator(); 		//returns -1 if this is the root.
    													//returns 0 if previousOperator is Unary
    													//returns 1 if this is the Exp1 of the previousOperator.
    													//returns 2 if this is the Exp2 of the previousOperator.
    public abstract void mutateExpressionOf(LimitExpression limExp); //gives this Expression a chance to be mutated 1 of three
    																//3 different ways: Expansion, Regression, or Substitution
    																//After mutating this Expression, this method mutates any
    																//member expressions as well. 
    
    public abstract void expandExpressionOf(LimitExpression limExp);  //mutateExpressionOf determines which of these three 
    public abstract void regressExpressionOf(LimitExpression limExp); 		//should be called. 
    public abstract void substituteExpressionOf(LimitExpression limExp);
 
    public abstract boolean validate(); //returns true if this Expression is a valid case*.
    								//returns false if this Expression is not a valid case.
    								
    								//*Valid cases refer to the trees we want to allow in an individual.
    								//For example we do not want to allow 2.0 * 2.0 because it can be easily simplified to 4.0.
    								//See LimitExpression constants for more information.
    public abstract Expression deepCopy(); //returns a "deepCopy" of this expression. This method traverses the Expression tree.
    
    //public abstract IntervalSet postfixDetermineDomain(); //returns the domainSet of this Expression. This method traverses
    														//this Expression tree in postfix.
    
    public abstract boolean equals(Expression other);
    public abstract String getExpressionString();
}
