package mathematicaParser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/* OPERATORS
 * <,1
 * >,2
 * <=,3
 * >=,4
 * ==,5
 * !=,6 
 */
public class NumericLexer 
{
	//BINARYOP("[*/+-^]"),
/*} */
	public static enum TokenType 
	  {
		    PI("Pi"),
		    E("E"),
		    NUMBER("[0-9]*[.]?[0-9]+"),
		    NEGATIVENUMBER("[-][0-9]*[.]?[0-9]+"), 
		    WHITESPACE("[ \t\f\r\n]+"),
		    UNARYOP("[A-Z,a-z]{2,}"), 
		    VARIABLE("[a-z]"),
		    ARBITRARYCONSTANT("C\\[" + "[0-9]+" + "\\]" ),
		    OPENBRACKET("\\["),
		  	CLOSEBRACKET("\\]"),
		    OPENPAREN("[(]"), 
		    CLOSEPAREN("[)]"),
		    NUMBERSIGN("#"),
		    BINARYOP("[*/+-^]")
		    ;

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
		  
		  public static String[] lexSimpleInequality(LogicLexer.Token toke) throws PruneException
		  {
			  //We assume that the first element in a simple inequality is a variable x.
			  int index = 0;
			  String str = toke.getData();
			  if(str.charAt(index) != 'x' && str.charAt(index) != 't' && str.charAt(index) != 'p')
			  {
				  throw new PruneException("This simple Inequality does not start with x: " + str);
			  }
			  
			  //Now we have to lex the operator (<,>,==,...) and the bound. 
			  String[] result = {"",""}; 
			  //result[0] = the operator
			  //result[1] = the bound
			  index = 2;
			  result[0] += str.charAt(index);
			  index++;
			  if(str.charAt(index) != ' ')
			  {
				  result[0] += str.charAt(index);
				  index++;
				  index++;
				  result[1] = str.substring(index);
			  }
			  else
			  {
				  index++;
				  result[1] = str.substring(index);
			  }
			  
			  return result;
		  }
		  
		  public static String[] lexComplexInequality(LogicLexer.Token toke)
		  {
			  String[] result = {"","","","",""}; 
			  //result[0] = low bound
			  //result[1] = operator1
			  //result[2] = variable
			  //result[3] = operator2
			  //result[4] = upper bound
			  int index = 11;
			  String str = toke.getData();
			  //LOW BOUND
			  while(str.charAt(index) != ',')
			  {
				  result[0] += str.charAt(index);
				  index++;
			  }
			  index++;
			  index++;
			  //OPERATOR1
			  if(str.charAt(index) == 'L')
			  {
				  if(str.charAt(index + 4) == 'E')
				  {
					  result[1] = "<=";
				  }
				  else
				  {
					  result[1] = "<";
				  }
			  }
			  else if(str.charAt(index) == 'G')
			  {
				  if(str.charAt(index + 7) == 'E')
				  {
					  result[1] = ">=";
				  }
				  else
				  {
					  result[1] = ">";
				  }
			  }
			  else
			  {
				  System.out.println("OPERATOR NOT FOUND IN LEXCOMPLEXINEQUALITY----------------: " + str);
			  }
			  while(str.charAt(index) != ',')
			  {
				  index++;
			  }
			  index++;
			  index++;
			  //VARIABLE
			  result[2] += str.charAt(index);
			  index++;
			  if(str.charAt(index) != ',')
			  {
				  System.out.println("COMMA NOT FOUND IN LEXCOMPLEXINEQUALITY------------------: " + str);
			  }
			  index++;
			  index++;
			  //OPERATOR2
			  if(str.charAt(index) == 'L')
			  {
				  if(str.charAt(index + 4) == 'E')
				  {
					  result[3] = "<=";
				  }
				  else
				  {
					  result[3] = "<";
				  }
			  }
			  else if(str.charAt(index) == 'G')
			  {
				  if(str.charAt(index + 7) == 'E')
				  {
					  result[3] = ">=";
				  }
				  else
				  {
					  result[3] = ">";
				  }
			  }
			  else
			  {
				  System.out.println("OPERATOR NOT FOUND IN LEXCOMPLEXINEQUALITY----------------: " + str);
			  }
			  while(str.charAt(index) != ',')
			  {
				  index++;
			  }
			  index++;
			  index++;
			  //UPPER BOUND
			  result[4] = str.substring(index, str.length()-1);		
			  
			  return result;
		  }
		  
		  public static String[] lexElementOf(LogicLexer.Token toke)
		  {
			  String[] result = {"",""}; 
			  //result[0] = the expression
			  //result[1] = the number system
			  int index = 8;
			  String str = toke.getData();
			  
			  while(str.charAt(index) != ',')
			  {
				  result[0] += str.charAt(index);
				  index++;
			  }
			  index++;
			  index++;
			  result[1] = str.substring(index,str.length()-1);
			  
			  return result;
		  }
		  
		  public static String[] lexNotElementOf(LogicLexer.Token toke)
		  {
			  String[] result = {"",""}; 
			  //result[0] = the expression
			  //result[1] = the number system
			  int index = 11;
			  String str = toke.getData();
			  
			  while(str.charAt(index) != ',')
			  {
				  result[0] += str.charAt(index);
				  index++;
			  }
			  index++;
			  index++;
			  result[1] = str.substring(index,str.length()-1);
			  
			  return result;
		  }

		  public static ArrayList<NumericLexer.Token> lex(String input) throws PruneException
		  {
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
		      if (matcher.group(TokenType.NEGATIVENUMBER.name()) != null) {
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
			  }else if (matcher.group(TokenType.BINARYOP.name()) != null) {
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
		      } else if (matcher.group(TokenType.ARBITRARYCONSTANT.name()) != null){
			    	tokens.add(new Token(TokenType.ARBITRARYCONSTANT, matcher.group(TokenType.ARBITRARYCONSTANT.name())));
			    	throw new PruneException("Here is an ArbitraryConstant: " + input);
		      } else if (matcher.group(TokenType.NUMBERSIGN.name()) != null){
			    	tokens.add(new Token(TokenType.NUMBERSIGN, matcher.group(TokenType.NUMBERSIGN.name())));
			    	throw new PruneException("Here is an NUMBERSIGN: " + input);
		      } else if (matcher.group(TokenType.OPENBRACKET.name()) != null){
			    	tokens.add(new Token(TokenType.OPENBRACKET, matcher.group(TokenType.OPENBRACKET.name())));
			    	continue;
		      } else if (matcher.group(TokenType.CLOSEBRACKET.name()) != null){
			    	tokens.add(new Token(TokenType.CLOSEBRACKET, matcher.group(TokenType.CLOSEBRACKET.name())));
			    	continue;
		      } else if (matcher.group(TokenType.WHITESPACE.name()) != null)
		    	  	continue;
		    }
		    
		    return tokens;
		  }
}
