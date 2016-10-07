package mathematicaParser;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import lexerAndParser.Lexer.Token;
import lexerAndParser.Lexer.TokenType;

public class MathematicaParser {
	
	public static String parseToOneLine(String in) {
		StringTokenizer strTk = new StringTokenizer(in);
		String output = "";
		while (strTk.hasMoreTokens()) {
			output += strTk.nextToken() + "\n";
		}
		return output;
	}
	
	 
	
	/*
	 * public class Lexer 
{
	  public static enum TokenType 
	  {
		    COMMENT("##"),
		    LIMITEXPRESSION("lim " + "[a-z,A-Z]" + "[>][+]?[-]?[ ]" + "[^ ]+" + "[ ]"),  
		    PI("#Pi"),
		    E("#E"),
		    NUMBER("[0-9]*[.]?[0-9]+"),
		    NEGATIVENUMBER("[-][0-9]*[.]?[0-9]+"), BINARYOP("[*+/-^]"), WHITESPACE("[ \t\f\r\n]+"),UNARYOP("[a-z,A-Z]{2,}"), VARIABLE("[a-z,A-Z]"), OPENPAREN("[(]"), 
		    CLOSEPAREN("[)]");

		    public final String pattern;

		    private TokenType(String pattern) 
		    {
		      this.pattern = pattern;
		    }
		  }

		  public static class Token 
		  {
		    public TokenType type;
		    public String data;

		    public Token(TokenType type, String data) 
		    {
		      this.type = type;
		      this.data = data;
		    }
		    
		    public TokenType getType()
		    {
		    	return type;
		    }
		    public String getData()
		    {
		    	return data;
		    }

		    @Override
		    public String toString() 
		    {
		      return String.format("(%s %s)", type.name(), data);
		    }
		  }

		  public ArrayList<Token> lex(String input) {
		    // The tokens to return
		    ArrayList<Token> tokens = new ArrayList<Token>();

		    // Lexer logic begins here
		    StringBuffer tokenPatternsBuffer = new StringBuffer();
		    for (TokenType tokenType : TokenType.values())
		      tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
		    Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

		    // Begin matching tokens
		    Matcher matcher = tokenPatterns.matcher(input);
		    while (matcher.find()) {
		      if(matcher.group(TokenType.COMMENT.name()) != null) {
			    	tokens.add(new Token(TokenType.COMMENT, matcher.group(TokenType.COMMENT.name())));
			    	continue;
		      }else if(matcher.group(TokenType.LIMITEXPRESSION.name()) != null) {
		    	  	tokens.add(new Token(TokenType.LIMITEXPRESSION, matcher.group(TokenType.LIMITEXPRESSION.name())));
		    	  	continue;
		      }else if (matcher.group(TokenType.NEGATIVENUMBER.name()) != null) {
		    	  	tokens.add(new Token(TokenType.NEGATIVENUMBER, matcher.group(TokenType.NEGATIVENUMBER.name())));
		    	  	continue;
		      }else if (matcher.group(TokenType.PI.name()) != null) {
			        tokens.add(new Token(TokenType.PI, matcher.group(TokenType.PI.name())));
			        continue;
		      }else if (matcher.group(TokenType.E.name()) != null) {
			        tokens.add(new Token(TokenType.E, matcher.group(TokenType.E.name())));
			        continue;
			  }else if (matcher.group(TokenType.NUMBER.name()) != null) {
			        tokens.add(new Token(TokenType.NUMBER, matcher.group(TokenType.NUMBER.name())));
			        continue;
			  } else if (matcher.group(TokenType.BINARYOP.name()) != null) {
				  	tokens.add(new Token(TokenType.BINARYOP, matcher.group(TokenType.BINARYOP.name())));
				  	continue;
		      } else if (matcher.group(TokenType.UNARYOP.name()) != null){
		    	  	tokens.add(new Token(TokenType.UNARYOP, matcher.group(TokenType.UNARYOP.name())));
		    	  	continue;
		      } else if (matcher.group(TokenType.OPENPAREN.name()) != null){
			    	tokens.add(new Token(TokenType.OPENPAREN, matcher.group(TokenType.OPENPAREN.name())));
			    	continue;	
		      } else if (matcher.group(TokenType.CLOSEPAREN.name()) != null){
			    	tokens.add(new Token(TokenType.CLOSEPAREN, matcher.group(TokenType.CLOSEPAREN.name())));
			    	continue;
		      } else if (matcher.group(TokenType.VARIABLE.name()) != null){
			    	tokens.add(new Token(TokenType.VARIABLE, matcher.group(TokenType.VARIABLE.name())));
			    	continue;
		      } else if (matcher.group(TokenType.WHITESPACE.name()) != null)
		    	  	continue;
		    }
		    
		    return tokens;
		  }
}

	 */
	
