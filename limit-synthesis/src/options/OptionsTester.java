package options;

public class OptionsTester {

	public static void main(String[] args) 
	{	
		String[] testStrArry = new String[5];
		testStrArry[0]="-prohibitXBoX";
		testStrArry[1]="-prohibitXBoExp";
		testStrArry[2]="-prohibitXBoNum";
		testStrArry[3]="-allowXBoX";
		testStrArry[4]="-uniformRate=.25";
		
		Options testOptions = new Options(testStrArry);
		testOptions.parseCommandLine();
		
	}

}
