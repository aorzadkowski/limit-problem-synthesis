package geneticAlgorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import crossover.Crossover;
import lexerAndParser.*;
import options.Options;
import hierarchy.*;

public class DriverGenAlg 
{
    /* GA parameters */
    public static final double uniformRate = Options.UNIFORM_RATE;
    public static final int tournamentSize = Options.TOURNAMENT_SIZE;
    private static final boolean elitism = Options.ELITISM;
	
	public static void main(String[] args)
	{
		//How to read/write files in java at https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html
		//READING FILE....................................................................................
		ArrayList<String> LinesOfTextFile = new ArrayList<String>();
		// The name of the file to open.
		String fileName = "GeneticAlgorithmIn.txt";

		// This will reference one line at a time
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			System.out.println("Here is what was in " + fileName);
			int index = 0;
			while((line = bufferedReader.readLine()) != null) {
				System.out.println("At line " + index + ", " + line + " was found.");
				LinesOfTextFile.add(line);
				index++;
			}   
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" +fileName + "'");                
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");                  
		}
		
		
		//WRITING FILE....................................................................................
        String WritefileName = "GeneticAlgorithmOut";
        try {
            FileWriter fileWriter =  new FileWriter(WritefileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            //PROCESSING FILE...........................................................................................
            ArrayList<LimitExpression> LimitExpressionList = new ArrayList<LimitExpression>(); //this will be the list of LimitExpressions.
    		int index = 0;
    		for(String testStr0: LinesOfTextFile)
    		{
    			//String testStr1 = "lim x> #Pi/2 x+2"; //"lim x> 1 reciprocal(2.0*5.0)+3.0+square(6.0*7.0/4.0)+sqrt(absVal(3.0*11.0+18.0))/cos(sin(cos(3.141592653589793/2.0)))"; 
    			System.out.println("Now Processing line: " + index);
    			
    			Lexer alex = new Lexer();
    			ArrayList<Lexer.Token> ALOut= alex.lex(testStr0);
    			Parser prsr = new Parser();
    			Expression e = prsr.parse(ALOut);
    			if(e != null)
    			{
    				LimitExpressionList.add(prsr.getLimit());
    			}
    			index++;
    		}
    		
    		bufferedWriter.write("\nHere is the list of original LimitExpressions");
    		bufferedWriter.newLine();
    		for(LimitExpression l: LimitExpressionList)
    		{
    			bufferedWriter.write(l.unParse());
    			bufferedWriter.newLine();
    		}
    		bufferedWriter.newLine();
    		bufferedWriter.newLine();
    		bufferedWriter.newLine();
    		
    		//
    		//
    		//------------------Evolution begins here.---------------------------------------------------------------------
    		//
    		//
    		
    		
    		LimitExpressionPopulation originalPopulation = new LimitExpressionPopulation(LimitExpressionList);
    		
//    		System.out.println("We will test crossover.\n");
//    		
//    		Expression random1 = originalPopulation.getIndividual((int)(Math.random() * originalPopulation.size())).getFunction();
//    		Expression random2 = originalPopulation.getIndividual((int)(Math.random() * originalPopulation.size())).getFunction();
//    		
//    		Expression crossover = Crossover.crossover(random1, random2);
//    		
//    		System.out.println("A: " + random1.unParse());
//    		System.out.println("B: " + random2.unParse());
//    		System.out.println("C: " + crossover.unParse());
    		
    		
    		System.out.println("Figures messed with and what they should be: tournamentSize = 5, maxSize = 20, minSize = 2, MutationRate = .015");
    		
    		int generationNumber = 1;
    		while(generationNumber < 101)
    		{   			
    			LimitExpressionPopulation newPopulation = new LimitExpressionPopulation(originalPopulation.size(), false);
    			
    			// Keep our best individual
                if (elitism) {
                    newPopulation.saveIndividual(0, originalPopulation.getFittest());
                }

                // Crossover population
                int elitismOffset;
                if (elitism) {
                    elitismOffset = 1;
                } else {
                    elitismOffset = 0;
                }
                
                // Loop over the population size and create new individuals with
                // crossover
                for (int i = elitismOffset; i < originalPopulation.size(); i++) {
                	LimitExpression indiv1 = originalPopulation.tournamentSelection();
                    LimitExpression indiv2 = originalPopulation.tournamentSelection();
                    
//                    while(!indiv1.getFunction().validate())
//                    {
//                        indiv1 = new LimitExpression();
//                    	indiv1.generateLimitExpression();
//                    } 
//                    
//                    while(!indiv2.getFunction().validate())
//                    {
//                    	indiv2 = new LimitExpression();
//                        indiv2.generateLimitExpression();
//                    } 
                    
                    //indiv1 = originalPopulation.getIndividual(i);
                    //indiv2 = originalPopulation.getIndividual(i);

                    double roll = Math.random();
                    if (roll< .5)
                    {
                    	//Expression newExp1 = Crossover.crossover(indiv1.getFunction(), indiv2.getFunction());
                    	//indiv1.setFunction(newExp1);
                    	//indiv1.mutateLimitExpression();
                    	newPopulation.saveIndividual(i, indiv1);
                    }
                    else
                    {
                    	//Expression newExp2 = Crossover.crossover(indiv1.getFunction(), indiv2.getFunction());
                    	//indiv2.setFunction(newExp2);
                    	//indiv2.mutateLimitExpression();
                    	newPopulation.saveIndividual(i, indiv2);
                    }
                }
                
                System.out.println("mutation counter at generation " + generationNumber);
                // Mutate population
                for (int i = elitismOffset; i < newPopulation.size(); i++) 
                {
                	int timesMutated = 0;
                	do
                	{
                		newPopulation.getIndividual(i).mutateLimitExpression();
                		timesMutated++;
                		//System.out.println();
                		//System.out.println(timesMutated);
                	} while((!newPopulation.getIndividual(i).getFunction().validate()) && (timesMutated < 10));//while((timesMutated < 5));//
                	
                	while (!newPopulation.getIndividual(i).getFunction().validate())
                	{
                		LimitExpression newGeneratedLimExp = new LimitExpression();
                		newGeneratedLimExp.generateLimitExpression();
                		newPopulation.saveIndividual(i,newGeneratedLimExp);
                	}
                }
                
                //reReference
                for (int i = elitismOffset; i < newPopulation.size(); i++) 
                {
                	newPopulation.getIndividual(i).getFunction().reReference();
                }
                
                bufferedWriter.write("Here is generation " + generationNumber);
                bufferedWriter.newLine();
                for (int i = 0; i < newPopulation.size(); i++) 
                {
                    bufferedWriter.write(newPopulation.getIndividual(i).getFunction().validate() + " " + newPopulation.getIndividual(i).unParse());
                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
                bufferedWriter.newLine(); 

                originalPopulation = newPopulation;
                
                System.out.println();
                System.out.println(originalPopulation.getFittest().getFitness() + "\t" + originalPopulation.getFittest().unParse());
                generationNumber++;
    		}
    		
            //end file processing.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        }
		
	}
}
