package lexerAndParser;

import java.util.ArrayList;
import java.util.regex.*;

public class Lexer 
{
	  public static enum TokenType 
	  {
		    COMMENT("##"),
		    LIMITEXPRESSION("lim " + "[a-z,A-Z]" + "[>][+]?[-]?[ ]" + "[-]?[0-9]*[.]?[0-9]+" + "[ ]"),  
		    PI("#Pi"),
		    E("#E"),
		    NUMBER("[0-9]*[.]?[0-9]+"),
		    NEGATIVENUMBER("[-][0-9]*[.]?[0-9]+"), BINARYOP("[*/+-^]"), WHITESPACE("[ \t\f\r\n]+"),UNARYOP("[a-z,A-Z]{2,}"), VARIABLE("[a-z,A-Z]"), OPENPAREN("[(]"), 
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
