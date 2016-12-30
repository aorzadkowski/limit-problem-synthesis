package mathematicaParser;

import symbolicSets.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import lexerAndParser.Lexer;
import hierarchy.*;

import java.util.Stack;

public class LogicParser 
{
	
	//takes a Token, toke, usually a complex inequality, determines the number of missing close-brackets and close-parentheses
	//there are for the Token, and returns an array of length two with the first entry being the number of missing 
	//parentheses and the second being the number of missing brackets.
	public static int[] countParenthesesAndBrackets(LogicLexer.Token toke)
	{
		//a[0] = number of missing parentheses
		//a[1] = number of missing brackets
		int[] a = {0,0};
		
		if(toke.getType().equals(LogicLexer.TokenType.COMPLEXINEQUALITY))
		{
			String data = toke.getData();
			
			int commas = 0;
			//go to last comma, begin counting parentheses. Do not forget the opening bracket of the inequality
			for(int i = 0; i < data.length(); i++)
			{
				if(data.charAt(i) == ',')
				{
					commas++;
				}
				else if(commas == 4)
				{
					//System.out.println(data.substring(i) + " " + a[0] + " " + a[1] + " " + data.charAt(i)); //for debugging
					switch(data.charAt(i))
					{
					case '(': a[0]++; break;
					case ')': a[0]--; break;
					case '[': a[1]++; break;
					case ']': a[1]--; break;
					default: break;
					}
				}
			}
		}
		
		a[1]++;//We also need the bracket that closes the bracket opened at the beginning of the inequality
		
		return a;
	}
	
	
	//takes the infix-oriented tokens from the lexer and cleans the list so that all LEFTOVERS are
	//resolved before passing the tokens to the parser. 
	private ArrayList<LogicLexer.Token> preParse(ArrayList<LogicLexer.Token> inList)
	{
		
		//LogicLexer.Token previous = null;
		for(int i = 0; i < inList.size(); i ++)
		{
			if(inList.get(i).getType().equals(LogicLexer.TokenType.COMPLEXINEQUALITY))
			{
				int[] missing = countParenthesesAndBrackets(inList.get(i)); //find out how many missing close 
																//parentheses and brackets the current complex inequality has.
				while(missing[0] != 0 || missing[1] !=0)
				{
					//System.out.println("current inequality " + inList.get(i).getData() + ", current missing characters: " );
					String str = "";
					String leftover = inList.get(i+1).getData(); // save the leftover data
					inList.remove(i+1); // get rid of the leftover
					str = inList.get(i).getData(); // get the data of the original complex inequality
					str += leftover; // add the leftover data
					inList.remove(i); //remove the old complex inequality
					inList.add(i, new LogicLexer.Token(LogicLexer.TokenType.COMPLEXINEQUALITY, str));//add the new complex inequality
				
					missing = countParenthesesAndBrackets(inList.get(i));
				}
			}			
		}
		//TODO FIX OTHER PARSING PROBLEMS
		
		
		//if there are no leftovers, the list is already clean
		return inList;
	}
	
