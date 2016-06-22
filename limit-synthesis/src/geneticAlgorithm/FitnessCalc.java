package geneticAlgorithm;

import hierarchy.LimitExpression;

public class FitnessCalc 
{
	//temporary fix
	public static int getFitness(LimitExpression individual)
	{
		int fitness = 0;
		if(individual.isContinuousAtTarget())
		{
			fitness++;
		}
		return fitness;
	}
}
