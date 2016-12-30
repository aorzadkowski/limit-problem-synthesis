package mathematicaParser;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

import hierarchy.BinaryDivideBy;
import hierarchy.BinaryExponent;
import hierarchy.BinaryMinus;
import hierarchy.BinaryMult;
import hierarchy.BinaryPlus;
import hierarchy.Expression;
import hierarchy.LimitExpression;
import hierarchy.Number;
import hierarchy.UnaryAbsVal;
import hierarchy.UnaryCos;
import hierarchy.UnaryCubeRoot;
import hierarchy.UnaryMinus;
import hierarchy.UnaryNaturalLog;
import hierarchy.UnaryPlus;
import hierarchy.UnaryReciprocal;
import hierarchy.UnarySin;
import hierarchy.UnarySquareRoot;
import hierarchy.UnarySquared;
import hierarchy.UnaryTan;
import hierarchy.Variable;
import lexerAndParser.Lexer;
import lexerAndParser.Lexer.TokenType;

public class NumericParser 
{
	public static Expression parse(ArrayList<NumericLexer.Token> input)
	{
		input.trimToSize();
		if(input.isEmpty()) return (Expression)null;
		Queue<NumericLexer.Token> postfix = new ConcurrentLinkedQueue<NumericLexer.Token>();
		Stack<NumericLexer.Token> operator = new Stack<NumericLexer.Token>();
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
		NumericLexer.Token whitespace = new NumericLexer.Token(NumericLexer.TokenType.WHITESPACE, " ");
		NumericLexer.Token previousToken = whitespace;
		NumericLexer.Token afterInserted = whitespace;
		boolean anythingInserted = false;
		boolean commentReached = false;
		
		
		//Converts infix ArrayList to postfix queue
		for(NumericLexer.Token toke: input)
		{	
			do
			{
				anythingInserted = false; 
				System.out.print("toke = " + toke.getData() + " " + toke.getType() + " ");
				
				if(toke.getType().equals(NumericLexer.TokenType.NUMBER) || toke.getType().equals(NumericLexer.TokenType.VARIABLE) || toke.getType().equals(NumericLexer.TokenType.E) || toke.getType().equals(NumericLexer.TokenType.PI))
				{
						postfix.add(toke);
				}
				else if(toke.getType().equals(NumericLexer.TokenType.NEGATIVENUMBER) )
				{
					if(previousToken.getType().equals(NumericLexer.TokenType.NEGATIVENUMBER) || previousToken.getType().equals(NumericLexer.TokenType.NUMBER) || previousToken.getType().equals(NumericLexer.TokenType.VARIABLE) || previousToken.getType().equals(NumericLexer.TokenType.CLOSEPAREN)|| previousToken.getType().equals(NumericLexer.TokenType.CLOSEBRACKET) || previousToken.getType().equals(NumericLexer.TokenType.E) || previousToken.getType().equals(NumericLexer.TokenType.PI) )
					{
						//deals with subtraction vs negative ambiguity
						NumericLexer.Token positiveNumber = new NumericLexer.Token(NumericLexer.TokenType.NUMBER, toke.getData().substring(1, toke.getData().length()));
						afterInserted = positiveNumber;
						NumericLexer.Token newMinus = new NumericLexer.Token(NumericLexer.TokenType.BINARYOP, "-");
						toke = newMinus;
						anythingInserted = true;
					}
					else
					{
						postfix.add(toke);
					}
				}
				else if(toke.getType().equals(NumericLexer.TokenType.UNARYOP))
				{
					operator.push(toke);
				}
				else if(toke.getType().equals(NumericLexer.TokenType.BINARYOP)) //SEE ABOVE PRECEDENCE
				{
					if(operator.isEmpty() || operator.peek().getType().equals(NumericLexer.TokenType.OPENPAREN) || operator.peek().getType().equals(NumericLexer.TokenType.OPENBRACKET))
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
						if(operator.isEmpty() || operator.peek().getType().equals(NumericLexer.TokenType.OPENPAREN) || operator.peek().getType().equals(NumericLexer.TokenType.OPENBRACKET))
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
							}while(!operator.isEmpty() || operator.peek().getType().equals(NumericLexer.TokenType.OPENPAREN) || operator.peek().getType().equals(NumericLexer.TokenType.OPENBRACKET));
							
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
						if(operator.isEmpty() || operator.peek().getType().equals(NumericLexer.TokenType.OPENPAREN) || operator.peek().getType().equals(NumericLexer.TokenType.OPENBRACKET))
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
						System.out.println("YOU FORGOT A COMBINATION!!!!!!!!!!!!!!!!");
					}
				}
				else if(toke.getType().equals(NumericLexer.TokenType.OPENPAREN) || toke.getType().equals(NumericLexer.TokenType.OPENBRACKET))
				{
					operator.push(toke);
				}
				else if(toke.getType().equals(NumericLexer.TokenType.CLOSEPAREN)|| toke.getType().equals(NumericLexer.TokenType.CLOSEBRACKET))
				{
					try
					{
						if(operator.isEmpty()) throw new Exception();
						
						while (!operator.peek().getType().equals(NumericLexer.TokenType.OPENPAREN) && !operator.peek().getType().equals(NumericLexer.TokenType.OPENBRACKET)) 
						{
							
							try 
							{
								if (operator.isEmpty())
									throw new Exception();
								//System.out.println("At this point, Operator.peek = " + operator.peek() + ". Postfix = " + postfixToString(postfix));
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
						if(operator.peek().getType().equals(NumericLexer.TokenType.UNARYOP))
						{
							postfix.add(operator.pop());
						}
					}
				}
				else if(toke.getType().equals(NumericLexer.TokenType.ARBITRARYCONSTANT))
				{
					throw new NullPointerException("ARBITRARY CONSTANT");
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
		
		
		//System.out.println();
		while(!postfix.isEmpty())
		{
			NumericLexer.Token current = postfix.poll();
			//System.out.print(current.getData().toString() + " ");
			
			if(current.getType().equals(NumericLexer.TokenType.NUMBER))
			{
				exStack.push(new Number(Double.parseDouble(current.getData())));
			}
			else if(current.getType().equals(NumericLexer.TokenType.VARIABLE))
			{
				exStack.push(new Variable(current.getData()));	
			}
			else if(current.getType().equals(NumericLexer.TokenType.NEGATIVENUMBER))
			{
				exStack.push(new Number(Double.parseDouble(current.getData())));
			}
			else if(current.getType().equals(NumericLexer.TokenType.PI))
			{
				exStack.push(new Number(Math.PI));
			}
			else if(current.getType().equals(NumericLexer.TokenType.E))
			{
				exStack.push(new Number(Math.E));
			}
			else if(current.getType().equals(NumericLexer.TokenType.BINARYOP))
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
			else if(current.getType().equals(NumericLexer.TokenType.UNARYOP))
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
				else if(current.getData().equalsIgnoreCase("CubeRoot"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnaryCubeRoot(pop1));
				}
				else if(current.getData().equalsIgnoreCase("Log"))
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
				else if(current.getData().equalsIgnoreCase("Abs"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnaryAbsVal(pop1));
				}
				else if(current.getData().equalsIgnoreCase("Square"))
				{
					Expression pop1 = exStack.pop();
					exStack.push(new UnarySquared(pop1));
				}
				else
				{
					throw new IllegalArgumentException("YOU FORGOT TO HANDLE ALL UNARYOPS" + current.getData());
				}
			}
			else if(current.getType().equals(NumericLexer.TokenType.OPENPAREN) || current.getType().equals(NumericLexer.TokenType.CLOSEPAREN) || current.getType().equals(NumericLexer.TokenType.OPENBRACKET) || current.getType().equals(NumericLexer.TokenType.CLOSEBRACKET))
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
			System.out.println(exStack.pop().unParse());
			throw new IllegalArgumentException("SOMEHOW THERE IS STILL SOMETHING IN EXSTACK");
		}
		
		//lastly, return the expression.
		return output;
	}
	
	//Allows us to check to see if the postfix Queue is correct before it is converted to Expresion form
	private static String postfixToString(Queue postfixAL)
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
