package lexerAndParser;

import java.io.*;
import hierarchy.*;
import java.util.ArrayList;
import java.util.*;

public class Driver2 
{
	public static void main(String[] args)
	{
		
		//READING FILE....
		ArrayList<String> LinesOfTextFile = new ArrayList<String>();
	      
		// The name of the file to open.
        String fileName = "NotepadTextTest.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            System.out.println("Here is what was in the text file");
            int index = 0;
            while((line = bufferedReader.readLine()) != null) {
                System.out.println("At line " + index + ", " + line + " was found.");
                LinesOfTextFile.add(line);
                index++;
            }   

            // Always close files.
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
        ArrayList<LimitExpression> LimitExpressionList = new ArrayList<LimitExpression>(); //this will be the list of LimitExpressions.
		int index = 0;
		for(String testStr0: LinesOfTextFile)
		{
			//String testStr1 = "0-x+2"; //"lim x> 1 reciprocal(2.0*5.0)+3.0+square(6.0*7.0/4.0)+sqrt(absVal(3.0*11.0+18.0))/cos(sin(cos(3.141592653589793/2.0)))"; 
			System.out.println();
			System.out.println("Now Processing line: " + index);
			
			Lexer alex = new Lexer();
			ArrayList<Lexer.Token> ALOut= alex.lex(testStr0);
			
//			System.out.println("Here is the Tokenized infix ArrayList.");
//			for(Lexer.Token toke: ALOut)
//			{
//				System.out.println(toke);
//			}
			
			System.out.println("Now to postfix------------------------");
			Parser prsr = new Parser();
			Expression e = prsr.parse(ALOut);
			if(e != null)
			{
				LimitExpressionList.add(prsr.getLimit());
				System.out.println("Here is the LimitExpression " + prsr.getLimit().unParse());
				
				System.out.println("Here is the Expression " + e.unParse());
				
				//now to evaluate for the limit expression variable
				HashMap m = new HashMap<Variable, Double>();
				if(!prsr.getVar().contains(prsr.getLimit().getVariable()))
				{
					System.out.println("the variable in the limit expression is not in the function being evaluated");
				}
				else
				{
					Double varEquals = (1.0);
					System.out.println("solving for " + prsr.getLimit().getVariable().getName() + " = " + varEquals + " & ");
					m.put(prsr.getLimit().getVariable(), varEquals);
					varEquals = null;
				}

				System.out.println("The value of the function when evaluating for those variables is " + e.evaluate(m));
				m.clear();
				prsr.clearVar();
				prsr.clearLimit();
			}
			index++;
		}
		
		System.out.println("\nHere is the list of Limits ");
		for(LimitExpression l : LimitExpressionList)
		{
			System.out.println(l.translateToWolfram());
		}
		
		
		//Questions:
		//How to format doubles
		//You can't have _target be Pi or E or Pi/2 yet.
		//Parenthesis in Mathematica
		//deal with constants											X
		//push to github
		//incorporate limits into parser								X
		//How are variables going to work?								X							
		//Right now, you can't do -x+2									
		//what is a limit going to be?									X				
		//Should we change posIntPowers to a Binary operation?			X
		//Any ideas on how to deal with absval? ||a|*b|					X
		//Do we need reciprocal or squared?								X
		//What was the difference between unparse and toString?        	X
		//Is this multiplication? (a)(b)							NO	X
		//There is a problem when you do a*b+c							X
		
	}
}
