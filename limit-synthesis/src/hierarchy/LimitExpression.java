//class which represents a limit expression.
package hierarchy;

import java.util.ArrayList;
import java.util.Random;

import geneticAlgorithm.FitnessCalc;

import java.util.HashMap;
import java.util.Map;
import lexerAndParser.*;
import hierarchy.*;


public class LimitExpression 
{
	private int fitness; //used in the Genetic algorithm.
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
	
	public Expression getFunction()
	{
		return _function;
	}
	
	public void setFunction(Expression e)
	{
		_function = e;
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
	public static final double uniformRate = 0.5;
	public static final double mutationRate = 0.5;//should be .015
	public static final double expansionRate = .2; //under normal parameters, how likely is a gene to expand?
	public static final double regressionRate = .66;//under normal parameters, how likely is a gene to regress?
	public static final double substitutionRate = 1.0;//............................................. be substituted?
	public static final int maxSize = 20;
	public static final int minSize = 6;
	public static final int maxRegression = 3; //determines how much information can be regressed at a time.
	
	//temporary fix
	public void generateLimitExpression()
	{
		Random gen = new Random();
		_LRB = (gen.nextInt(2) - 1);
		_variable = new Variable("x");
		_target = new Number((double)gen.nextInt());
		_function = new BinaryDivideBy(_variable, _target);
	}
	
	public int getFitness()
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
		double piWeight = 0.4;
		double eWeight = 0.6;
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
	
//	public void mutate()
//	{			
//		ArrayList<Expression> PreOrderTree = _function.toPreOrderAL();
//		int index = 0;
//		Expression current = PreOrderTree.get(0);
//		Random gen = new Random();
//		int mutationType = 2; //0 = expansion, 1 = regression, 2 = substitution.
//		
//		int createdGene = -1; 
//		//0 = Number
//		//1 = Variable
//		//2 = UnaryAbsVal
//		//3 = UnaryCos
//		//4 = UnaryCubeRoot
//		//5 = UnaryMinus
//		//6 = UnaryNaturalLog
//		//7 = UnaryPlus
//		//8 = UnaryReciprocal
//		//9 = UnarySin
//		//10 = UnarySquared
//		//11 = UnarySquareRoot
//		//12 = UnaryTan
//		//13 = BinaryDivideBy
//		//14 = BinaryExponent
//		//15 = BinaryMinus
//		//16 = BinaryMult
//		//17 = BinaryPlus
//		
//		
//		// Loop through genes
//		while (index < PreOrderTree.size()) 
//		{	
//			current = PreOrderTree.get(index);
//            createdGene = -1;
//            
//			//determine if gene is to be mutated.
//			if (Math.random() <= mutationRate)
//            {
//				//
//				// Determine expansion/regression/substitution
//				//
//				
//				if(this.functionSize() >= maxSize - 2)
//				{
//					//if this function is already at the max size, it cannot expand
//					double roll = gen.nextDouble();
//					if(roll <= .5)//50% chance of substitution
//					{
//						mutationType = 2;
//					}
//					else
//					{
//						mutationType = 1;//regression
//					}					
//				}
//				else if(this.functionSize() < minSize)
//				{
//					//if this function is already at the minimum size, it cannot regress.
//					double roll = gen.nextDouble();
//					if(roll <= .5)//50% chance of substitution
//					{
//						mutationType = 2;
//					}
//					else
//					{
//						mutationType = 0;//expansion
//					}
//				}
//				else
//				{
//					//normal mutationType selection
//					double roll = gen.nextDouble();
//					if(roll <= expansionRate)//expansion
//					{
//						mutationType = 0;
//					}
//					else if(roll > expansionRate && roll <= regressionRate)//regression
//					{
//						mutationType = 1;
//					}
//					else//substitution
//					{
//						mutationType = 2;
//					}
//				}
//				
//				//
//				// Mutation
//                //
//				if(mutationType == 0)//expansion
//				{
//					int expansionStep = gen.nextInt(2) + 1;
//					//determine whether to add a Unary Operation (1) or a binary Operation (2)
//					
//					// Create random genes
//					if(expansionStep == 1)
//					{
//						createdGene = gen.nextInt(11) + 2;
//						
//						//expand existing Gene
//						if(index == 0)//if current is the root of the tree, we have to re-define _function.
//						{
//							switch(createdGene)
//							{
//								case 2: _function = new UnaryAbsVal(_function);
//									break;
//								case 3: _function = new UnaryCos(_function);
//									break;
//								case 4: _function = new UnaryCubeRoot(_function);
//									break;
//								case 5: _function = new UnaryMinus(_function);
//									break;
//								case 6: _function = new UnaryNaturalLog(_function);
//									break;
//								case 7: _function = new UnaryPlus(_function);
//									break;
//								case 8: _function = new UnaryReciprocal(_function);
//									break;
//								case 9: _function = new UnarySin(_function);
//									break;
//								case 10: _function = new UnarySquared(_function);
//									break;
//								case 11: _function = new UnarySquareRoot(_function);
//									break;
//								case 12: _function = new UnaryTan(_function);
//									break;
//								default:
//									System.out.println("There was an error in the mutation/expansion/unaryExpansion of " 
//												+ _function.unParse() + "===Undefined UnaryOperator.");	
//							}						
//						}
//						else//if current is anything else but the root of the tree, we expand between previous and current.
//						{
//							
//							if(current.getPreviousOperator().getExpType().equalsIgnoreCase("Unary"))
//							{
//								switch(createdGene)
//								{
//								case 2: Expression temp = new UnaryAbsVal(current);
//									((UnaryOperator)current.getPreviousOperator()).setExp(temp);
//									break;
//								case 3: temp = new UnaryCos(current);
//									((UnaryOperator)current.getPreviousOperator()).setExp(temp);
//									break;
//								case 4: temp = new UnaryCubeRoot(current);
//									((UnaryOperator)current.getPreviousOperator()).setExp(temp);
//									break;
//								case 5: temp = new UnaryMinus(current);
//									((UnaryOperator)current.getPreviousOperator()).setExp(temp);
//									break;
//								case 6: temp = new UnaryNaturalLog(current);
//									((UnaryOperator)current.getPreviousOperator()).setExp(temp);
//									break;
//								case 7: temp = new UnaryPlus(current);
//									((UnaryOperator)current.getPreviousOperator()).setExp(temp);
//									break;
//								case 8: temp = new UnaryReciprocal(current);
//									((UnaryOperator)current.getPreviousOperator()).setExp(temp);
//									break;
//								case 9: temp = new UnarySin(current);
//									((UnaryOperator)current.getPreviousOperator()).setExp(temp);
//									break;
//								case 10: temp = new UnarySquared(current);
//									((UnaryOperator)current.getPreviousOperator()).setExp(temp);
//									break;
//								case 11: temp = new UnarySquareRoot(current);
//									((UnaryOperator)current.getPreviousOperator()).setExp(temp);
//									break;
//								case 12: temp = new UnaryTan(current);
//									((UnaryOperator)current.getPreviousOperator()).setExp(temp);
//									break;
//								default:
//									System.out.println("There was an error in the mutation/expansion/unaryExpansion of " 
//											+ _function.unParse() + "===Undefined UnaryOperator (second switch).");
//								}
//							}
//							else if(current.getPreviousOperator().getExpType().equalsIgnoreCase("Binary"))
//							{
//								if(current.getLocationRelativeToPreviousOperator() == 1)//if current is the left node of previous,
//								{
//									switch(createdGene)
//									{
//									case 2: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryAbsVal(current)); break;
//									case 3:	((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryCos(current)); break;
//									case 4: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryCubeRoot(current)); break;
//									case 5: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryMinus(current)); break;
//									case 6: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryNaturalLog(current)); break;
//									case 7: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryPlus(current)); break;
//									case 8: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryReciprocal(current)); break;
//									case 9: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnarySin(current)); break;
//									case 10: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnarySquared(current)); break;
//									case 11: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnarySquareRoot(current)); break;
//									case 12: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryTan(current)); break;
//									default:
//										System.out.println("There was an error in the mutation/expansion/unaryExpansion of " 
//											+ _function.unParse() + "===Undefined UnaryOperator (third switch(exp1).");
//									}
//								}
//								else if(current.getLocationRelativeToPreviousOperator() == 2)//if current is the right node of previous,
//								{
//									switch(createdGene)
//									{
//									case 2: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryAbsVal(current)); break;
//									case 3:	((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryCos(current)); break;
//									case 4: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryCubeRoot(current)); break;
//									case 5: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryMinus(current)); break;
//									case 6: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryNaturalLog(current)); break;
//									case 7: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryPlus(current)); break;
//									case 8: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryReciprocal(current)); break;
//									case 9: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnarySin(current)); break;
//									case 10: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnarySquared(current)); break;
//									case 11: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnarySquareRoot(current)); break;
//									case 12: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryTan(current)); break;
//									default:
//										System.out.println("There was an error in the mutation/expansion/unaryExpansion of " 
//											+ _function.unParse() + "===Undefined UnaryOperator (third switch(exp2).");
//									}
//								}
//								else
//								{
//									System.out.println("When mutating/UnaryExpanding " + _function.unParse() 
//										+ ", exp1 == current did not handle all cases.");
//								}
//							}
//							else
//							{
//								System.out.println("when mutating/expanding/unaryExpanding " + _function.unParse() + ", previous was not a unary/binary operator.");
//							}
//						}
//						
//					}
//					else if(expansionStep == 2)//if expanding by a binary operator
//					{
//						createdGene = gen.nextInt(5) + 13;//decide the binaryOperator
//						int leftOrRight = gen.nextInt(2); //decide where to put the new number/variable
//						int varOrNum = gen.nextInt(2);	  //decide whether to add a new number or new variable
//						Expression newExp1;
//						Expression newExp2;
//						if(leftOrRight == 0)
//						{
//							//We put the Number/Variable on the left (exp1)
//							if(varOrNum == 0)
//							{
//								//we put the variable on exp1
//								newExp1 = _variable;
//								newExp2 = current;
//							}
//							else
//							{
//								//we put the number on exp1
//								newExp1 = new Number(generateNewDouble());
//								newExp2 = current;
//							}
//						}
//						else
//						{
//							//We put the Number/Variable on the right (exp2)
//							if(varOrNum == 0)
//							{
//								//we put the variable on exp2
//								newExp2 = _variable;
//								newExp1 = current;
//							}
//							else
//							{
//								//we put the number on exp2
//								newExp2 = new Number(generateNewDouble());
//								newExp1 = current;
//							}
//						}
//						
//						if(index == 0)//if current is the root of the tree, we have to re-define _function.
//						{
//							// Replace existing gene
//							switch(createdGene)
//							{
//							case 13: _function = new BinaryDivideBy(newExp1, newExp2); break;
//							case 14: _function = new BinaryExponent(newExp1, newExp2); break;
//							case 15: _function = new BinaryMinus(newExp1, newExp2); break;
//							case 16: _function = new BinaryMult(newExp1, newExp2); break;
//							case 17: _function = new BinaryPlus(newExp1, newExp2); break;
//							default: System.out.println("There was an error in the mutation/expansion/BinaryExpansion of " 
//									+ _function.unParse() + "===Undefined BinaryOperator (root).");
//							}
//						}
//						else//if current is anything else but the root of the tree, we expand between previous and current.
//						{
//							if(current.getPreviousOperator().getExpType().equalsIgnoreCase("Unary"))
//							{
//								switch(createdGene)
//								{
//								case 13: ((UnaryOperator)(current.getPreviousOperator())).setExp(new BinaryDivideBy(newExp1, newExp2)); break;
//								case 14: ((UnaryOperator)(current.getPreviousOperator())).setExp(new BinaryExponent(newExp1, newExp2));break;
//								case 15: ((UnaryOperator)(current.getPreviousOperator())).setExp(new BinaryMinus(newExp1, newExp2));break;
//								case 16: ((UnaryOperator)(current.getPreviousOperator())).setExp(new BinaryMult(newExp1, newExp2));break;
//								case 17: ((UnaryOperator)(current.getPreviousOperator())).setExp(new BinaryPlus(newExp1, newExp2));break;
//								default: System.out.println("There was an error in the mutation/expansion/BinaryExpansion of " 
//										+ _function.unParse() + "===Undefined BinaryOperator (UnaryPrevious).");
//								}
//							}
//							else if(current.getPreviousOperator().getExpType().equalsIgnoreCase("Binary"))
//							{
//								//determine the location of current relative to previousOperator
//								if(current.getLocationRelativeToPreviousOperator() == 1)
//								{
//									switch(createdGene)
//									{
//									case 13: ((BinaryOperator)current.getPreviousOperator()).setExp1(new BinaryDivideBy(newExp1, newExp2));break;
//									case 14: ((BinaryOperator)current.getPreviousOperator()).setExp1(new BinaryExponent(newExp1, newExp2));break;
//									case 15: ((BinaryOperator)current.getPreviousOperator()).setExp1(new BinaryMinus(newExp1, newExp2));break;
//									case 16: ((BinaryOperator)current.getPreviousOperator()).setExp1(new BinaryMult(newExp1, newExp2));break;
//									case 17: ((BinaryOperator)current.getPreviousOperator()).setExp1(new BinaryPlus(newExp1, newExp2));break;
//									default: System.out.println("There was an error in the mutation/expansion/BinaryExpansion of " 
//											+ _function.unParse() + "===Undefined BinaryOperator (BinaryPrevious1).");
//									}
//								}
//								else if(current.getLocationRelativeToPreviousOperator() == 2)
//								{
//									switch(createdGene)
//									{
//									case 13: ((BinaryOperator)current.getPreviousOperator()).setExp2(new BinaryDivideBy(newExp1, newExp2));break;
//									case 14: ((BinaryOperator)current.getPreviousOperator()).setExp2(new BinaryExponent(newExp1, newExp2));break;
//									case 15: ((BinaryOperator)current.getPreviousOperator()).setExp2(new BinaryMinus(newExp1, newExp2));break;
//									case 16: ((BinaryOperator)current.getPreviousOperator()).setExp2(new BinaryMult(newExp1, newExp2));break;
//									case 17: ((BinaryOperator)current.getPreviousOperator()).setExp2(new BinaryPlus(newExp1, newExp2));break;
//									default: System.out.println("There was an error in the mutation/expansion/BinaryExpansion of " 
//											+ _function.unParse() + "===Undefined BinaryOperator (BinaryPrevious2).");
//									}
//								}
//							}
//							else
//							{
//								System.out.println("current.getPrevious() was not a unary or a binary expression " + current.getPreviousOperator().getClassName()); 
//							}
//						}
//					}
//				}
//				else if(mutationType == 1)//regression
//				{
//					//connect previousOperator to a Variable or number under current. 
//					
//					int leftOrRight = gen.nextInt(2);//determine which side of a Binary Operator we will keep.
//					
//					if(index == 0)//if current is the root of the tree, we have to re-define _function.
//					{
//						if(_function.getExpType().equalsIgnoreCase("Unary"))
//						{
//							_function = ((UnaryOperator)_function).getExp();
//						}
//						else if(_function.getExpType().equalsIgnoreCase("Number") || _function.getExpType().equalsIgnoreCase("Variable"))
//						{
//							//do nothing.
//						}
//						else if(_function.getExpType().equalsIgnoreCase("Binary"))
//						{
//							if(leftOrRight == 0)//decide which side to save.
//							{
//								//If regressing would result in losing too much information, we do not regress.
//								if(((BinaryOperator)_function).getExp1().size() > minSize)
//								{
//									_function = ((BinaryOperator)_function).getExp1();
//								}
//							}
//							else
//							{
//								//If regressing would result in losing too much information, we do not regress.
//								if(((BinaryOperator)_function).getExp2().size() > minSize)
//								{
//									_function = ((BinaryOperator)_function).getExp2();
//								}
//							}
//						}
//						else
//						{
//							System.out.println("root was not a unary or a binary expression in root regression: " + _function.unParse()); 
//						}
//					}
//					else//if current is anything else but the root of the tree, we eliminate current and connect previousOperator and one of current's member expressions.
//					{
//						//We cannot regress Numbers or Variables and preserve the functionality of the tree.
//						if(current.getExpType().equalsIgnoreCase("Number") || current.getExpType().equalsIgnoreCase("Variable"))
//						{
//							//do nothing
//						}
//						else if(current.getExpType().equalsIgnoreCase("Unary"))
//						{
//							if(current.getLocationRelativeToPreviousOperator() == 0)//if current's previousOperator is Unary
//							{
//								((UnaryOperator)current.getPreviousOperator()).setExp(((UnaryOperator)current).getExp());
//							}
//							else if(current.getLocationRelativeToPreviousOperator() == 1)//if current is the exp1 of a binaryOperator
//							{
//								((BinaryOperator)current.getPreviousOperator()).setExp1(((UnaryOperator)current).getExp());
//							}
//							else if(current.getLocationRelativeToPreviousOperator() == 2)//if current is the exp2 of a binaryOperator
//							{
//								((BinaryOperator)current.getPreviousOperator()).setExp2(((UnaryOperator)current).getExp());
//							}
//							else
//							{
//								System.out.println("current.getPrevious() was not a unary or a binary expression in Regression (current is Unary) " + current.getPreviousOperator().getClassName());
//							}
//						}
//						else if(current.getExpType().equalsIgnoreCase("Binary"))
//						{
//							Expression savedExpRegression = null;
//							if(leftOrRight == 0)//decide which side to save.
//							{
//								//If regressing would result in losing too much information, we do not regress.
//								if(((BinaryOperator)current).getExp2().size() < maxRegression)
//								{
//									savedExpRegression = ((BinaryOperator)current).getExp1();
//									System.out.println("getting rid of " + ((BinaryOperator)current).getExp2().unParse() );
//									System.out.println(savedExpRegression.size());
//									System.out.println("keeping " + savedExpRegression.unParse());
//								}
//							}
//							else
//							{
//								//If regressing would result in losing too much information, we do not regress.
//								if(((BinaryOperator)current).getExp1().size() < maxRegression)
//								{
//									savedExpRegression = ((BinaryOperator)current).getExp2();
//									System.out.println("getting rid of " + ((BinaryOperator)current).getExp1().unParse() );
//									System.out.println(savedExpRegression.size());
//									System.out.println("keeping " + savedExpRegression.unParse());
//								}
//							}
//							
//							if(savedExpRegression != null)
//							{
//								if(current.getLocationRelativeToPreviousOperator() == 0)//if current's previousOperator is Unary
//								{
//									((UnaryOperator)current.getPreviousOperator()).setExp(savedExpRegression);
//								}
//								else if(current.getLocationRelativeToPreviousOperator() == 1)//if current is the exp1 of a binaryOperator
//								{
//									((BinaryOperator)current.getPreviousOperator()).setExp1(savedExpRegression);
//								}
//								else if(current.getLocationRelativeToPreviousOperator() == 2)//if current is the exp2 of a binaryOperator
//								{
//									((BinaryOperator)current.getPreviousOperator()).setExp2(savedExpRegression);
//								}
//								else
//								{
//									System.out.println("current.getPrevious() was not a unary or a binary expression in Regression (current is Unary) " + current.getPreviousOperator().getClassName());
//								}
//							}
//						}
//						else
//						{
//							System.out.println("current was not a Number,Unary,Binary,Or Variable in regression");
//						}
//					}
//					
//				}
//				else if(mutationType == 2)//substitution
//				{
//					// Create random gene & Replace existing gene
//					
//					if(current.getExpType().equalsIgnoreCase("Number") || current.getExpType().equalsIgnoreCase("Variable"))
//					{
//						int numOrVar = gen.nextInt(2);//determine whether to change current to a Number, to a Variable, or keep it the same.
//						double newLeafValue = generateNewDouble();
//						
//						if(numOrVar == 0)
//						{
//							//we are going to substitute in a number
//							switch(current.getLocationRelativeToPreviousOperator())
//							{
//							case -1: _function = new Number(gen.nextInt(1000));break;//current is the root
//							case 0: ((UnaryOperator)current.getPreviousOperator()).setExp(new Number(newLeafValue));break;
//							case 1: ((BinaryOperator)current.getPreviousOperator()).setExp1(new Number(newLeafValue));break;
//							case 2: ((BinaryOperator)current.getPreviousOperator()).setExp2(new Number(newLeafValue));break;
//							default: System.out.println("In Substitution ((Number)/variable), current's location relative to previous was not -1,0,1,or 2");
//							}
//						}
//						else
//						{
//							//We are going to substitute in a variable
//							switch(current.getLocationRelativeToPreviousOperator())
//							{
//							case -1: _function = _variable;break;//current is the root
//							case 0: ((UnaryOperator)current.getPreviousOperator()).setExp(_variable);break;
//							case 1: ((BinaryOperator)current.getPreviousOperator()).setExp1(_variable);break;
//							case 2: ((BinaryOperator)current.getPreviousOperator()).setExp2(_variable);break;
//							default: System.out.println("In Substitution (Number/(variable)), current's location relative to previous was not -1,0,1,or 2");
//							}
//						}
//					}
//					else if(current.getExpType().equalsIgnoreCase("Unary"))
//					{
//						createdGene = gen.nextInt(11) + 2;
//						Expression substitutedNewExp = ((UnaryOperator)current).getExp();
//						
//						switch(current.getLocationRelativeToPreviousOperator())
//						{
//						case -1: 
//							switch(createdGene)
//							{
//								case 2: _function = new UnaryAbsVal(substitutedNewExp );break;
//								case 3: _function = new UnaryCos(substitutedNewExp );break;
//								case 4: _function = new UnaryCubeRoot(substitutedNewExp );break;
//								case 5: _function = new UnaryMinus(substitutedNewExp );break;
//								case 6: _function = new UnaryNaturalLog(substitutedNewExp );break;
//								case 7: _function = new UnaryPlus(substitutedNewExp );break;
//								case 8: _function = new UnaryReciprocal(substitutedNewExp );break;
//								case 9: _function = new UnarySin(substitutedNewExp );break;
//								case 10: _function = new UnarySquared(substitutedNewExp );break;
//								case 11: _function = new UnarySquareRoot(substitutedNewExp );break;
//								case 12: _function = new UnaryTan(substitutedNewExp );break;
//								default:
//									System.out.println("There was an error in the mutation/substituation/unarySubstitution of " 
//												+ _function.unParse() + "===Undefined UnaryOperator (root).");	
//							}
//						break;//current is the root
//						case 0: 
//							switch(createdGene)
//							{
//							case 2: ((UnaryOperator)current.getPreviousOperator()).setExp(new UnaryAbsVal(substitutedNewExp));break;
//							case 3: ((UnaryOperator)current.getPreviousOperator()).setExp(new UnaryCos(substitutedNewExp));break;
//							case 4: ((UnaryOperator)current.getPreviousOperator()).setExp(new UnaryCubeRoot(substitutedNewExp));break;
//							case 5: ((UnaryOperator)current.getPreviousOperator()).setExp(new UnaryMinus(substitutedNewExp));break;
//							case 6: ((UnaryOperator)current.getPreviousOperator()).setExp(new UnaryNaturalLog(substitutedNewExp));break;
//							case 7: ((UnaryOperator)current.getPreviousOperator()).setExp(new UnaryPlus(substitutedNewExp));break;
//							case 8: ((UnaryOperator)current.getPreviousOperator()).setExp(new UnaryReciprocal(substitutedNewExp));break;
//							case 9: ((UnaryOperator)current.getPreviousOperator()).setExp(new UnarySin(substitutedNewExp));break;
//							case 10: ((UnaryOperator)current.getPreviousOperator()).setExp(new UnarySquared(substitutedNewExp));break;
//							case 11: ((UnaryOperator)current.getPreviousOperator()).setExp(new UnarySquareRoot(substitutedNewExp)); break;
//							case 12: ((UnaryOperator)current.getPreviousOperator()).setExp(new UnaryTan(substitutedNewExp));break;
//							default:System.out.println("There was an error in the mutation/substitution/unarySubstitution of " 
//										+ _function.unParse() + "===Undefined UnaryOperator (Unary Previous).");
//							}
//						break;
//						case 1: 
//							switch(createdGene)
//							{
//							case 2: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryAbsVal(substitutedNewExp)); break;
//							case 3:	((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryCos(substitutedNewExp)); break;
//							case 4: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryCubeRoot(substitutedNewExp)); break;
//							case 5: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryMinus(substitutedNewExp)); break;
//							case 6: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryNaturalLog(substitutedNewExp)); break;
//							case 7: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryPlus(substitutedNewExp)); break;
//							case 8: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryReciprocal(substitutedNewExp)); break;
//							case 9: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnarySin(substitutedNewExp)); break;
//							case 10: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnarySquared(substitutedNewExp)); break;
//							case 11: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnarySquareRoot(substitutedNewExp)); break;
//							case 12: ((BinaryOperator)current.getPreviousOperator()).setExp1(new UnaryTan(substitutedNewExp)); break;
//							default:
//								System.out.println("There was an error in the mutation/substitution/unarySubstution of " 
//									+ _function.unParse() + "===Undefined UnaryOperator (third switch(exp1).");
//							}
//						break;
//						case 2:
//							switch(createdGene)
//							{
//							case 2: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryAbsVal(substitutedNewExp)); break;
//							case 3:	((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryCos(substitutedNewExp)); break;
//							case 4: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryCubeRoot(substitutedNewExp)); break;
//							case 5: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryMinus(substitutedNewExp)); break;
//							case 6: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryNaturalLog(substitutedNewExp)); break;
//							case 7: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryPlus(substitutedNewExp)); break;
//							case 8: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryReciprocal(substitutedNewExp)); break;
//							case 9: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnarySin(substitutedNewExp)); break;
//							case 10: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnarySquared(substitutedNewExp)); break;
//							case 11: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnarySquareRoot(substitutedNewExp)); break;
//							case 12: ((BinaryOperator)current.getPreviousOperator()).setExp2(new UnaryTan(substitutedNewExp)); break;
//							default:
//								System.out.println("There was an error in the mutation/substitution/unarySubstitution of " 
//									+ _function.unParse() + "===Undefined UnaryOperator (third switch(exp2).");
//							}
//						break;
//						default: System.out.println("In Substitution (Number/(variable)), current's location relative to previous was not -1,0,1,or 2");
//						}
//					}
//					else if(current.getExpType().equalsIgnoreCase("Binary"))
//					{
//						
//						createdGene = gen.nextInt(5) + 13;
//						Expression substitutedNewExp1 = ((BinaryOperator)current).getExp1();
//						Expression substitutedNewExp2 = ((BinaryOperator)current).getExp2();
//						
//						switch(current.getLocationRelativeToPreviousOperator())
//						{
//						case -1: //current is the root
//							switch(createdGene)
//							{
//							case 13: _function = new BinaryDivideBy(substitutedNewExp1, substitutedNewExp2); break;
//							case 14: _function = new BinaryExponent(substitutedNewExp1, substitutedNewExp2); break;
//							case 15: _function = new BinaryMinus(substitutedNewExp1, substitutedNewExp2); break;
//							case 16: _function = new BinaryMult(substitutedNewExp1, substitutedNewExp2); break;
//							case 17: _function = new BinaryPlus(substitutedNewExp1, substitutedNewExp2); break;
//							default: System.out.println("There was an error in the mutation/substitution/BinarySubstitution of " 
//									+ _function.unParse() + "===Undefined BinaryOperator (root).");
//							}
//						break;
//						case 0: 
//							switch(createdGene)
//							{
//							case 13: ((UnaryOperator)(current.getPreviousOperator())).setExp(new BinaryDivideBy(substitutedNewExp1, substitutedNewExp2)); break;
//							case 14: ((UnaryOperator)(current.getPreviousOperator())).setExp(new BinaryExponent(substitutedNewExp1, substitutedNewExp2));break;
//							case 15: ((UnaryOperator)(current.getPreviousOperator())).setExp(new BinaryMinus(substitutedNewExp1, substitutedNewExp2));break;
//							case 16: ((UnaryOperator)(current.getPreviousOperator())).setExp(new BinaryMult(substitutedNewExp1, substitutedNewExp2));break;
//							case 17: ((UnaryOperator)(current.getPreviousOperator())).setExp(new BinaryPlus(substitutedNewExp1, substitutedNewExp2));break;
//							default: System.out.println("There was an error in the mutation/substitution/BinarySubstitution of " 
//									+ _function.unParse() + "===Undefined BinaryOperator (UnaryPrevious).");
//							}
//						break;
//						case 1: 
//							switch(createdGene)
//							{
//							case 13: ((BinaryOperator)current.getPreviousOperator()).setExp1(new BinaryDivideBy(substitutedNewExp1, substitutedNewExp2));break;
//							case 14: ((BinaryOperator)current.getPreviousOperator()).setExp1(new BinaryExponent(substitutedNewExp1, substitutedNewExp2));break;
//							case 15: ((BinaryOperator)current.getPreviousOperator()).setExp1(new BinaryMinus(substitutedNewExp1, substitutedNewExp2));break;
//							case 16: ((BinaryOperator)current.getPreviousOperator()).setExp1(new BinaryMult(substitutedNewExp1, substitutedNewExp2));break;
//							case 17: ((BinaryOperator)current.getPreviousOperator()).setExp1(new BinaryPlus(substitutedNewExp1, substitutedNewExp2));break;
//							default: System.out.println("There was an error in the mutation/substitution/BinarySubstitution of " 
//									+ _function.unParse() + "===Undefined BinaryOperator (BinaryPrevious1).");
//							}
//						break;
//						case 2:
//							switch(createdGene)
//							{
//							case 13: ((BinaryOperator)current.getPreviousOperator()).setExp2(new BinaryDivideBy(substitutedNewExp1, substitutedNewExp2));break;
//							case 14: ((BinaryOperator)current.getPreviousOperator()).setExp2(new BinaryExponent(substitutedNewExp1, substitutedNewExp2));break;
//							case 15: ((BinaryOperator)current.getPreviousOperator()).setExp2(new BinaryMinus(substitutedNewExp1, substitutedNewExp2));break;
//							case 16: ((BinaryOperator)current.getPreviousOperator()).setExp2(new BinaryMult(substitutedNewExp1, substitutedNewExp2));break;
//							case 17: ((BinaryOperator)current.getPreviousOperator()).setExp2(new BinaryPlus(substitutedNewExp1, substitutedNewExp2));break;
//							default: System.out.println("There was an error in the mutation/substitution/BinarySubstitution of " 
//									+ _function.unParse() + "===Undefined BinaryOperator (BinaryPrevious2).");
//							}
//						break;
//						default: System.out.println("In Substitution (Number/(variable)), current's location relative to previous was not -1,0,1,or 2");
//						}
//					}
//					else
//					{
//						System.out.println("In Substitution, current was not a Number, Variable, Unary or Binary." + _function.unParse());
//					}
//				}				
//            }
//			index++;
//		}
//	}
	
	//gives every Expression in the _function of this LimitExpression a chance to perform 1 of 3 mutationTypes.
	public void mutateLimitExpression()
	{
		_function.mutateExpressionOf(this);
	}
	
	
}
