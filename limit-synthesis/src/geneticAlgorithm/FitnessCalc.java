package geneticAlgorithm;

import hierarchy.LimitExpression;
import fitness.*;

public class FitnessCalc 
{
	//These constant values are based on a sample set of problems.
	public static final double OPERATORS_MEAN= 3.787037037;
	public static final double OPERANDS_MEAN = 3.685185185;
	public static final double WIDTH_MEAN = 2.851851852;
	public static final double HEIGHT_MEAN = 4.027777778;
	
	//Standard deviation
	public static final double OPERATORS_STDEV = 1.600531021;
	public static final double OPERANDS_STDEV = 1.638600658;
	public static final double WIDTH_STDEV = 1.074903298;
	public static final double HEIGHT_STDEV = 1.063043887;
	
	
	//temporary fix
	public static double getFitness(LimitExpression individual)
	{
		double fitness = 0;
		TreeAnalysis analyzer = new TreeAnalysis(individual.getFunction());
		
		//NUMBER OF OPERATORS
		int operators = analyzer.countOperators();
		double differenceInOperators = Math.abs(operators - OPERATORS_MEAN);
		fitness = fitness + (differenceInOperators/OPERATORS_STDEV);
		
		//NUMBER OF OPERANDS
		int operands = analyzer.countOperands();
		double differenceInOperands= Math.abs(operands- OPERANDS_MEAN);
		fitness = fitness + (differenceInOperands/OPERANDS_STDEV);
		
		//WIDTH
		int width = analyzer.getWidth();
		double differenceInWidth= Math.abs(width- WIDTH_MEAN);
		fitness = fitness + (differenceInWidth/WIDTH_STDEV);
		
		//HEIGHT
		int height = analyzer.getHeight();
		double differenceInHeight= Math.abs(height- HEIGHT_MEAN);
		fitness = fitness + (differenceInHeight/HEIGHT_STDEV);
		
		
		return fitness;
	}
}
