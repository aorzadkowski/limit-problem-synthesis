//class which represents a limit expression.
package hierarchy;

import java.util.ArrayList;
import java.util.Random;

import geneticAlgorithm.FitnessCalc;

import java.util.HashMap;
import java.util.Map;
import lexerAndParser.*;
import options.Options;
import hierarchy.*;


public class LimitExpression 
{
	private double fitness; //used in the Genetic algorithm.
	private int _LRB; //Whether this limit expression is a left-handed(1), right-handed(-1), or both(0);
	private final int LEFT = 1;
	private final int RIGHT = -1;
	private final int BOTH = 0;
	
	private HashMap<Expression, Integer> depthMap;
	
	private Variable _variable; //the variable which approaches the target value;
	private Expression _target; //the target value;
	
	private Expression _function; //the function.
	private String _stringRepresentation = ""; //only created when using the overloaded constructor.
												//This represents the limit notation. "lim _variable>_LRB _target "
	
	public LimitExpression()
	{
		_LRB = BOTH;
		_variable = (Variable)null;
		_target = (Expression)null;
		_function = (Expression)null;
		depthMap = new HashMap<>();
		initializeDepthMap();
	}
	
	public LimitExpression(int lrb, Variable var, Expression target, Expression function) {
		_LRB = lrb;
		//_variable = var;
		_variable = new Variable(var.getName());
		//_target = target;
		_target = target.deepCopy();
		//_function = function;
		_function = function.deepCopy();

		depthMap = new HashMap<>();
		initializeDepthMap();
	}
	
	public LimitExpression(String stringRepresentation, Expression aFunction)
	{
		//The stringRepresentation should be in the following format:
		//lim _variable>_LRB _target _function
		_stringRepresentation = stringRepresentation;
		
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
//			try
//			{
//				exp = LEParser.parse(LELOutput);
//			}
//			catch(Exception e)
//			{
//				
//				e.printStackTrace();
//				System.out.println(targetString);
//				
//				
//				targetString = stringRepresentation.substring(7, stringRepresentation.indexOf('(') -1);
//				LELOutput= LELexer.lex(targetString);
//				exp = LEParser.parse(LELOutput);
//				
//				e.printStackTrace();
//				System.out.println(targetString);
//				System.out.println("Here is the target Expression " + exp.unParse());
//			}
			
			
			_target = exp;
		}
		else
		{
			String targetString = stringRepresentation.substring(8, stringRepresentation.length()-1);
			Lexer LELexer = new Lexer();
			Parser LEParser = new Parser();
			ArrayList<Lexer.Token> LELOutput= LELexer.lex(targetString);
			Expression exp = LEParser.parse(LELOutput);
//			try
//			{
//				exp = 
//			}
//			catch(Exception e)
//			{
//				targetString = stringRepresentation.substring(8, stringRepresentation.indexOf('(') -1);
//				LELOutput= LELexer.lex(targetString);
//				exp = LEParser.parse(LELOutput);
//				
//				e.printStackTrace();
//				System.out.println(targetString);
//				System.out.println("Here is the target Expression " + exp.unParse());
//			}
			//System.out.println("Here is the target Expression " + exp.unParse());
			_target = exp;
		}
		_function = aFunction;	
		depthMap = new HashMap<>();
		initializeDepthMap();	
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
	
