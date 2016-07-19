//One of two possible ends to a syntax tree of an expression
//represents a double
package hierarchy;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Number implements Expression
{
    private double _num = 0;
    private boolean _init = false;
    private int _locationRelativeToPreviousOperator = -1;
    private Expression _previousOperator = null;

    public Number(double aNum)
    {
        _num = aNum;
        _init = true;
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_init == false) throw new IllegalArgumentException("This Number has not been initialized");
        return _num;
    }
    
    @Override
    public boolean isContinuousAt(Map<Variable,Double> variableMap)
    {
    	return true;
    }
    
    public String unParse()
    {
    	String str;
    	if(_num == Math.PI)
    	{
    		str = "#Pi";
    	}
    	else if(_num == Math.E)
    	{
    		str = "#E";
    	}
    	else
    	{
    		str = ((Double)_num).toString();
    	}
    	return str;
    }
    
    public String toWolf()
    {
    	String str;
    	if(_num == Math.PI)
    	{
    		str = "Pi";
    	}
    	else if(_num == Math.E)
    	{
    		str = "E";
    	}
    	else
    	{
    		str = ((Double)_num).toString();
    	}
    	return str;
    }
    
    public String getClassName()
    {
    	return "Number";
    }
    
    public int size()
    {
    	return 1;
    }
    public String getExpType()
    {
    	return "Number";
    }
    
    public ArrayList<Expression> toPreOrderAL()
    {
    	ArrayList<Expression> result = new ArrayList<Expression>();
    	result.add(this);
    	return result;
    }
    
    public void setPreviousOperator(Expression e)
    {
    	_previousOperator = e;
    }
    
    public Expression getPreviousOperator()
    {
    	return _previousOperator;
    }
    
    public void setLocationRelativeToPreviousOperator(int i)
    {
    	_locationRelativeToPreviousOperator = i;
    }
    
    public int getLocationRelativeToPreviousOperator()
    {
    	return _locationRelativeToPreviousOperator;
    }
    
    public void mutateExpressionOf(LimitExpression limExp)
    {
    	Random gen = new Random();
		int mutationType = 2; //0 = expansion, 1 = regression, 2 = substitution.
		
		int createdGene = -1; 
		//0 = Number
		//1 = Variable
		//2 = UnaryAbsVal
		//3 = UnaryCos
		//4 = UnaryCubeRoot
		//5 = UnaryMinus
		//6 = UnaryNaturalLog
		//7 = UnaryPlus
		//8 = UnaryReciprocal
		//9 = UnarySin
		//10 = UnarySquared
		//11 = UnarySquareRoot
		//12 = UnaryTan
		//13 = BinaryDivideBy
		//14 = BinaryExponent
		//15 = BinaryMinus
		//16 = BinaryMult
		//17 = BinaryPlus
    	
    	//determine if gene is to be mutated.
		if (Math.random() <= LimitExpression.mutationRate)
        {
			//
			// Determine expansion/regression/substitution
			//
			
			if(limExp.functionSize() >= LimitExpression.maxSize - 2)
			{
				//if this function is already at the max size, it cannot expand
				double roll = gen.nextDouble();
				if(roll <= .5)//50% chance of substitution
				{
					mutationType = 2;
				}
				else
				{
					mutationType = 1;//regression
				}					
			}
			else if(limExp.functionSize() < LimitExpression.minSize)
			{
				//if this function is already at the minimum size, it cannot regress.
				double roll = gen.nextDouble();
				if(roll <= .5)//50% chance of substitution
				{
					mutationType = 2;
				}
				else
				{
					mutationType = 0;//expansion
				}
			}
			else
			{
				//normal mutationType selection
				double roll = gen.nextDouble();
				if(roll <= LimitExpression.expansionRate)//expansion
				{
					mutationType = 0;
				}
				else if(roll > LimitExpression.expansionRate && roll <= LimitExpression.regressionRate)//regression
				{
					mutationType = 1;
				}
				else//substitution
				{
					mutationType = 2;
				}
			}
        }
		
		//
		//Mutation
		//
		if(mutationType == 0)//expansion
		{
			int expansionStep = gen.nextInt(2) + 1;
			//determine whether to add a Unary Operation (1) or a binary Operation (2)
			
			// Create random genes
			if(expansionStep == 1)
			{
				createdGene = gen.nextInt(11) + 2;
				
				//expand existing Gene
				if(getLocationRelativeToPreviousOperator() == -1)//if current is the root of the tree, we have to re-define _function.
				{
					switch(createdGene)
					{
						case 2: limExp.setFunction(new UnaryAbsVal(this));	break;
						case 3: limExp.setFunction(new UnaryCos(this));		break;
						case 4: limExp.setFunction(new UnaryCubeRoot(this));break;
						case 5: limExp.setFunction(new UnaryMinus(this));	break;
						case 6: limExp.setFunction(new UnaryNaturalLog(this));break;
						case 7: limExp.setFunction(new UnaryPlus(this));	break;
						case 8: limExp.setFunction(new UnaryReciprocal(this));break;
						case 9: limExp.setFunction(new UnarySin(this));		break;
						case 10: limExp.setFunction(new UnarySquared(this));break;
						case 11: limExp.setFunction(new UnarySquareRoot(this));break;
						case 12: limExp.setFunction(new UnaryTan(this));	break;
						default:
							System.out.println("There was an error in the mutation/expansion/unaryExpansion of " 
										+ unParse() + "===Undefined UnaryOperator.");	
					}						
				}
				else//if current is anything else but the root of the tree, we expand between previous and current.
				{
					
					if(getPreviousOperator().getExpType().equalsIgnoreCase("Unary"))
					{
						switch(createdGene)
						{
						case 2: ((UnaryOperator)_previousOperator).setExp(new UnaryAbsVal(this)); 			break;
						case 3: ((UnaryOperator)_previousOperator).setExp(new UnaryCos(this));				break;
						case 4: ((UnaryOperator)_previousOperator).setExp(new UnaryCubeRoot(this));			break;
						case 5: ((UnaryOperator)_previousOperator).setExp(new UnaryMinus(this));			break;
						case 6: ((UnaryOperator)_previousOperator).setExp(new UnaryNaturalLog(this));		break;
						case 7: ((UnaryOperator)_previousOperator).setExp(new UnaryPlus(this));				break;
						case 8: ((UnaryOperator)_previousOperator).setExp(new UnaryReciprocal(this));		break;
						case 9: ((UnaryOperator)_previousOperator).setExp(new UnarySin(this));				break;
						case 10: ((UnaryOperator)_previousOperator).setExp(new UnarySquared(this));			break;
						case 11: ((UnaryOperator)_previousOperator).setExp(new UnarySquareRoot(this));		break;
						case 12: ((UnaryOperator)_previousOperator).setExp(new UnaryTan(this));				break;
						default:
							System.out.println("There was an error in the mutation/expansion/unaryExpansion of " 
									+ unParse() + "===Undefined UnaryOperator (second switch).");
						}
					}
					else if(getPreviousOperator().getExpType().equalsIgnoreCase("Binary"))
					{
						if(getLocationRelativeToPreviousOperator() == 1)//if current is the left node of previous,
						{
							switch(createdGene)
							{
							case 2: ((BinaryOperator)_previousOperator).setExp1(new UnaryAbsVal(this)); break;
							case 3:	((BinaryOperator)_previousOperator).setExp1(new UnaryCos(this)); break;
							case 4: ((BinaryOperator)_previousOperator).setExp1(new UnaryCubeRoot(this)); break;
							case 5: ((BinaryOperator)_previousOperator).setExp1(new UnaryMinus(this)); break;
							case 6: ((BinaryOperator)_previousOperator).setExp1(new UnaryNaturalLog(this)); break;
							case 7: ((BinaryOperator)_previousOperator).setExp1(new UnaryPlus(this)); break;
							case 8: ((BinaryOperator)_previousOperator).setExp1(new UnaryReciprocal(this)); break;
							case 9: ((BinaryOperator)_previousOperator).setExp1(new UnarySin(this)); break;
							case 10: ((BinaryOperator)_previousOperator).setExp1(new UnarySquared(this)); break;
							case 11: ((BinaryOperator)_previousOperator).setExp1(new UnarySquareRoot(this)); break;
							case 12: ((BinaryOperator)_previousOperator).setExp1(new UnaryTan(this)); break;
							default:
								System.out.println("There was an error in the mutation/expansion/unaryExpansion of " 
									+ unParse() + "===Undefined UnaryOperator (third switch(exp1).");
							}
						}
						else if(getLocationRelativeToPreviousOperator() == 2)//if current is the right node of previous,
						{
							switch(createdGene)
							{
							case 2: ((BinaryOperator)_previousOperator).setExp2(new UnaryAbsVal(this)); break;
							case 3:	((BinaryOperator)_previousOperator).setExp2(new UnaryCos(this)); break;
							case 4: ((BinaryOperator)_previousOperator).setExp2(new UnaryCubeRoot(this)); break;
							case 5: ((BinaryOperator)_previousOperator).setExp2(new UnaryMinus(this)); break;
							case 6: ((BinaryOperator)_previousOperator).setExp2(new UnaryNaturalLog(this)); break;
							case 7: ((BinaryOperator)_previousOperator).setExp2(new UnaryPlus(this)); break;
							case 8: ((BinaryOperator)_previousOperator).setExp2(new UnaryReciprocal(this)); break;
							case 9: ((BinaryOperator)_previousOperator).setExp2(new UnarySin(this)); break;
							case 10: ((BinaryOperator)_previousOperator).setExp2(new UnarySquared(this)); break;
							case 11: ((BinaryOperator)_previousOperator).setExp2(new UnarySquareRoot(this)); break;
							case 12: ((BinaryOperator)_previousOperator).setExp2(new UnaryTan(this)); break;
							default:
								System.out.println("There was an error in the mutation/expansion/unaryExpansion of " 
									+ unParse() + "===Undefined UnaryOperator (third switch(exp2).");
							}
						}
						else
						{
							System.out.println("When mutating/UnaryExpanding " + unParse() 
								+ ", this Expression was not the 1st or 2nd Exp of a BinaryOperator.");
						}
					}
					else
					{
						System.out.println("when mutating/expanding/unaryExpanding " + unParse() + ", previous was not a unary/binary operator.");
					}
				}
				
			}
			else if(expansionStep == 2)//if expanding by a binary operator
			{
				createdGene = gen.nextInt(5) + 13;//decide the binaryOperator
				int leftOrRight = gen.nextInt(2); //decide where to put the new number/variable
				int varOrNum = gen.nextInt(2);	  //decide whether to add a new number or new variable
				Expression newExp1;
				Expression newExp2;
				if(leftOrRight == 0)
				{
					//We put the Number/Variable on the left (exp1)
					if(varOrNum == 0)
					{
						//we put the variable on exp1
						newExp1 = limExp.getVariable();
						newExp2 = this;
					}
					else
					{
						//we put the number on exp1
						newExp1 = new Number(LimitExpression.generateNewDouble());
						newExp2 = this;
					}
				}
				else
				{
					//We put the Number/Variable on the right (exp2)
					if(varOrNum == 0)
					{
						//we put the variable on exp2
						newExp2 = limExp.getVariable();
						newExp1 = this;
					}
					else
					{
						//we put the number on exp2
						newExp2 = new Number(LimitExpression.generateNewDouble());
						newExp1 = this;
					}
				}
				
				if(_locationRelativeToPreviousOperator == -1)//if current is the root of the tree, we have to re-define _function.
				{
					// Replace existing gene
					switch(createdGene)
					{
					case 13: limExp.setFunction(new BinaryDivideBy(newExp1, newExp2)); break;
					case 14: limExp.setFunction(new BinaryExponent(newExp1, newExp2)); break;
					case 15: limExp.setFunction(new BinaryMinus(newExp1, newExp2)); break;
					case 16: limExp.setFunction(new BinaryMult(newExp1, newExp2)); break;
					case 17: limExp.setFunction(new BinaryPlus(newExp1, newExp2)); break;
					default: System.out.println("There was an error in the mutation/expansion/BinaryExpansion of " 
							+ unParse() + "===Undefined BinaryOperator (root).");
					}
				}
				else//if current is anything else but the root of the tree, we expand between previous and current.
				{
					if(_locationRelativeToPreviousOperator == 0)//if the previous operator is Unary
					{
						switch(createdGene)
						{
						case 13: ((UnaryOperator)(_previousOperator)).setExp(new BinaryDivideBy(newExp1, newExp2)); break;
						case 14: ((UnaryOperator)(_previousOperator)).setExp(new BinaryExponent(newExp1, newExp2));break;
						case 15: ((UnaryOperator)(_previousOperator)).setExp(new BinaryMinus(newExp1, newExp2));break;
						case 16: ((UnaryOperator)(_previousOperator)).setExp(new BinaryMult(newExp1, newExp2));break;
						case 17: ((UnaryOperator)(_previousOperator)).setExp(new BinaryPlus(newExp1, newExp2));break;
						default: System.out.println("There was an error in the mutation/expansion/BinaryExpansion of " 
								+ unParse() + "===Undefined BinaryOperator (UnaryPrevious).");
						}
					}
					else if(_locationRelativeToPreviousOperator == 1)
					{
						//determine the location of current relative to previousOperator
						switch(createdGene)
						{
						case 13: ((BinaryOperator)_previousOperator).setExp1(new BinaryDivideBy(newExp1, newExp2));break;
						case 14: ((BinaryOperator)_previousOperator).setExp1(new BinaryExponent(newExp1, newExp2));break;
						case 15: ((BinaryOperator)_previousOperator).setExp1(new BinaryMinus(newExp1, newExp2));break;
						case 16: ((BinaryOperator)_previousOperator).setExp1(new BinaryMult(newExp1, newExp2));break;
						case 17: ((BinaryOperator)_previousOperator).setExp1(new BinaryPlus(newExp1, newExp2));break;
						default: System.out.println("There was an error in the mutation/expansion/BinaryExpansion of " 
								+ unParse() + "===Undefined BinaryOperator (BinaryPrevious1).");
						}
					}	
					else if(_locationRelativeToPreviousOperator == 2)
					{
						switch(createdGene)
						{
						case 13: ((BinaryOperator)_previousOperator).setExp2(new BinaryDivideBy(newExp1, newExp2));break;
						case 14: ((BinaryOperator)_previousOperator).setExp2(new BinaryExponent(newExp1, newExp2));break;
						case 15: ((BinaryOperator)_previousOperator).setExp2(new BinaryMinus(newExp1, newExp2));break;
						case 16: ((BinaryOperator)_previousOperator).setExp2(new BinaryMult(newExp1, newExp2));break;
						case 17: ((BinaryOperator)_previousOperator).setExp2(new BinaryPlus(newExp1, newExp2));break;
						default: System.out.println("There was an error in the mutation/expansion/BinaryExpansion of " 
								+ unParse() + "===Undefined BinaryOperator (BinaryPrevious2).");
						}
					}
					else
					{
						System.out.println("current.getPrevious() was not a unary or a binary expression " + _previousOperator.getClassName()); 
					}
				}
			}
		}
		else if(mutationType == 1)//regression
		{
			//connect previousOperator to a Variable or number under current. 
			
			//not applicable to Number
		}
		else if(mutationType == 2)//substitution
		{
			// Create random gene & Replace existing gene
			
			int numOrVar = gen.nextInt(2);//determine whether to change current to a Number, to a Variable, or keep it the same.
			double newLeafValue = LimitExpression.generateNewDouble();
			
			if(numOrVar == 0)
			{
				//we are going to substitute in a number
				switch(_locationRelativeToPreviousOperator)
				{
				case -1: limExp.setFunction(new Number(gen.nextInt(1000)));break;//current is the root
				case 0: ((UnaryOperator)_previousOperator).setExp(new Number(newLeafValue));break;
				case 1: ((BinaryOperator)_previousOperator).setExp1(new Number(newLeafValue));break;
				case 2: ((BinaryOperator)_previousOperator).setExp2(new Number(newLeafValue));break;
				default: System.out.println("In Substitution ((Number)), current's location relative to previous was not -1,0,1,or 2");
				}
			}
			else
			{
				//We are going to substitute in a variable
				switch(_locationRelativeToPreviousOperator)
				{
				case -1: limExp.setFunction(limExp.getVariable());break;//current is the root
				case 0: ((UnaryOperator)_previousOperator).setExp(limExp.getVariable());break;
				case 1: ((BinaryOperator)_previousOperator).setExp1(limExp.getVariable());break;
				case 2: ((BinaryOperator)_previousOperator).setExp2(limExp.getVariable());break;
				default: System.out.println("In Substitution (Number), current's location relative to previous was not -1,0,1,or 2");
				}
			}
		}
    }
    
    public boolean equals(Expression other) {
    	if (other == null) return false;
    	if (!(other instanceof Number)) return false;
    	if (other.evaluate(null) == _num) return true;
    	return false;
    }
    @Override
	public String getExpressionString() {
		
		return "" + _num;
	}
}