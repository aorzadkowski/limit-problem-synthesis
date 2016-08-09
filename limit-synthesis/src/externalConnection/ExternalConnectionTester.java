package externalConnection;

public class ExternalConnectionTester 
{
	public static void main(String[] args)
	{
		System.out.println(LocalMathematicaCasInterface.getInstance().connection());
		System.out.println("Let's try it again.");
		System.out.println(LocalMathematicaCasInterface.getInstance().connection());
		
	}

}
