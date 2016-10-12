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
		        //ArrayList<LimitExpression> LimitExpressionList = new ArrayList<LimitExpression>(); //this will be the list of LimitExpressions.
				int index = 0;
				for(String testStr0: LinesOfTextFile)
				{
					//String testStr1 = "lim x> #Pi/2 x+2"; //"lim x> 1 reciprocal(2.0*5.0)+3.0+square(6.0*7.0/4.0)+sqrt(absVal(3.0*11.0+18.0))/cos(sin(cos(3.141592653589793/2.0)))"; 
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
//					if(e != null)
//					{
//						LimitExpressionList.add(prsr.getLimit());
//						System.out.println("Here is the LimitExpression " + prsr.getLimit().unParse());
//						
//						//System.out.println("Here is the Expression " + e.unParse());
//						
//						//now to evaluate for the limit expression variable
//						HashMap m = new HashMap<Variable, Double>();
//						if(!prsr.getVar().contains(prsr.getLimit().getVariable()))
//						{
//							System.out.println("the variable in the limit expression is not in the function being evaluated");
//						}
//						else
//						{
//							Double varEquals = prsr.getLimit().getTargetDouble();
//							System.out.println("solving for " + prsr.getLimit().getVariable().getName() + " = " + varEquals + " & ");
//							m.put(prsr.getLimit().getVariable(), varEquals);
//							varEquals = null;
//						}
//
//						System.out.println("The value of the function when evaluating for those variables is " + e.evaluate(m));
//						m.clear();
//						prsr.clearVar();
//						prsr.clearLimit();
//					}
					index++;
//				}
//				
//				System.out.println("\nHere is the list of Limits ");
//				for(LimitExpression l : LimitExpressionList)
//				{
//					System.out.println(l.translateToWolfram());
//					if(l.isContinuousAtTarget())
//					{
//						System.out.println("And is it continuous at the approach value?	" 
//								+ l.isContinuousAtTarget() + "    And the value at that point is " + l.evaluate());
//					}
//					else
//					{
//						System.out.println("And is it continuous at the approach value?	" 
//								+ l.isContinuousAtTarget());
//					}
//					System.out.println("And the functionSize is:        \t" + l.functionSize());
//					System.out.println("And the left handed behavior is:\t" + l.leftHandBehaviorAtTarget());
//					System.out.println("And the right handed behavior is:\t" + l.rightHandBehaviorAtTarget());
//					System.out.println();			
				}
				
				
				
				
	}	
}
