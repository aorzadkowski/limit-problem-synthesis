//class which represents a population of LimitExpressions
package geneticAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hierarchy.Expression;
import hierarchy.LimitExpression;
import lexerAndParser.*;

public class LimitExpressionPopulation 
{
	LimitExpression[] individuals;

    /*
     * Constructors
     */
    // Create a population
    public LimitExpressionPopulation(int populationSize, boolean initialise) {
        individuals = new LimitExpression[populationSize];
        // Initialize population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < size(); i++) {
                LimitExpression newIndividual = new LimitExpression();							//could be a problem
                newIndividual.generateLimitExpression();
                saveIndividual(i, newIndividual);
            }
        }
    }
    
    //creates a population from an ArrayList of sample LimitExpressions.
    public LimitExpressionPopulation(ArrayList<LimitExpression> sample)
    {
    	individuals = new LimitExpression[sample.size()];
    	int index = 0;
    	for(LimitExpression l: sample)
    	{
    		individuals[index] = l;
    		index++;
    	}
    	
    }

    /* Getters */
    public LimitExpression getIndividual(int index)
    {
        return individuals[index];
    }

    public LimitExpression getFittest() {
        LimitExpression fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /* Public methods */
    // Get population size
    public int size() 
    {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, LimitExpression indiv) {
        individuals[index] = indiv;
    }
    
    public static LimitExpression tournamentSelection(LimitExpressionPopulation pop)
    {
    	// Create a tournament population
        LimitExpressionPopulation tournament = new LimitExpressionPopulation(DriverGenAlg.tournamentSize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < DriverGenAlg.tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        LimitExpression fittest = tournament.getFittest();
        
        return fittest;
    }
    
    public LimitExpression tournamentSelection()
    {
    	// Create a tournament population
        LimitExpressionPopulation tournament = new LimitExpressionPopulation(DriverGenAlg.tournamentSize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < DriverGenAlg.tournamentSize; i++) {
            int randomId = (int) (Math.random() * individuals.length);
            LimitExpression newIndiv = individuals[randomId];
            tournament.saveIndividual(i, newIndiv);
            
        }
        // Get the fittest
        LimitExpression fittest = tournament.getFittest();
        
        LimitExpression newFittest = new LimitExpression(fittest);
        return newFittest;
        
//        String targetString = fittest.getFunction().unParse();
//        try
//        {
//			Lexer LEPLexer = new Lexer();
//			Parser LEPParser = new Parser();
//			ArrayList<Lexer.Token> LEPLOutput= LEPLexer.lex(targetString);
//			Expression newExp = LEPParser.parse(LEPLOutput);
//            
//			LimitExpression newIndiv = new LimitExpression(fittest.getStringRepresentation(), newExp);
//            //LimitExpression newIndiv = individuals[randomId];
//            fittest = newIndiv;
//        }
//        catch(Exception e)
//        {
//        	System.out.println(targetString);
//        	e.printStackTrace();
//        }
        
//        String targetString = individuals[randomId].getFunction().unParse();
//        try
//        {
//			Lexer LEPLexer = new Lexer();
//			Parser LEPParser = new Parser();
//			ArrayList<Lexer.Token> LEPLOutput= LEPLexer.lex(targetString);
//			Expression newExp = LEPParser.parse(LEPLOutput);
//            
//			LimitExpression newIndiv = new LimitExpression(individuals[randomId].getStringRepresentation(), newExp);
//            //LimitExpression newIndiv = individuals[randomId];
//            tournament.saveIndividual(i, newIndiv);
//        }
//        catch(Exception e)
//        {
//        	System.out.println(targetString);
//        	e.printStackTrace();
//        	
//        }
        
        //return fittest;
    }
}