	/* ************ PARSER  *********************
	 * private ArrayList<Variable> _variables; //used to access variables used in the expression.
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
					if(previousToken.getType().equals(TokenType.NEGATIVENUMBER) || previousToken.getType().equals(TokenType.NUMBER) || previousToken.getType().equals(TokenType.VARIABLE) || previousToken.getType().equals(TokenType.CLOSEPAREN) || previousToken.getType().equals(TokenType.E) || previousToken.getType().equals(TokenType.PI) )
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
		
		//System.out.println();
		while(!postfix.isEmpty())
		{
			Lexer.Token current = postfix.poll();
			//System.out.print(current.getData().toString() + " ");
			
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
			System.out.println(exStack.pop().unParse());
			throw new IllegalArgumentException("SOMEHOW THERE IS STILL SOMETHING IN EXSTACK");
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
	 */
	
	
	
	
	
	
	
	
	/*
	public static String parseToOneLine(String in) {
		String out = "";
		if (in.contains("\n")) {
			String[] splitString = in.split("\n");
			
			if (splitString.length == 2) {
				System.out.println("There is at least one exponential.");
			} else if (splitString.length == 3) {
				System.out.println("There is at least one fraction here");
				
			} else if (splitString.length >= 4) {
				System.out.println("There is at least one fraction and one exponent.");
			}
			
			char[][] charMatrix = charMatrix(in);
			
			for (int i = 0; i < charMatrix.length; i++) {
				out += new String(charMatrix[i]) + "\n";
			}
			
		} else {
			out = in;
		}
		return out;
	}
	
	public static char[][] charMatrix(String in) {
		String[] splitString = in.split("\n");
		
		int height = splitString.length;
		int width = 0;
		for (int i = 0; i < splitString.length; i++) {
			if (splitString[i].length() > width) 
				width = splitString[i].length();
		}
		
		char[][] charMatrix = new char[height][width];
		
		for (int i = 0; i < splitString.length; i++) {
			for (int j = 0; j < charMatrix[0].length; j++) {
				int dividingLineStart = -1;
				int dividingLineHeight = -1;
				int dividingLineEnd = -1;
				if (j < splitString[i].length()) 
				{
					charMatrix[i][j] = splitString[i].charAt(j);
					//Potentially process the difference between minus, negative, and division line here.
					if (charMatrix[i][j] == '-' && j + 1 < splitString[i].length()) {
						if ((j - 1 >= 0 && splitString[i].charAt(j - 1) == '-') ||  //See if the locations nearby are valid
							splitString[i].charAt(j + 1) == '-' || 					//See if current character is the character we want.
							(i - 1 >= 0 && i + 1 < splitString.length && j < splitString[i + 1].length() && (splitString[i - 1].charAt(j) != ' ' || //Check and see if the characters above and below are not spaces
							splitString[i + 1].charAt(j) != ' '))) 
						{
							//System.out.println("Dividing Line at: " + i + " " + j);
							if (dividingLineStart == -1) {
								dividingLineStart = j;
								dividingLineHeight = i;
							}
						} 
						else if (splitString[i].charAt(j + 1) != '-' && splitString[i].charAt(j + 1) != ' ') 
						{
							System.out.println("Negative Sign at: " + i + " " + j);
						} 
						else 
						{
							System.out.println("Subtraction at: " + i + " " + j);
						}
					}
					
					if (dividingLineStart != -1 && charMatrix[i][j] != '-') {
						System.out.println("Dividing Line from: " + dividingLineHeight + " " + dividingLineStart + " to " + dividingLineHeight + " " + dividingLineEnd );
						dividingLineStart = -1;
						dividingLineEnd = -1;
						dividingLineHeight = -1;
					}
					
				}
				else 
					charMatrix[i][j] = '@';
				
				
			}
		}
		
		return charMatrix;
	}
	
//	private static boolean characterAboveCurrentCharacter (String[] strings, int y, int x) {
//		boolean bool 
//	}
	
	private static String appendToListInString(String original, String listHeader, String appendedString) {
		String toReturn = original;
		
		if (original.contains(listHeader)) {
			//List is in the string
		}
		
		return toReturn;
	}
	
	public static int countSpacesBeforeFirstCharacter(String in) {
		int counter = 0;
		char ch = in.charAt(0);
		while (ch == ' ') {
			counter++;
			ch = in.charAt(counter);
		}
		return counter;
	}
	*/
}