package options;

import java.util.Vector;

//
// A aggregation class for options specified in the command-line
//
public class Options
{
	private String[] args;
	private Vector<String> templateFiles;
    public Vector<String> getFiles() { return templateFiles; }

    
    
    //Genetic constants
    public static double UNIFORM_RATE = 0.5;//crossoverRate
    public static int TOURNAMENT_SIZE = 5;
    public static boolean ELITISM = true;
    
 
  	public static double MUTATION_RATE = 0.1;//should be .015
  	public static double EXPANSION_RATE = 0.005; //under normal parameters, how likely is a gene to expand?
  													//should be .2
  	public static double REGRESSION_RATE = .66;//under normal parameters, how likely is a gene to regress?
  													//should be .66
  	public static double SUBSTITUTION_RATE = 1.0;//............................................. be substituted?
  													//1.0
  	public static int MAX_SIZE = 20;
  	public static int MIN_SIZE = 5;
  	public static int MAX_REGRESSION = 3; //determines how much information can be regressed at a time.
  	
  	//simplification cases
  	public static boolean PROHIBIT_NUM_BO_NUM = true; //should be true. Makes the following an invalid case: two Numbers
  																//combined by a BinaryOperator
  																//The cases of e^num, num*#Pi,
  																//#Pi*num, and num/num are not included.
  	public static boolean PROHIBIT_NUM_BO_X = false;//Marks Expressions like
  																// num^x, num*x,...  as invalid. 
  	public static boolean PROHIBIT_NUM_BO_EXP = false;//Marks Expressions like
  																// 2-(1/2), 3/cbrt(x), 4*absval(-9) as invalid.
  																//num^exp is NOT included.
  	public static boolean PROHIBIT_X_BO_NUM = false;//Marks Expressions like
  																//x^2, x*2, x-2... as invalid
  	public static boolean PROHIBIT_X_BO_X = true;//should be true. Marks Expressions like
  																//x^x, x*x, x-x,... as invalid.
  	public static boolean PROHIBIT_X_BO_EXP = false;//Marks Expressions like
  																//x-cbrt(x), x*absval(-9),... as invalid.
  																//x^exp is NOT included
  	public static boolean PROHIBIT_EXP_BO_NUM = false;//Marks Expressions like
  																//(cbrt(x)^2, absval(-9)*2 as invalid
  	public static boolean PROHIBIT_EXP_BO_X= false;//Marks Expressions like
  																//cbrt(x)*x, absval(-9)*x as invalid
  																//exp^x is NOT included.
  	public static boolean PROHIBIT_EXP_BO_EXP= false;//Should be true. Marks Expressions like
  																//cbrt(x)*(absval(-9), sin(x)/(Pi/2) to occur
  																//exp^exp is NOT included
  	public static boolean PROHIBIT_UO_NUM = true;//should be true. Marks Expressions like
  																//absval(2) as invalid
  																//reciprocal(num) not included.
  	public static boolean PROHIBIT_UO_X = false;//Marks Expressions like
  																//absval(x) as invalid
  	public static boolean PROHIBIT_UO_EXP= false;//Marks Expressions like
  																//absval(3*x) as invalid
  	
  	//Special cases
  	public static boolean PROHIBIT_E_TO_THE_NUM = false;//e^num
  	
  	public static boolean PROHIBIT_NUM_TIMES_PI= false;//num*pi
  	
  	public static boolean PROHIBIT_PI_TIMES_NUM= true;//pi*num
  	
  	public static boolean PROHIBIT_NUM_OVER_NUM= true;//num/num (num/pi) (num/e) (e/num)...
  	
  	public static boolean PROHIBIT_NUM_TO_THE_EXP= true;//num^exp ... Does not include num^(num/num)
  	
  	public static boolean PROHIBIT_X_TO_THE_EXP= true;//x^exp ... x^(num/num) is always allowed
  	
  	public static boolean PROHIBIT_EXP_TO_THE_X= true;//exp^x
  	
