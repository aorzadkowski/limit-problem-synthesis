//class which represents a population of LimitExpressions
package geneticAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        // Initialise population
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
}