	public LimitExpression(LimitExpression limExp)
	{
		_variable = new Variable(limExp.getVariable().getName());
		Expression afunction = limExp.getFunction().deepCopy();
		_function = afunction;
		Expression aTarget = limExp.getTarget().deepCopy();
		_target = aTarget;
		int anLRB = limExp.getLRB();
		_LRB = anLRB;
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
	
	public String getWolframFunctionDomainString()
	{
		return "FunctionDomain[" + _function.toWolf() + "," + _variable.toWolf()+",Reals]";
	}
	
	public String getWolframFunctionRangeString()
	{
		return "FunctionRange[" + _function.toWolf() + "," + _variable.toWolf()+",y,Reals]";
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
	
	public Expression getTarget()
	{
		return _target;
	}
	
	public int getLRB()
	{
		return _LRB;
	}
	
	public Expression getFunction()
	{
		return _function;
	}
	
	public void setFunction(Expression e)
	{
		_function = e;
		depthMap = new HashMap<>();
		initializeDepthMap();
	}
	
	public String getStringRepresentation()
	{
		if(_LRB == BOTH)
		{
			_stringRepresentation = "lim " + _variable.unParse() + "> " + _target.unParse() + " "; 
		}
		else if(_LRB == LEFT)
		{
			_stringRepresentation = "lim " + _variable.unParse() + ">- " + _target.unParse() + " ";
		}
		else
		{
			_stringRepresentation = "lim " + _variable.unParse() + ">+ " + _target.unParse() + " ";
		}
		
		return _stringRepresentation;
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
	
	
	//*******************************************************
	//Here are the methods related to the genetic algorithm.
	//*******************************************************
	
	//GA individual constants
	public static final double MUTATION_RATE = Options.MUTATION_RATE;//should be .015
	public static final double EXPANSION_RATE = Options.EXPANSION_RATE; //under normal parameters, how likely is a gene to expand?
													//should be .2
	public static final double REGRESSION_RATE = Options.REGRESSION_RATE;//under normal parameters, how likely is a gene to regress?
													//should be .66
	public static final double SUBSTITUTION_RATE = Options.SUBSTITUTION_RATE;//............................................. be substituted?
													//1.0
	public static final int MAX_SIZE = Options.MAX_SIZE;
	public static final int MIN_SIZE = Options.MIN_SIZE;
	public static final int MAX_REGRESSION = Options.MAX_REGRESSION; //determines how much information can be regressed at a time.
	
	//simplification cases
	public static final boolean PROHIBIT_NUM_BO_NUM = Options.PROHIBIT_NUM_BO_NUM; //should be true. Makes the following an invalid case: two Numbers
																//combined by a BinaryOperator
																//The cases of e^num, num*#Pi,
																//#Pi*num, and num/num are not included.
	public static final boolean PROHIBIT_NUM_BO_X = Options.PROHIBIT_NUM_BO_X;//Marks Expressions like
																// num^x, num*x,...  as invalid. 
	public static final boolean PROHIBIT_NUM_BO_EXP = Options.PROHIBIT_NUM_BO_EXP;//Marks Expressions like
																// 2-(1/2), 3/cbrt(x), 4*absval(-9) as invalid.
																//num^exp is NOT allowed unless exp is num/num.
	public static final boolean PROHIBIT_X_BO_NUM = Options.PROHIBIT_X_BO_NUM;//Marks Expressions like
																//x^2, x*2, x-2... as invalid
	public static final boolean PROHIBT_X_BO_X = Options.PROHIBIT_X_BO_X;//should be true. Marks Expressions like
																//x^x, x*x, x-x,... as invalid.
	public static final boolean PROHIBIT_X_BO_EXP = Options.PROHIBIT_X_BO_EXP;//Marks Expressions like
																//x-cbrt(x), x*absval(-9),... as invalid.
																//x^exp is NOT allowed unless the exp is a num/num
	public static final boolean PROHIBIT_EXP_BO_NUM = Options.PROHIBIT_EXP_BO_NUM;//Marks Expressions like
																//(cbrt(x)^2, absval(-9)*2 as invalid
	public static final boolean PROHIBIT_EXP_BO_X= Options.PROHIBIT_EXP_BO_X;//Marks Expressions like
																//cbrt(x)*x, absval(-9)*x as invalid
																//exp^x is NOT allowed.
	public static final boolean PROHIBIT_EXP_BO_EXP= Options.PROHIBIT_EXP_BO_EXP;//Should be true. Marks Expressions like
																//cbrt(x)*(absval(-9), sin(x)/(Pi/2) to occur
																//exp^exp is NOT allowed unless the second exp is num/num
	public static final boolean PROHIBIT_UO_NUM = Options.PROHIBIT_UO_NUM;//should be true. Marks Expressions like
																//absval(2) as invalid
																//reciprocal(num) always allowed.
	public static final boolean PROHIBIT_UO_X = Options.PROHIBIT_UO_X;//Marks Expressions like
																//absval(x) as invalid
	public static final boolean PROHIBIT_UO_EXP= Options.PROHIBIT_UO_EXP;//Marks Expressions like
																//absval(3*x) as invalid
	
	//Special cases
	public static final boolean PROHIBIT_E_TO_THE_NUM = Options.PROHIBIT_E_TO_THE_NUM;//e^num
	
	public static final boolean PROHIBIT_NUM_TIMES_PI= Options.PROHIBIT_NUM_TIMES_PI;//num*pi
	
	public static final boolean PROHIBIT_PI_TIMES_NUM= Options.PROHIBIT_PI_TIMES_NUM;//pi*num
	
	public static final boolean PROHIBIT_NUM_OVER_NUM= Options.PROHIBIT_NUM_OVER_NUM;//num/num (num/pi) (num/e) (e/num)...
	
	public static final boolean PROHIBIT_NUM_TO_THE_EXP= Options.PROHIBIT_NUM_TO_THE_EXP;//num^exp ****exp == num/num? Does not include num^(num/num)
	
	public static final boolean PROHIBIT_X_TO_THE_EXP= Options.PROHIBIT_X_TO_THE_EXP;//x^exp ****exp == num/num? x^(num/num) is always allowed
	
	public static final boolean PROHIBIT_EXP_TO_THE_X= Options.PROHIBIT_EXP_TO_THE_X;//exp^x
	
	public static final boolean PROHIBIT_EXP_TO_THE_EXP= Options.PROHIBIT_EXP_TO_THE_EXP;//exp^exp****exp == num/num? exp^(num/num) is always allowed
	
	public static final boolean PROHIBIT_RECIPROCAL_NUM= Options.PROHIBIT_RECIPROCAL_NUM;//reciprocal(num)
	

	
	//temporary fix
	public void generateLimitExpression()
	{
		Random gen = new Random();
		_LRB = (gen.nextInt(2) - 1);
		_variable = new Variable("x");
		_target = new Number(generateNewDouble());
		_function = new Variable("x");
		_function.expandExpressionOf(this);
		_function.expandExpressionOf(this);
		_function.expandExpressionOf(this);
		_function.expandExpressionOf(this);
		_function.expandExpressionOf(this);
	}
	
	public double getFitness()
	{
		if (fitness == 0)
		{
			fitness = FitnessCalc.getFitness(this);
		}
		return fitness;
	}
	
	public static double generateNewDouble()
	{
		//This method generates new doubles to be used in genetic operations. 
		//The double returned can be an integer ((0-9) or (0-100)), pi, or e. 
		double newDoub = 0.0;
		
		double intWeight = 0.2;
		double piWeight = 0.3;
		double eWeight = 0.4;
		//double digitWeight = 1.0;
		
		Random gen = new Random();
		double roll = gen.nextDouble();
		
		if(roll < intWeight)
		{
			newDoub = gen.nextInt(101);
		}
		else if(roll < piWeight)
		{
			newDoub = Math.PI;
		}
		else if(roll < eWeight)
		{
			newDoub = Math.E;
		}
		else
		{
			newDoub = gen.nextInt(10);
		}
		
		return newDoub;
	}
	
	
	//returns the the number of nodes in the _function of this LimitExpression.
	//i.e. the sum of all operators and operands. 
	public int functionSize()
	{
		return _function.size();
	}
	

	
	//gives every Expression in the _function of this LimitExpression a chance to perform 1 of 3 mutationTypes.
	public void mutateLimitExpression()
	{
		_function.mutateExpressionOf(this);
	}
	
	////////////////////////////////////////////////////////
	//////////All map related methods start here.///////////
	////////////////////////////////////////////////////////
	public void initializeDepthMap() {
		initializeDepthMap(_function, 0);
	}
	
	private void initializeDepthMap(Expression node, int depth) {
		depthMap.put(node, depth);
		if (node instanceof UnaryOperator) {
			if (((UnaryOperator) node).getExp() != null) {
				initializeDepthMap(((UnaryOperator) node).getExp(), depth + 1);
			}
		} else if (node instanceof BinaryOperator) {
			if (((BinaryOperator) node).getExp1() != null) {
				initializeDepthMap(((BinaryOperator) node).getExp1(), depth + 1);
			}
			if (((BinaryOperator) node).getExp2() != null) {
				initializeDepthMap(((BinaryOperator) node).getExp2(), depth + 1);
			}
		}
	}
	
	public void updateMap() {
		initializeDepthMap();
	}
	
	public ArrayList<Expression> getAllLeaves() {
		ArrayList<Expression> list = new ArrayList<>();
		if (depthMap == null) {
			depthMap = new HashMap<>();
			initializeDepthMap();
		}
		Object[] keys =  depthMap.keySet().toArray();
		
		for (Object exp : keys) {
			if (((Expression) exp) instanceof Variable || ((Expression) exp) instanceof Number) 
				list.add(((Expression) exp));
		}
		
		return list;
	}
	
	private ArrayList<Expression> getAllNodes() {
		ArrayList<Expression> list = new ArrayList<>();
		
		Expression[] keys = (Expression[]) depthMap.keySet().toArray();
		
		for (Expression exp : keys) {
			list.add(exp);
		}
		
		return list;
	}
	
	public Expression getLeafWithShortestPath() {
		ArrayList<Expression> leaves = getAllLeaves();
		
		Expression temp = leaves.get(0);
		
		for (Expression leaf : leaves) {
			if (depthMap.get(leaf).intValue() < depthMap.get(temp).intValue()) 
				temp = leaf;
		}
		
		return temp;
	}
	
	public Expression getLeafWithLongestPath() {
		ArrayList<Expression> leaves = getAllLeaves();
		
		Expression temp = leaves.get(0);
		
		for (Expression leaf : leaves) {
			if (depthMap.get(leaf).intValue() > depthMap.get(temp).intValue()) 
				temp = leaf;
		}
		
		return temp;
	}
	
	public int getLengthOfShortestPath() {
		return depthMap.get(getLeafWithShortestPath()).intValue();
	}
	
	public int getLengthOfLongestPath() {
		return depthMap.get(getLeafWithLongestPath()).intValue();
	}
	
	public Expression getRandomLeaf() {
		ArrayList<Expression> leaves = getAllLeaves();
		return leaves.get((int)(Math.random() * leaves.size()));
	}
	
	public Expression getRandomNode() {
		ArrayList<Expression> list = getAllNodes();
		
		return list.get((int)(Math.random() * list.size()));
	}
	
}