  	public static boolean PROHIBIT_EXP_TO_THE_EXP= true;//exp^exp... exp^(num/num) is always allowed
  	
  	public static boolean PROHIBIT_RECIPROCAL_NUM= true;//reciprocal(num)
  	
  	public static String MATHEMATICA_PATH = "C:\\Program Files\\Wolfram Research\\Mathematica\\10.4\\MathKernel.exe";
    
  	
  	
    public Options(String[] args)
    {
    	this.args = args;
    	this.templateFiles = new Vector<String>();
    }

    public boolean parseCommandLine()
    {
        for (int i = 0; i < args.length; i++)
        {
            if (args[i].charAt(0) == '-')
            {
                if (!handleOption(i)) return false;
            }
            else if (args[i].endsWith("template-specs.txt"))
            {
                templateFiles.add(args[i]);
            } else if (args[i].endsWith("options.txt")) {
            	//OptionsFileParser optionsFileParser = new OptionsFileParser(args[i]);
            	//optionsFileParser.parseFile();
            }
        }

        return true;
    }

    //
    // Deal with the actual options specified on the command-line.
    //
    private boolean handleOption(int index)
    {
    	//Booleans
        
    	if (args[index].equalsIgnoreCase("-ELITISM="))
        {
            Options.ELITISM = Boolean.parseBoolean(args[index].substring(args[index].indexOf('=')+1, args[index].length()));
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_NUM_BO_NUM"))
        {
            Options.PROHIBIT_NUM_BO_NUM = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_NUM_BO_X"))
        {
            Options.PROHIBIT_NUM_BO_X = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_NUM_BO_EXP"))
        {
            Options.PROHIBIT_NUM_BO_EXP = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_X_BO_NUM"))
        {
            Options.PROHIBIT_X_BO_NUM = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_X_BO_X"))
        {
            Options.PROHIBIT_X_BO_X = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_X_BO_EXP"))
        {
            Options.PROHIBIT_X_BO_EXP = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_EXP_BO_NUM"))
        {
            Options.PROHIBIT_EXP_BO_NUM = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_EXP_BO_X"))
        {
            Options.PROHIBIT_EXP_BO_X = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_EXP_BO_EXP"))
        {
            Options.PROHIBIT_EXP_BO_EXP = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_UO_NUM"))
        {
            Options.PROHIBIT_UO_NUM = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_UO_X"))
        {
            Options.PROHIBIT_UO_X = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_UO_EXP"))
        {
            Options.PROHIBIT_UO_EXP = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_E_TO_THE_NUM"))
        {
            Options.PROHIBIT_E_TO_THE_NUM = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_NUM_TIMES_PI"))
        {
            Options.PROHIBIT_NUM_TIMES_PI = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_PI_TIMES_NUM"))
        {
            Options.PROHIBIT_PI_TIMES_NUM = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_NUM_OVER_NUM"))
        {
            Options.PROHIBIT_NUM_OVER_NUM = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_NUM_TO_THE_EXP"))
        {
            Options.PROHIBIT_NUM_TO_THE_EXP = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_X_TO_THE_EXP"))
        {
            Options.PROHIBIT_X_TO_THE_EXP = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_EXP_TO_THE_X"))
        {
            Options.PROHIBIT_EXP_TO_THE_X = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_EXP_TO_THE_EXP"))
        {
            Options.PROHIBIT_EXP_TO_THE_EXP = true;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-PROHIBIT_RECIPROCAL_NUM"))
        {
            Options.PROHIBIT_RECIPROCAL_NUM = true;
            return true;
        }
    	
    	
    	
        else if (args[index].equalsIgnoreCase("-ALLOW_NUM_BO_NUM"))
        {
            Options.PROHIBIT_NUM_BO_NUM = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_NUM_BO_X"))
        {
            Options.PROHIBIT_NUM_BO_X = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_NUM_BO_EXP"))
        {
            Options.PROHIBIT_NUM_BO_EXP = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_X_BO_NUM"))
        {
            Options.PROHIBIT_X_BO_NUM = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_X_BO_X"))
        {
            Options.PROHIBIT_X_BO_X = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_X_BO_EXP"))
        {
            Options.PROHIBIT_X_BO_EXP = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_EXP_BO_NUM"))
        {
            Options.PROHIBIT_EXP_BO_NUM = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_EXP_BO_X"))
        {
            Options.PROHIBIT_EXP_BO_X = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_EXP_BO_EXP"))
        {
            Options.PROHIBIT_EXP_BO_EXP = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_UO_NUM"))
        {
            Options.PROHIBIT_UO_NUM = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_UO_X"))
        {
            Options.PROHIBIT_UO_X = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_UO_EXP"))
        {
            Options.PROHIBIT_UO_EXP = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_E_TO_THE_NUM"))
        {
            Options.PROHIBIT_E_TO_THE_NUM = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_NUM_TIMES_PI"))
        {
            Options.PROHIBIT_NUM_TIMES_PI = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_PI_TIMES_NUM"))
        {
            Options.PROHIBIT_PI_TIMES_NUM = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_NUM_OVER_NUM"))
        {
            Options.PROHIBIT_NUM_OVER_NUM = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_NUM_TO_THE_EXP"))
        {
            Options.PROHIBIT_NUM_TO_THE_EXP = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_X_TO_THE_EXP"))
        {
            Options.PROHIBIT_X_TO_THE_EXP = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_EXP_TO_THE_X"))
        {
            Options.PROHIBIT_EXP_TO_THE_X = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_EXP_TO_THE_X"))
        {
            Options.PROHIBIT_EXP_TO_THE_EXP = false;
            return true;
        }
        else if (args[index].equalsIgnoreCase("-ALLOW_RECIPROCAL_NUM"))
        {
            Options.PROHIBIT_RECIPROCAL_NUM = false;
            return true;
        }
        
        
        
        //Values
        else if (args[index].startsWith("-UNIFORM_RATE="))
        {
            Options.UNIFORM_RATE = Double.parseDouble(args[index].substring(args[index].indexOf('=')+1, args[index].length()));
            return true;
        }
        else if (args[index].startsWith("-TOURNAMENT_SIZE="))
        {
            Options.TOURNAMENT_SIZE = Integer.parseInt((args[index].substring(args[index].indexOf('=')+1, args[index].length())));
            return true;
        }
        else if (args[index].startsWith("-MUTATION_RATE="))
        {
            Options.MUTATION_RATE = Double.parseDouble(args[index].substring(args[index].indexOf('=')+1, args[index].length()));
            return true;
        }
        else if (args[index].startsWith("-EXPANSION_RATE="))
        {
            Options.EXPANSION_RATE = Double.parseDouble(args[index].substring(args[index].indexOf('=')+1, args[index].length()));
            return true;
        }
        else if (args[index].startsWith("-REGRESSION_RATE="))
        {
            Options.REGRESSION_RATE = Double.parseDouble(args[index].substring(args[index].indexOf('=')+1, args[index].length()));
            return true;
        }
        else if (args[index].startsWith("-SUBSTITUTION_RATE="))
        {
            Options.SUBSTITUTION_RATE = Double.parseDouble(args[index].substring(args[index].indexOf('=')+1, args[index].length()));
            return true;
        }
        else if (args[index].startsWith("-MAX_SIZE="))
        {
            Options.MAX_SIZE = Integer.parseInt((args[index].substring(args[index].indexOf('=')+1, args[index].length())));
            return true;
        }
        else if (args[index].startsWith("-MIN_SIZE="))
        {
            Options.MIN_SIZE = Integer.parseInt((args[index].substring(args[index].indexOf('=')+1, args[index].length())));
            return true;
        }
        else if (args[index].startsWith("-MAX_REGRESSION="))
        {
            Options.MAX_REGRESSION = Integer.parseInt((args[index].substring(args[index].indexOf('=')+1, args[index].length())));
            return true;
        }
        

        return false;
    }
}