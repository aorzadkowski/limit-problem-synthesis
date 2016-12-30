//Breaks a string into Logical tokens to be handled by the parser.
package mathematicaParser;

import java.util.ArrayList;
import java.util.regex.*;

public class LogicLexer
{
	//("lim " + "[a-z,A-Z]" + "[>][+]?[-]?[ ]" + "[^ ]+" + "[ ]"), 
	//SIMPLEINEQUALITY( "[^=!<>]+"  + "[ ]" + "[=]?[!]?[<]?[>]?[=]?" + "[ ]" + "[-]?[0-9]*[.]?[0-9]+"),
	//SURD("Surd\\[" + "[^,]+" + ", " + "[^\\]]+" + "\\]"),
	//}else if(matcher.group(TokenType.SURD.name()) != null) {
  	//tokens.add(new Token(TokenType.SURD, matcher.group(TokenType.SURD.name())));
  	//continue;
	public static enum TokenType 
	  {
		    COMMENT("##"),
		    TRUE("True"),
		    FALSE("False"),
		    ARBITRARYCONSTANT("C\\[" + "[0-9]+" + "\\]" ),
		    SIMPLEINEQUALITY("[a-z,A-Z]" + "[ ]" + "[=]?[!]?[<]?[>]?[=]?" + "[ ]" + "[^( |\\))]+"),
		    AND("&&"),
		    OR("[\\|]" + "[\\|]"),
		    COMPLEXINEQUALITY("Inequality\\[" + "[^,]+" + ", " + "[^,]+" + ", " + "[^,]+" + ", " + "[^,]+" + ", " + "[^\\]]+" + "\\]"),
		    ELEMENT("Element\\[" + "[^,]+" + ", " + "[^\\]]+" + "\\]"),
		    NOTELEMENT("NotElement\\[" + "[^,]+" + ", " + "[^\\]]+" + "\\]"),
		    WHITESPACE("[ \t\f\r\n]+"),
		    OPENPAREN("[(]"), 
		    CLOSEPAREN("[)]"),
		  	OPENBRACKET("\\["),
		  	CLOSEBRACKET("\\]"),
		  	LEFTOVERS("[^\\]]+");
		  	

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
			    	return tokens;
		      }else if (matcher.group(TokenType.TRUE.name()) != null) {
			        tokens.add(new Token(TokenType.TRUE, matcher.group(TokenType.TRUE.name())));
			        continue;
		      }else if (matcher.group(TokenType.FALSE.name()) != null) {
			        tokens.add(new Token(TokenType.FALSE, matcher.group(TokenType.FALSE.name())));
			        continue;
		      }else if(matcher.group(TokenType.ARBITRARYCONSTANT.name()) != null) {
		    	  	tokens.add(new Token(TokenType.ARBITRARYCONSTANT, matcher.group(TokenType.ARBITRARYCONSTANT.name())));
		    	  	continue;
		      }else if(matcher.group(TokenType.SIMPLEINEQUALITY.name()) != null) {
		    	  	tokens.add(new Token(TokenType.SIMPLEINEQUALITY, matcher.group(TokenType.SIMPLEINEQUALITY.name())));
		    	  	continue;
		      }else if (matcher.group(TokenType.COMPLEXINEQUALITY.name()) != null) {
		    	  	tokens.add(new Token(TokenType.COMPLEXINEQUALITY, matcher.group(TokenType.COMPLEXINEQUALITY.name())));
		    	  	continue;
		      }else if (matcher.group(TokenType.ELEMENT.name()) != null) {
		    	  	tokens.add(new Token(TokenType.ELEMENT, matcher.group(TokenType.ELEMENT.name())));
		    	  	continue;
		      }else if (matcher.group(TokenType.NOTELEMENT.name()) != null) {
		    	  	tokens.add(new Token(TokenType.NOTELEMENT, matcher.group(TokenType.NOTELEMENT.name())));
		    	  	continue;
		      }else if (matcher.group(TokenType.AND.name()) != null) {
			        tokens.add(new Token(TokenType.AND, matcher.group(TokenType.AND.name())));
			        continue;
		      }else if (matcher.group(TokenType.OR.name()) != null) {
			        tokens.add(new Token(TokenType.OR, matcher.group(TokenType.OR.name())));
			        continue;
		      } else if (matcher.group(TokenType.OPENPAREN.name()) != null){
			    	tokens.add(new Token(TokenType.OPENPAREN, matcher.group(TokenType.OPENPAREN.name())));
			    	continue;	
		      } else if (matcher.group(TokenType.CLOSEPAREN.name()) != null){
			    	tokens.add(new Token(TokenType.CLOSEPAREN, matcher.group(TokenType.CLOSEPAREN.name())));
			    	continue;
		      } else if (matcher.group(TokenType.OPENBRACKET.name()) != null){
			    	tokens.add(new Token(TokenType.OPENBRACKET, matcher.group(TokenType.OPENBRACKET.name())));
			    	continue;
		      } else if (matcher.group(TokenType.CLOSEBRACKET.name()) != null){
			    	tokens.add(new Token(TokenType.CLOSEBRACKET, matcher.group(TokenType.CLOSEBRACKET.name())));
			    	System.out.println("-------------------------------------------------------------------------------------------------------OH NO! AN UNINCLUDED BRACKET");
			    	continue;
		      } else if (matcher.group(TokenType.LEFTOVERS.name()) != null){
			    	tokens.add(new Token(TokenType.LEFTOVERS, matcher.group(TokenType.LEFTOVERS.name())));
			    	System.out.println("------------------------OH NO! --------------------------------------------------------------------------LEFTOVERS");
			    	continue;
		      } else if (matcher.group(TokenType.WHITESPACE.name()) != null)
		    	  	continue;
		    }
		    
		    return tokens;
		  }
}
