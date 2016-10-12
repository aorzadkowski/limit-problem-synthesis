package mathematicaParser;

import symbolicSets.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import lexerAndParser.Lexer;

import java.util.Stack;

public class LogicParser 
{
	//converts inList to postfix, then converts all tokens into 
	//LogicStatements (found in symbolicSets) and assembles them into a domain.
	public Domain parseDomain(ArrayList<LogicLexer.Token> inList)
	{
		if(inList.isEmpty()) return new Domain();
		
		Domain result = new Domain();
		
		//First we would need to deal with Leftovers and unincluded closeBrackets.
		
		
		
		//Next we convert the infix logic to postfix.
		Queue<LogicLexer.Token> postfix = new ConcurrentLinkedQueue<LogicLexer.Token>();
		Stack<LogicLexer.Token> operator = new Stack<LogicLexer.Token>();
		
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
		//while(!postfix.isEmpty())
		//{
			
		//}
		
		
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
