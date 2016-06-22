package lexerAndParser;

import hierarchy.*;
import hierarchy.Number;
import lexerAndParser.Lexer.TokenType;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Parser 
{
	private ArrayList<Variable> _variables; //used to access variables used in the expression.
											//usually to verify that variables in the limit expression are
											//contained in the expression. 
	private String _limitString = "";
	private LimitExpression _limit;
	//constructor
	public Parser()
	{
		_variables = new ArrayList<Variable>();
		_limit = new LimitExpression();
	}
	
	//Accessor methods
	public ArrayList<Variable> getVar()
	{
		return _variables;
	}
	public void clearVar()
	{
		_variables = new ArrayList<Variable>();
	}
	public LimitExpression getLimit()
	{
		return _limit;
	}
	public void clearLimit()
	{
		_limit = new LimitExpression();
		_limitString = "";
	}
	
	//Given a lexed ArrayList of Tokens, this method will first convert the ArrayList (which is in infix notation)
	//to a ConcurrentLinkedQueue (which is in postfix notation). Then this method will create an Expression from this postfix queue.
	//It returns the resulting Expression.
	//At the same time, this method takes the limit expression token and converts it to a LimitExpression object.
	public Expression parse(ArrayList<Lexer.Token> input)
	{
		input.trimToSize();
		if(input.isEmpty()) return (Expression)null;
		Queue<Lexer.Token> postfix = new ConcurrentLinkedQueue<Lexer.Token>();
		Stack<Lexer.Token> operator = new Stack<Lexer.Token>();
		Stack<Expression> exStack = new Stack<Expression>(); //Expression Stack
		Expression output = new Number(1);
		
		//precedence:
		//highest: 	UNARYOP
		//			PAREN
		//			EXPONENT
		//			MULTIPLY/DIVIDE
		//			ADD/SUBTRACT
		
		//These tokens are utility tokens used during the do-while loop. 
		//They help deal with the subtraction vs. negative ambiguity among other things.
		Lexer.Token whitespace = new Lexer.Token(TokenType.WHITESPACE, " ");
		Lexer.Token previousToken = whitespace;
		Lexer.Token afterInserted = whitespace;
		boolean anythingInserted = false;
		boolean commentReached = false;
		
		
		//Converts infix ArrayList to postfix queue
		for(Lexer.Token toke: input)
		{	
			do
			{
				anythingInserted = false; 
				
				if (toke.getType().equals(Lexer.TokenType.COMMENT))
				{
					System.out.println("The rest of this line is a comment.");
					commentReached = true;
				}
				else if(toke.getType().equals(Lexer.TokenType.LIMITEXPRESSION))
				{
					    postfix.add(toke);
				}
				else if(toke.getType().equals(Lexer.TokenType.NUMBER) || toke.getType().equals(Lexer.TokenType.VARIABLE) || toke.getType().equals(Lexer.TokenType.E) || toke.getType().equals(Lexer.TokenType.PI))
				{
						postfix.add(toke);
				}
				else if(toke.getType().equals(Lexer.TokenType.NEGATIVENUMBER) )
				{
					if(previousToken.getType().equals(TokenType.NEGATIVENUMBER) || previousToken.getType().equals(TokenType.NUMBER) || previousToken.getType().equals(TokenType.VARIABLE) || previousToken.getType().equals(TokenType.CLOSEPAREN) )
					{
						//deals with subtraction vs negative ambiguity
						Lexer.Token positiveNumber = new Lexer.Token(TokenType.NUMBER, toke.getData().substring(1, toke.getData().length()));
						afterInserted = positiveNumber;
						Lexer.Token newMinus = new Lexer.Token(TokenType.BINARYOP, "-");
						toke = newMinus;
						anythingInserted = true;
					}
					else
					{
						postfix.add(toke);
					}
				}
				else if(toke.getType().equals(Lexer.TokenType.UNARYOP))
				{
					operator.push(toke);
				}
				else if(toke.getType().equals(Lexer.TokenType.BINARYOP)) //SEE ABOVE PRECEDENCE
				{
					if(operator.isEmpty() || operator.peek().getType().equals(Lexer.TokenType.OPENPAREN))
					{
						operator.push(toke);
					}
					else if(toke.getData().equalsIgnoreCase("^") && operator.peek().getData().equalsIgnoreCase("^"))
					{
						//Left right association
						postfix.add(operator.pop());
						operator.push(toke);
					}
					else if(toke.getData().equalsIgnoreCase("^"))//if operator.peek() has a lower precedence than toke.
					{
						operator.push(toke);
					}
					else if((toke.getData().equalsIgnoreCase("*") || toke.getData().equalsIgnoreCase("/")) && (operator.peek().getData().equalsIgnoreCase("*") || operator.peek().getData().equalsIgnoreCase("/")))
					{
						//Left right association
						postfix.add(operator.pop());
						operator.push(toke);
					}
					else if((toke.getData().equalsIgnoreCase("*") || toke.getData().equalsIgnoreCase("/")) && operator.peek().getData().equalsIgnoreCase("^")) //toke has a lower precedence than operator.peek();
					{
						postfix.add(operator.pop());
						if(operator.isEmpty() || operator.peek().getType().equals(TokenType.OPENPAREN))
						{
							operator.push(toke);
						}
						else
						{
							//continues to look through operator until Operator is empty or reaches an '('
							do
							{
								//while toke has a lower precedence than operator.peek().
								while(operator.peek().getData().equalsIgnoreCase("^"))
								{
									postfix.add(operator.pop());
								}
								//now to check if operator.peek() is of the same precedence as toke.
								if(operator.isEmpty())
								{
									
								}
								else if(operator.peek().getData().equalsIgnoreCase("*") || operator.peek().getData().equalsIgnoreCase("/"))
								{
									postfix.add(operator.pop());
									operator.push(toke);
								}
								//now check if operator.peek() has lower precedence
								else if(operator.peek().getData().equalsIgnoreCase("+") || operator.peek().getData().equalsIgnoreCase("-"))
								{
									operator.push(toke);
								}
							}while(!operator.isEmpty() || operator.peek().getType().equals(TokenType.OPENPAREN));
							
						}
					}
					else if(toke.getData().equalsIgnoreCase("*") || toke.getData().equalsIgnoreCase("/"))//if operator.peek() has a lower precedence than toke.
					{
						operator.push(toke);
					}
					else if((toke.getData().equalsIgnoreCase("+") || toke.getData().equalsIgnoreCase("-")) && (operator.peek().getData().equalsIgnoreCase("+") || operator.peek().getData().equalsIgnoreCase("-")))
					{
						//left right association
					    postfix.add(operator.pop());
						operator.push(toke);
					}
					else if(toke.getData().equalsIgnoreCase("+") || toke.getData().equalsIgnoreCase("-"))//if operator.peek() has a higher precedence than toke.
					{
						postfix.add(operator.pop());
						if(operator.isEmpty() || operator.peek().getType().equals(TokenType.OPENPAREN))
						{
							operator.push(toke);
						}
						else
						{
							//while toke has a lower precedence than operator.peek().
							while(operator.peek().getData().equalsIgnoreCase("*") || operator.peek().getData().equalsIgnoreCase("/") || operator.peek().getData().equalsIgnoreCase("^"))
							{
								postfix.add(operator.pop());
							}
							//now to check if operator.peek() is of the same precedence as toke.
							if(operator.isEmpty())
							{
								
							}
							else if(operator.peek().getData().equalsIgnoreCase("+") || operator.peek().getData().equalsIgnoreCase("-"))
							{
								postfix.add(operator.pop());
							}
							operator.push(toke);
						}
					}
					else
					{
						System.err.println("YOU FORGOT A COMBINATION!!!!!!!!!!!!!!!!");
					}
				}
				else if(toke.getType().equals(Lexer.TokenType.OPENPAREN))
				{
					operator.push(toke);
				}
				else if(toke.getType().equals(Lexer.TokenType.CLOSEPAREN))
				{
					try
					{
						if(operator.isEmpty()) throw new Exception();
						
						while (!operator.peek().getType().equals(Lexer.TokenType.OPENPAREN)) 
						{
							
							try 
							{
								if (operator.isEmpty())
									throw new Exception();

								postfix.add(operator.pop());
							} catch (Exception e) 
							{
								System.err.println("Parentheses do not match");
								e.printStackTrace();
							}
						}
						
						operator.pop();//removes openparen.
					}
					catch(Exception e)
					{
						System.err.println("Parentheses do not match");
						e.printStackTrace();
					}
					
					
					//handles UNARYOP
					if(!operator.isEmpty())
					{
						if(operator.peek().getType().equals(Lexer.TokenType.UNARYOP))
						{
							postfix.add(operator.pop());
						}
					}
				}
				
				previousToken = toke;
			}while(anythingInserted);
			//FOR DEBUGGING PURPOSES...
			//System.out.println("Got through the do-while loop after processing " + toke.toString());
			//System.out.println("here is the postfix so far: " + postfixToString(postfix) + "END TOSTRING");
			//System.out.println("here is the operator stack so far: " + operator.toString() + "End toString");
			
			if(afterInserted != whitespace)
			{
				postfix.add(afterInserted);
				afterInserted = whitespace;
			}
			if(commentReached)
			{
				break;
			}
		}
		//Adds any remaining operators to the Queue
		while(!operator.isEmpty())
		{
			postfix.add(operator.pop());
		}

		//Done converting to postfix. Printing to verify...
		//System.out.println(postfixToString(postfix));
		//Now to convert to Expression...
		
		if(!postfix.isEmpty())
		{
			//First we remove the LIMITEXPRESSION.
			if(postfix.peek().getType().equals(TokenType.LIMITEXPRESSION))
			{
				_limitString = postfix.poll().getData();
				//System.out.println("Here is the limit Expression" + postfix.poll());
			}
		}
		
		
		while(!postfix.isEmpty())
		{
			Lexer.Token current = postfix.poll();
			
			if(current.getType().equals(TokenType.NUMBER))
			{
				exStack.push(new Number(Double.parseDouble(current.getData())));
			}
			else if(current.getType().equals(TokenType.VARIABLE))
			{
				//allows variables to be accessed through the _variables arraylist 
				//by adding new variables only if they are not already contained in _variables
				Boolean varContained = false;
				int foundAt = 0;
				if(!_variables.isEmpty())
				{
					for(Variable check: _variables)
					{
						if(check.getName().equalsIgnoreCase(current.getData()))
						{
							varContained = true;
							foundAt = _variables.indexOf(check);
						}
					}
					
					if(!varContained)
					{
						Variable aVariable = new Variable(current.getData());
						_variables.add(aVariable);
						exStack.push(aVariable);
					}
					else
					{
						exStack.push(_variables.get(foundAt));
					}
				}
				else
				{
					Variable aVariable = new Variable(current.getData());
					_variables.add(aVariable);
					exStack.push(aVariable);
				}
				
			}
			else if(current.getType().equals(TokenType.NEGATIVENUMBER))
			{
				exStack.push(new Number(Double.parseDouble(current.getData())));
			}
			else if(current.getType().equals(TokenType.PI))
			{
				exStack.push(new Number(Math.PI));
			}
			else if(current.getType().equals(TokenType.E))
			{
				exStack.push(new Number(Math.E));
			}
			else if(current.getType().equals(TokenType.BINARYOP))
			{
				if(current.getData().equalsIgnoreCase("+"))
				{
					Expression pop1 = exStack.pop();
					Expression pop2 = exStack.pop();
					exStack.push(new BinaryPlus(pop2, pop1));
				}
				else if(current.getData().equalsIgnoreCase("-"))
				{
					Expression pop1 = exStack.pop();
					Expression pop2 = exStack.pop();
					exStack.push(new BinaryMinus(pop2, pop1));
				}
				else if(current.getData().equalsIgnoreCase("*"))
				{
					Expression pop1 = exStack.pop();
					Expression pop2 = exStack.pop();
					exStack.push(new BinaryMult(pop2, pop1));
				}
				else if(current.getData().equalsIgnoreCase("/"))
				{
					Expression pop1 = exStack.pop();
					Expression pop2 = exStack.pop();
					exStack.push(new BinaryDivideBy(pop2, pop1));
				}
				else if(current.getData().equalsIgnoreCase("^"))
				{
					Expression pop1 = exStack.pop();
					Expression pop2 = exStack.pop();
					exStack.push(new BinaryExponent(pop2, pop1));
				}
				else
				{
					System.err.println("YOU FORGOT A BINARY OPERATOR IN THE TOEXPRESSION PHASE.");
				}
			}
			else if(current.getType().equals(TokenType.UNARYOP))
			{
				if(current.getData().equalsIgnoreCase("sin"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnarySin(pop1));
				}
				else if(current.getData().equalsIgnoreCase("cos"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnaryCos(pop1));
				}
				else if(current.getData().equalsIgnoreCase("cbrt"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnaryCubeRoot(pop1));
				}
				else if(current.getData().equalsIgnoreCase("ln"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnaryNaturalLog(pop1));
				}
				else if(current.getData().equalsIgnoreCase("sqrt"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnarySquareRoot(pop1));
				}
				else if(current.getData().equalsIgnoreCase("tan"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnaryTan(pop1));
				}
				else if(current.getData().equalsIgnoreCase("absVal"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnaryAbsVal(pop1));
				}
				else if(current.getData().equalsIgnoreCase("reciprocal"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnaryReciprocal(pop1));
				}
				else if(current.getData().equalsIgnoreCase("square"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnarySquared(pop1));
				}
				else if(current.getData().equalsIgnoreCase("negative"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnaryMinus(pop1));
				}
				else if(current.getData().equalsIgnoreCase("positive"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnaryPlus(pop1));
				}
				else
				{
					System.err.println("YOU FORGOT TO HANDLE ALL UNARYOPS");
				}
			}
			else if(current.getType().equals(Lexer.TokenType.OPENPAREN) || current.getType().equals(Lexer.TokenType.CLOSEPAREN))
			{
				//There should not be ANY parentheses in TOEXPRESSION phase.
				throw new IllegalArgumentException("Parentheses do not match in TOEXPRESSION phase");
			}
			else
			{
				System.err.println("Something else is wrong in the TOEXPRESSION phase. Unhandled ______: " + current.toString());
			}
		}
			
		if(exStack.isEmpty())
		{
			output = (Expression)null;
		}
		else
		{
			output = exStack.pop();
		}
		if(!exStack.isEmpty())
		{
			System.err.println("SOMEHOW THERE IS STILL SOMETHING IN EXSTACK");
		}
		
		//now to create a limit out of this Expression
		if(_limitString.equalsIgnoreCase(""))
		{
			_limit = null;
		}
		else
		{
			_limit = new LimitExpression(_limitString, output);
			
			
			//allows variables to be accessed through the _variables arraylist 
			//by adding new variables only if they are not already contained in _variables
			Variable current = _limit.getVariable();
			if(!_variables.isEmpty())
			{
				for(Variable check: _variables)
				{
					if(check.getName().equalsIgnoreCase(current.unParse()))
					{
						_limit.setVariable(check);
						break;
					}
				}
			}
		}
		
		//lastly, return the expression.
		return output;
	}
	
	//Allows us to check to see if the postfix Queue is correct before it is converted to Expresion form
	private String postfixToString(Queue postfixAL)
	{
		String str = "";
		Queue<Lexer.Token> postfixClone = new ConcurrentLinkedQueue<Lexer.Token>(postfixAL);
		while(!postfixClone.isEmpty())
		{
			str += postfixClone.poll() + "\n";
		}		
		return str;
	}
}
