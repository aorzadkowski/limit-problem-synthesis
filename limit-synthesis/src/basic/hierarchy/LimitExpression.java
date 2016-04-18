package hierarchy;

import java.util.Map;

public class LimitExpression 
{
	private int _LRB; //Whether this limit expression is a left-handed(1), right-handed(-1), or both(0);
	private final int LEFT = 1;
	private final int RIGHT = -1;
	private final int BOTH = 0;
	
	private Variable _variable; //the variable which approaches the target value;
	private Number _target; //the target value;
	
	private Expression _function; //the function.
	
	public LimitExpression()
	{
		_LRB = BOTH;
		_variable = (Variable)null;
		_target = (Number)null;
		_function = (Expression)null;
	}
	
	public LimitExpression(String stringRepresentation, Expression aFunction)
	{
		//The stringRepresentation should be in the following format:
		//lim _variable>_LRB _target _function
		
		//A variable, by our definition, is one alphabetical character.
		_variable = new Variable(stringRepresentation.substring(4, 5));
		//The determining character for _LRB is at index 6.
		if(stringRepresentation.charAt(6) == '+')
		{
			_LRB = RIGHT;
		}
		else if(stringRepresentation.charAt(6) == '-')
		{
			_LRB = LEFT;
		}
		else
		{
			_LRB = BOTH;
		}
		//now to determine _target which is the remainder of the string. 
		if(_LRB == BOTH)
		{
			_target = new Number(Double.parseDouble(stringRepresentation.substring(7, stringRepresentation.length()-1)));
		}
		else
		{
			_target = new Number(Double.parseDouble(stringRepresentation.substring(8, stringRepresentation.length()-1)));
		}
		_function = aFunction;		
	}
	
	//Evaluates this limit expression without the limit
	public double evaluateAsExpression(Map<Variable,Double> variableMap)
	{		
		return _function.evaluate(variableMap);
	}
	
	//This method will eventually solve the limit expression
	public double evaluate()
	{
		return 0;
	}
	
	//Wolfram expects a limit expression to look like this:
	//Limit[expr,x->x0,Direction->1] (this is a "below" or left-handed limit)
	//Limit[expr,x->x0,Direction->-1] (this is an "above" or right-handed limit)
	//Limit[expr,x->x0] (this is a two-sided limit (_LRB == BOTH))
	
	//This method returns a string representation of the wolfram limit format
	public String translateToWolfram()
	{
		if(_LRB == BOTH)
		{
			return "Limit[" + _function.toWolf() + "," + _variable.toWolf() + "->" + _target.toWolf() + "]";
		}
		else
		{
			return "Limit[" + _function.toWolf() + ", " + _variable.toWolf() + "->" + _target.toWolf() + ", Direction->" + _LRB + "]";
		}
	}
	
	public Variable getVariable()
	{
		return _variable;
	}
	public void setVariable(Variable newVariable)
	{
		_variable = newVariable;
	}
	
	public String unParse()
	{
		String str = "";
		if(_LRB == BOTH)
		{
			str = "lim " + _variable.unParse() + "> " + _target.unParse() + " " + _function.unParse(); 
		}
		else if(_LRB == LEFT)
		{
			str = "lim " + _variable.unParse() + ">- " + _target.unParse() + " " + _function.unParse();
		}
		else
		{
			str = "lim " + _variable.unParse() + ">+ " + _target.unParse() + " " + _function.unParse();
		}
		return str;
	}
}
