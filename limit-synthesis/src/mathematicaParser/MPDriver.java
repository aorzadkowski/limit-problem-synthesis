package mathematicaParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import symbolicSets.*;

import hierarchy.Expression;
import hierarchy.Variable;


public class MPDriver 
{
	public static void main(String[] args)
	{
		//READING FILE....
				ArrayList<String> LinesOfTextFile = new ArrayList<String>();
			      
				// The name of the file to open.
		        String fileName = "MPText.txt";

		        // This will reference one line at a time
		        String line = null;

		        try {
		            FileReader fileReader = 
		                new FileReader(fileName);

		            BufferedReader bufferedReader = 
		                new BufferedReader(fileReader);

		            System.out.println("Here is what was in the text file");
		            int index = 0;
		            while((line = bufferedReader.readLine()) != null) {
		                System.out.println("At line " + index + ", " + line + " was found.");
		                LinesOfTextFile.add(line);
		                index++;
		            }   

		            bufferedReader.close();         
		        }
		        catch(FileNotFoundException ex) {
		            System.out.println(
		                "Unable to open file '" + 
		                fileName + "'");                
		        }
		        catch(IOException ex) {
		            System.out.println(
		                "Error reading file '" 
		                + fileName + "'");                  
		            // Or we could just do this: 
		            // ex.printStackTrace();
		        }
				
		        //PROCESSING FILE...
				int index = 0;
				for(String testStr0: LinesOfTextFile)
				{
					System.out.println();
					System.out.println("Now Processing line: " + index);
					
					LogicLexer alex = new LogicLexer();
					ArrayList<LogicLexer.Token> ALOut= alex.lex(testStr0);
					
					System.out.println("Here is the Tokenized infix ArrayList.");
					
					
					
					for(LogicLexer.Token toke: ALOut)
					{
						if(!toke.getType().equals(LogicLexer.TokenType.ARBITRARYCONSTANT))
						{
							System.out.println(toke);
						}
						else
						{
							System.out.println("The rest was probably caused by the arbitrary constant.");
							break;
						}						
					}
								
					
					System.out.println("Now to postfix------------------------");
					LogicParser prsr = new LogicParser();
					Domain d = prsr.parseDomain(ALOut);
					System.out.println("Here is the domain: ");
					System.out.println(d.toString());
					index++;	
				}
				
				//further tests
//				LogicLexer.Token testToke = new LogicLexer.Token(LogicLexer.TokenType.COMPLEXINEQUALITY, "Inequality[(-3*Pi + 4*Pi*C[1])/4, Less, x, Less, (Pi + 4*Pi*C[1]");
//				System.out.println("Testing count parens and bracks: " + testToke.getData());
//				System.out.println("Missing parens: " + LogicParser.countParenthesesAndBrackets(testToke)[0] +  ", Missing brackets: " + LogicParser.countParenthesesAndBrackets(testToke)[1]);
				
				/*LogicLexer.Token testToke = new LogicLexer.Token(LogicLexer.TokenType.SIMPLEINEQUALITY, "x <= 11");
				System.out.println("Testing simpleinequality lexer: " + testToke.getData());
				System.out.println("operator: " + NumericLexer.lexSimpleInequality(testToke)[0] +  ", bound: " + NumericLexer.lexSimpleInequality(testToke)[1]);
				*/
				/*LogicLexer.Token testToke = new LogicLexer.Token(LogicLexer.TokenType.COMPLEXINEQUALITY, "Inequality[-Sqrt[2 + E], Less, x, Less, Sqrt[2 + E]]");
				System.out.println("Testing complexinequality lexer: " + testToke.getData());
				System.out.println("LOW BOUND: " + NumericLexer.lexComplexInequality(testToke)[0] +  ", OPERATOR1: " + NumericLexer.lexComplexInequality(testToke)[1] +  ", VARIABLE: " + NumericLexer.lexComplexInequality(testToke)[2] +  ", OPERATOR2: " + NumericLexer.lexComplexInequality(testToke)[3] +  ", UPPER BOUND: " + NumericLexer.lexComplexInequality(testToke)[4]);
*/
				/*LogicLexer.Token testToke = new LogicLexer.Token(LogicLexer.TokenType.ELEMENT, "Element[1/2 + x/Pi, Integers]");
				System.out.println("Testing simpleinequality lexer: " + testToke.getData());
				System.out.println("Expression: " + NumericLexer.lexElementOf(testToke)[0] +  ", numberSystem: " + NumericLexer.lexElementOf(testToke)[1]);
				*/
				/*LogicLexer.Token testToke = new LogicLexer.Token(LogicLexer.TokenType.NOTELEMENT, "NotElement[1/2 + x/Pi, Integers]");
				System.out.println("Testing simpleinequality lexer: " + testToke.getData());
				System.out.println("Expression: " + NumericLexer.lexNotElementOf(testToke)[0] +  ", numberSystem: " + NumericLexer.lexNotElementOf(testToke)[1]);
*/
				/*System.out.println("Testing NumericLexer and NumericParser");
				LogicLexer.Token testToke = new LogicLexer.Token(LogicLexer.TokenType.COMPLEXINEQUALITY, "Inequality[Cos[12/2], Less, x, Less, 72]");
				System.out.println("LOW BOUND: " + NumericLexer.lexComplexInequality(testToke)[0] +  ", OPERATOR1: " + NumericLexer.lexComplexInequality(testToke)[1] +  ", VARIABLE: " + NumericLexer.lexComplexInequality(testToke)[2] +  ", OPERATOR2: " + NumericLexer.lexComplexInequality(testToke)[3] +  ", UPPER BOUND: " + NumericLexer.lexComplexInequality(testToke)[4]);
				System.out.println("Lexing low Bound: ");
				ArrayList<NumericLexer.Token> lowBoundLexedTokens = NumericLexer.lex(NumericLexer.lexComplexInequality(testToke)[0]);
				for(NumericLexer.Token aToke: lowBoundLexedTokens)
				{
					System.out.println(aToke);
				}
				Expression lowBoundExpression = NumericParser.parse(lowBoundLexedTokens);
				System.out.println("Here is the low bound expression: " + lowBoundExpression.toWolf());
				*/
	}	
}
