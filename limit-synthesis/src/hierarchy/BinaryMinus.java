//Class which represents subtraction. Requires two expressions to function.
package hierarchy;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class BinaryMinus implements BinaryOperator {

    private Expression _exp1 = null;
    private Expression _exp2 = null;
    private int _locationRelativeToPreviousOperator = -1;
    private Expression _previousOperator = null;
    
    public BinaryMinus(Expression subtractFrom, Expression subtractHowMuch)
    {
        _exp1= subtractFrom;
        _exp2= subtractHowMuch;
    	_exp1.setPreviousOperator(this);
    	_exp2.setPreviousOperator(this);
    	_exp1.setLocationRelativeToPreviousOperator(1);
    	_exp2.setLocationRelativeToPreviousOperator(2);        
    }
    
    public double evaluate(Map<Variable,Double> variableMap) throws IllegalArgumentException
    {
        if(_exp1 == null || _exp2 == null) throw new IllegalArgumentException("This Minus has not been initialized");
        
        return _exp1.evaluate(variableMap) - _exp2.evaluate(variableMap);
    }
    
    @Override
    public boolean isContinuousAt(Map<Variable,Double> variableMap)
    {
    	boolean result = true;
    	return (result && _exp1.isContinuousAt(variableMap) && _exp2.isContinuousAt(variableMap));
    }
    
    public String unParse()
    {
    	String str = "(" + _exp1.unParse() + "-" + _exp2.unParse() + ")";
    	return str;
    }
    
    public String toWolf()
    {
    	String str = "(" + _exp1.toWolf() + "-" + _exp2.toWolf() + ")";
    	return str;
    }
    
    public String getClassName()
    {
    	return "BinaryMinus";
    }
    
    public int size()
    {
    	return 1 + _exp1.size() + _exp2.size();
    }
    
    public String getExpType()
    {
    	return "Binary";
    }
    
    public Expression getExp1()
    {
    	return _exp1;
    }
    
    public Expression getExp2()
    {
    	return _exp2;
    }
    
    public void setExp1(Expression anExp)
    {
    	_exp1 = anExp;
    	_exp1.setLocationRelativeToPreviousOperator(1);
    	_exp1.setPreviousOperator(this);
    }
    
    public void setExp2(Expression anExp)
    {
    	_exp2 = anExp;
    	_exp2.setLocationRelativeToPreviousOperator(2);
    	_exp2.setPreviousOperator(this);
    }
    
    public ArrayList<Expression> toPreOrderAL()
    {
    	_exp1.setLocationRelativeToPreviousOperator(1);
    	_exp1.setPreviousOperator(this);
    	_exp2.setLocationRelativeToPreviousOperator(2);
    	_exp2.setPreviousOperator(this);
    	ArrayList<Expression> result = new ArrayList<Expression>();
    	result.add(this);
    	result.addAll(_exp1.toPreOrderAL());
    	result.addAll(_exp2.toPreOrderAL());
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
			
			int leftOrRight = gen.nextInt(2);//determine which side of a Binary Operator we will keep.
			
			if(_locationRelativeToPreviousOperator == -1)//if current is the root of the tree, we have to re-define _function.
			{
				if(leftOrRight == 0)//decide which side to save.
				{
					//If regressing would result in losing too much information, we do not regress.
					if(_exp1.size() > LimitExpression.minSize)
					{
						limExp.setFunction(_exp1);
					}
				}
				else
				{
					//If regressing would result in losing too much information, we do not regress.
					if(_exp2.size() > LimitExpression.minSize)
					{
						limExp.setFunction(_exp2);
					}
				}
			}
			else//if current is anything else but the root of the tree, we eliminate current and connect previousOperator and one of current's member expressions.
			{
				Expression savedExpRegression = null;
				if(leftOrRight == 0)//decide which side to save.
				{
					//If regressing would result in losing too much information, we do not regress.
					if(_exp2.size() < LimitExpression.maxRegression)
					{
						savedExpRegression = _exp1;
					}
				}
				else
				{
					//If regressing would result in losing too much information, we do not regress.
					if(_exp1.size() < LimitExpression.maxRegression)
					{
						savedExpRegression = _exp2;
					}
				}

				if(savedExpRegression != null)
				{
					if(_locationRelativeToPreviousOperator == 0)//if current's previousOperator is Unary
					{
						((UnaryOperator)_previousOperator).setExp(savedExpRegression);
					}
					else if(_locationRelativeToPreviousOperator == 1)//if current is the exp1 of a binaryOperator
					{
						((BinaryOperator)_previousOperator).setExp1(savedExpRegression);
					}
					else if(_locationRelativeToPreviousOperator == 2)//if current is the exp2 of a binaryOperator
					{
						((BinaryOperator)_previousOperator).setExp2(savedExpRegression);
					}
					else
					{
						System.out.println("_previousOperator was not a unary or a binary expression in Regression (current is Binary) " + _previousOperator.getClassName());
					}
				}
			}
		}
		else if(mutationType == 2)//substitution
		{
			// Create random gene & Replace existing gene
			
			createdGene = gen.nextInt(5) + 13;
			Expression substitutedNewExp1 = _exp1;
			Expression substitutedNewExp2 = _exp2;

			switch(_locationRelativeToPreviousOperator)
			{
			case -1: //current is the root
				switch(createdGene)
				{
				case 13: limExp.setFunction(new BinaryDivideBy(substitutedNewExp1, substitutedNewExp2)); break;
				case 14: limExp.setFunction(new BinaryExponent(substitutedNewExp1, substitutedNewExp2)); break;
				case 15: limExp.setFunction(new BinaryMinus(substitutedNewExp1, substitutedNewExp2)); break;
				case 16: limExp.setFunction(new BinaryMult(substitutedNewExp1, substitutedNewExp2)); break;
				case 17: limExp.setFunction(new BinaryPlus(substitutedNewExp1, substitutedNewExp2)); break;
				default: System.out.println("There was an error in the mutation/substitution/BinarySubstitution of " 
						+ unParse() + "===Undefined BinaryOperator (root).");
				}
				break;
			case 0: 
				switch(createdGene)
				{
				case 13: ((UnaryOperator)(_previousOperator)).setExp(new BinaryDivideBy(substitutedNewExp1, substitutedNewExp2)); break;
				case 14: ((UnaryOperator)(_previousOperator)).setExp(new BinaryExponent(substitutedNewExp1, substitutedNewExp2));break;
				case 15: ((UnaryOperator)(_previousOperator)).setExp(new BinaryMinus(substitutedNewExp1, substitutedNewExp2));break;
				case 16: ((UnaryOperator)(_previousOperator)).setExp(new BinaryMult(substitutedNewExp1, substitutedNewExp2));break;
				case 17: ((UnaryOperator)(_previousOperator)).setExp(new BinaryPlus(substitutedNewExp1, substitutedNewExp2));break;
				default: System.out.println("There was an error in the mutation/substitution/BinarySubstitution of " 
						+ unParse() + "===Undefined BinaryOperator (UnaryPrevious).");
				}
				break;
			case 1: 
				switch(createdGene)
				{
				case 13: ((BinaryOperator)_previousOperator).setExp1(new BinaryDivideBy(substitutedNewExp1, substitutedNewExp2));break;
				case 14: ((BinaryOperator)_previousOperator).setExp1(new BinaryExponent(substitutedNewExp1, substitutedNewExp2));break;
				case 15: ((BinaryOperator)_previousOperator).setExp1(new BinaryMinus(substitutedNewExp1, substitutedNewExp2));break;
				case 16: ((BinaryOperator)_previousOperator).setExp1(new BinaryMult(substitutedNewExp1, substitutedNewExp2));break;
				case 17: ((BinaryOperator)_previousOperator).setExp1(new BinaryPlus(substitutedNewExp1, substitutedNewExp2));break;
				default: System.out.println("There was an error in the mutation/substitution/BinarySubstitution of " 
						+ unParse() + "===Undefined BinaryOperator (BinaryPrevious1).");
				}
				break;
			case 2:
				switch(createdGene)
				{
				case 13: ((BinaryOperator)_previousOperator).setExp2(new BinaryDivideBy(substitutedNewExp1, substitutedNewExp2));break;
				case 14: ((BinaryOperator)_previousOperator).setExp2(new BinaryExponent(substitutedNewExp1, substitutedNewExp2));break;
				case 15: ((BinaryOperator)_previousOperator).setExp2(new BinaryMinus(substitutedNewExp1, substitutedNewExp2));break;
				case 16: ((BinaryOperator)_previousOperator).setExp2(new BinaryMult(substitutedNewExp1, substitutedNewExp2));break;
				case 17: ((BinaryOperator)_previousOperator).setExp2(new BinaryPlus(substitutedNewExp1, substitutedNewExp2));break;
				default: System.out.println("There was an error in the mutation/substitution/BinarySubstitution of " 
						+ unParse() + "===Undefined BinaryOperator (BinaryPrevious2).");
				}
				break;
			default: System.out.println("In Substitution (Binary), current's location relative to previous was not -1,0,1,or 2");
			}
		}
		
		//continue recursive path
		_exp1.mutateExpressionOf(limExp);
		_exp2.mutateExpressionOf(limExp);
    }
}