	//converts inList to postfix, then converts all tokens into 
	//LogicStatements (found in symbolicSets) and assembles them into a domain.
	public Domain parseDomain(ArrayList<LogicLexer.Token> inList)
	{
		//clean inList
		inList = preParse(inList);
		
		if(inList.isEmpty()) return new Domain();
		
		Domain result = new Domain();
		
		//First we would need to deal with Leftovers and unincluded closeBrackets.
		
		
		
		//Next we convert the infix logic to postfix.
		Queue<LogicLexer.Token> postfix = new ConcurrentLinkedQueue<LogicLexer.Token>();
		Stack<LogicLexer.Token> operator = new Stack<LogicLexer.Token>();
		Stack<LogicStatements> logicStatementsStack = new Stack<LogicStatements>();
		
		for(LogicLexer.Token toke: inList)
		{
			if(toke.getType().equals(LogicLexer.TokenType.TRUE))
			{
				return new Domain();
			}
			else if(toke.getType().equals(LogicLexer.TokenType.FALSE))
			{
				return new Domain(new NotElementOf(new hierarchy.Number(1), new Reals()));
			}
			else if(toke.getType().equals(LogicLexer.TokenType.SIMPLEINEQUALITY))
			{
				postfix.add(toke);
			}
			else if(toke.getType().equals(LogicLexer.TokenType.COMPLEXINEQUALITY))
			{
				postfix.add(toke);
			}
			else if(toke.getType().equals(LogicLexer.TokenType.ELEMENT))
			{
				postfix.add(toke);
			}
			else if(toke.getType().equals(LogicLexer.TokenType.NOTELEMENT))
			{
				postfix.add(toke);
			}
			else if(toke.getType().equals(LogicLexer.TokenType.AND))
			{
				if(operator.isEmpty() || operator.peek().getType().equals(LogicLexer.TokenType.OPENPAREN))
				{
					operator.push(toke);
				}
				else
				{
					//Left right association
					postfix.add(operator.pop());
					operator.push(toke);
				}				
			}
			else if(toke.getType().equals(LogicLexer.TokenType.OR))
			{
				if(operator.isEmpty() || operator.peek().getType().equals(LogicLexer.TokenType.OPENPAREN))
				{
					operator.push(toke);
				}
				else
				{
					//Left right association
					postfix.add(operator.pop());
					operator.push(toke);
				}	
			}
			else if(toke.getType().equals(LogicLexer.TokenType.OPENPAREN))
			{
				operator.push(toke);
			}
			else if(toke.getType().equals(LogicLexer.TokenType.CLOSEPAREN))
			{
				try
				{
					if(operator.isEmpty()) throw new Exception();
					
					while (!operator.peek().getType().equals(LogicLexer.TokenType.OPENPAREN)) 
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
			}
			
		}
		//Adds any remaining operators to the Queue
		while(!operator.isEmpty())
		{
			postfix.add(operator.pop());
		}
		//Done converting to postfix. Printing to verify...
		System.out.println(postfixToString(postfix));
		
		//Lastly, we convert the tokens into LogicStatements and create a domain
		while(!postfix.isEmpty())
		{
			LogicLexer.Token current = postfix.poll();
			
			try {
				if(current.getType().equals(LogicLexer.TokenType.SIMPLEINEQUALITY))
				{
					//create the appropriate simple inequality.
					ArrayList<NumericLexer.Token> lexedTokens = NumericLexer.lex(NumericLexer.lexSimpleInequality(current)[1]);
					
					String symbol = NumericLexer.lexSimpleInequality(current)[0];
					if(symbol.equalsIgnoreCase("<"))
					{
						logicStatementsStack.push(new LessThan(new Variable("x"), NumericParser.parse(lexedTokens)));
					}
					else if(symbol.equalsIgnoreCase(">"))
					{
						logicStatementsStack.push(new GreaterThan(new Variable("x"), NumericParser.parse(lexedTokens)));
					}
					else if(symbol.equalsIgnoreCase("<="))
					{
						logicStatementsStack.push(new LessThanOrEqualTo(new Variable("x"), NumericParser.parse(lexedTokens)));
					}
					else if(symbol.equalsIgnoreCase(">="))
					{
						logicStatementsStack.push(new GreaterThanOrEqualTo(new Variable("x"), NumericParser.parse(lexedTokens)));
					}
					else if(symbol.equalsIgnoreCase("=="))
					{
						logicStatementsStack.push(new EqualTo(new Variable("x"), NumericParser.parse(lexedTokens)));
					}
					else if(symbol.equalsIgnoreCase("!="))
					{
						logicStatementsStack.push(new NotEqualTo(new Variable("x"), NumericParser.parse(lexedTokens)));
					}
					else
					{
						throw new IllegalArgumentException("Symbol was not what was expected: " + symbol);
					}
				}
				else if(current.getType().equals(LogicLexer.TokenType.COMPLEXINEQUALITY))
				{
					//create the appropriate complex inequality
					ArrayList<NumericLexer.Token> lexedExpression0 = NumericLexer.lex(NumericLexer.lexComplexInequality(current)[0]);
					ArrayList<NumericLexer.Token> lexedExpression4 = NumericLexer.lex(NumericLexer.lexComplexInequality(current)[4]);
					
					String op1 = NumericLexer.lexComplexInequality(current)[1];
					String op2 = NumericLexer.lexComplexInequality(current)[3];
					
					if(op1.equalsIgnoreCase("<") && op2.equalsIgnoreCase("<"))
					{
						logicStatementsStack.push(new LessThanLessThan(NumericParser.parse(lexedExpression0), new Variable("x"), NumericParser.parse(lexedExpression4)));
					}
					else if(op1.equalsIgnoreCase(">") && op2.equalsIgnoreCase(">"))
					{
						logicStatementsStack.push(new GreaterThanGreaterThan(NumericParser.parse(lexedExpression0), new Variable("x"), NumericParser.parse(lexedExpression4)));
					}
					else if(op1.equalsIgnoreCase("<") && op2.equalsIgnoreCase("<="))
					{
						logicStatementsStack.push(new LessThanLessThanOrEqualTo(NumericParser.parse(lexedExpression0), new Variable("x"), NumericParser.parse(lexedExpression4)));
					}
					else if(op1.equalsIgnoreCase(">") && op2.equalsIgnoreCase(">="))
					{
						logicStatementsStack.push(new GreaterThanGreaterThanOrEqualTo(NumericParser.parse(lexedExpression0), new Variable("x"), NumericParser.parse(lexedExpression4)));
					}
					else if(op1.equalsIgnoreCase("<=") && op2.equalsIgnoreCase("<"))
					{
						logicStatementsStack.push(new LessThanOrEqualToLessThan(NumericParser.parse(lexedExpression0), new Variable("x"), NumericParser.parse(lexedExpression4)));
					}
					else if(op1.equalsIgnoreCase(">=") && op2.equalsIgnoreCase(">"))
					{
						logicStatementsStack.push(new GreaterThanOrEqualToGreaterThan(NumericParser.parse(lexedExpression0), new Variable("x"), NumericParser.parse(lexedExpression4)));
					}
					else
					{
						throw new NullPointerException("Symbol was not what was expected: " + op1 +  ", " + op2);
					}
				}
				else if(current.getType().equals(LogicLexer.TokenType.ELEMENT))
				{
					//create the appropriate element.
					ArrayList<NumericLexer.Token> lexedTokens = NumericLexer.lex(NumericLexer.lexElementOf(current)[0]);
					
					String system = NumericLexer.lexElementOf(current)[1];
					if(system.equalsIgnoreCase("Integers"))
					{
						logicStatementsStack.push(new ElementOf(NumericParser.parse(lexedTokens), new Integers()));
					}
					else if(system.equalsIgnoreCase("Reals"))
					{
						logicStatementsStack.push(new ElementOf(NumericParser.parse(lexedTokens), new Reals()));
					}
					else
					{
						throw new IllegalArgumentException("System was not what was expected: " + system);
					}
				}
				else if(current.getType().equals(LogicLexer.TokenType.NOTELEMENT))
				{
					//create the appropriate not element.
					ArrayList<NumericLexer.Token> lexedTokens = NumericLexer.lex(NumericLexer.lexNotElementOf(current)[0]);
					
					String system = NumericLexer.lexNotElementOf(current)[1];
					if(system.equalsIgnoreCase("Integers"))
					{
						logicStatementsStack.push(new NotElementOf(NumericParser.parse(lexedTokens), new Integers()));
					}
					else if(system.equalsIgnoreCase("Reals"))
					{
						logicStatementsStack.push(new NotElementOf(NumericParser.parse(lexedTokens), new Reals()));
					}
					else
					{
						throw new IllegalArgumentException("System was not what was expected: " + system);
					}
				}
				else if(current.getType().equals(LogicLexer.TokenType.AND))
				{
					LogicStatements pop1 = logicStatementsStack.pop();
					LogicStatements pop2 = logicStatementsStack.pop();
					logicStatementsStack.push(new And(pop2, pop1));
				}
				else if(current.getType().equals(LogicLexer.TokenType.OR))
				{
					LogicStatements pop1 = logicStatementsStack.pop();
					LogicStatements pop2 = logicStatementsStack.pop();
					logicStatementsStack.push(new Or(pop2, pop1));
				}
				else if(current.getType().equals(LogicLexer.TokenType.OPENPAREN) || current.getType().equals(LogicLexer.TokenType.CLOSEPAREN) || current.getType().equals(LogicLexer.TokenType.OPENBRACKET) || current.getType().equals(LogicLexer.TokenType.CLOSEBRACKET))
				{
					//There should not be ANY parentheses in TOEXPRESSION phase.
					throw new IllegalArgumentException("Parentheses do not match in TOEXPRESSION phase");
				}
				else
				{
					System.err.println("Something else is wrong in the TOEXPRESSION phase. Unhandled ______: " + current.toString());
				}
			} catch (NullPointerException e) {
				
				e.printStackTrace();
				return result;
			}
		}
		
		if(!logicStatementsStack.isEmpty())
		{
			result = new Domain(logicStatementsStack.pop());
		}
		
		return result;
	}
	
	//Allows us to check to see if the postfix Queue is correct before it is converted to Expresion form
	private String postfixToString(Queue postfixAL)
	{
		String str = "";
		Queue<LogicLexer.Token> postfixClone = new ConcurrentLinkedQueue<LogicLexer.Token>(postfixAL);
		while(!postfixClone.isEmpty())
		{
			str += postfixClone.poll() + "\n";
		}		
		return str;
	}
}
