package hierarchy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lexerAndParser.*;

public class LimitExpression 
{
	private int _LRB; //Whether this limit expression is a left-handed(1), right-handed(-1), or both(0);
	private final int LEFT = 1;
	private final int RIGHT = -1;
	private final int BOTH = 0;
	
	private Variable _variable; //the variable which approaches the target value;
	private Expression _target; //the target value;
	
	private Expression _function; //the function.
	
	public LimitExpression()
	{
		_LRB = BOTH;
		_variable = (Variable)null;
		_target = (Expression)null;
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
		
		//now to determine _target which is the remainder of the string. In order to allow for targets like pi/2, we parse
		//the remainder of the string and assign _target to be the evaluation of that expression. For example, #Pi/2 would
		//assign the _target to be new Number(Math.PI/2).

		
		
		
		if(_LRB == BOTH)
		{
			String targetString = stringRepresentation.substring(7, stringRepresentation.length()-1);
			Lexer LELexer = new Lexer();
			Parser LEParser = new Parser();
			ArrayList<Lexer.Token> LELOutput= LELexer.lex(targetString);
			Expression exp = LEParser.parse(LELOutput);
			//System.out.println("Here is the target Expression " + exp.unParse());
			_target = exp;
		}
		else
		{
			String targetString = stringRepresentation.substring(8, stringRepresentation.length()-1);
			Lexer LELexer = new Lexer();
			Parser LEParser = new Parser();
			ArrayList<Lexer.Token> LELOutput= LELexer.lex(targetString);
			Expression exp = LEParser.parse(LELOutput);
			//System.out.println("Here is the target Expression " + exp.unParse());
			_target = exp;
		}
		_function = aFunction;		
	}
	
	//Evaluates this limit expression without the limit
	public double evaluateAsExpression(Map<Variable,Double> variableMap)
	{		
		return _function.evaluate(variableMap);
	}
	
	
	
	public boolean isContinuousAt(Variable var, Double doub)
	{
		HashMap m = new HashMap<Variable, Double>();
		m.put(var, doub);
		if(_function.isContinuousAt(m))
		{
			return true;
		}
		return false;
	}
	
	public boolean isContinuousAtTarget()
	{
		HashMap m = new HashMap<Variable, Double>();
		HashMap m2 = new HashMap<Variable, Double>();
		m.put(_variable, _target.evaluate(m2));
		if(_function.isContinuousAt(m))
		{
			return true;
		}
		return false;
	}
	
	//This method examines the output values of the function as the input values approach the target from the left.
	//This method returns a string which describes the behavior of the function under these circumstances. 
	public String leftHandBehaviorAtTarget()
	{
		String change;
		String approximation;
		
		HashMap m = new HashMap<Variable, Double>();
		HashMap m2 = new HashMap<Variable, Double>();
		Double targetDouble = _target.evaluate(m2);
		m.put(_variable, targetDouble - .0001);
		
		Double approx1 = _function.evaluate(m);
		m.clear();
		m.put(_variable, targetDouble - .00001);
		Double approx2 = _function.evaluate(m);
		m.clear();
		m.put(_variable, targetDouble - .000001);
		Double approx3 = _function.evaluate(m);


		if(approx1 > approx2 && approx2 > approx3)
		{
			change = "negative";
		}
		else if(approx1 < approx2 && approx2 < approx3)
		{
			change = "positive";
		}
		else if(approx1.equals(approx2) && approx2.equals(approx3))
		{
			change = "zero";
		}
		else
		{
			change = "not clear";
		}
		
		return ("An approximation is " + approx3 + " and the slope is " + change);
	}
	
	//This method examines the output values of the function as the input values approach the target from the left.
		//This method returns a string which describes the behavior of the function under these circumstances. 
		public String rightHandBehaviorAtTarget()
		{
			String change;
			String approximation;
			
			HashMap m = new HashMap<Variable, Double>();
			HashMap m2 = new HashMap<Variable, Double>();
			Double targetDouble = _target.evaluate(m2);
			m.put(_variable, targetDouble + .0001);
			
			Double approx3 = _function.evaluate(m);
			m.clear();
			m.put(_variable, targetDouble + .00001);
			Double approx2 = _function.evaluate(m);
			m.clear();
			m.put(_variable, targetDouble + .000001);
			Double approx1 = _function.evaluate(m);


			if(approx1 > approx2 && approx2 > approx3)
			{
				change = "negative";
			}
			else if(approx1 < approx2 && approx2 < approx3)
			{
				change = "positive";
			}
			else if(approx1.equals(approx2) && approx2.equals(approx3))
			{
				change = "zero";
			}
			else
			{
				change = "not clear";
			}
			
			return ("An approximation is " + approx3 + " and the slope is " + change);
		}
	
	//This method will eventually solve the limit expression
	public double evaluate()
	{
		HashMap m = new HashMap<Variable, Double>();
		HashMap m2 = new HashMap<Variable, Double>();
		m.put(_variable, _target.evaluate(m2));
		return _function.evaluate(m);

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
			return "Limit[" + _function.toWolf() + ", " + _variable.toWolf() + "->" + _target.toWolf() + "]";
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
	public Double getTargetDouble()
	{
		HashMap m = new HashMap<Variable, Double>();
		return _target.evaluate(m);
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
	
	public Expression getFunction() {
		return _function;
	}
}
