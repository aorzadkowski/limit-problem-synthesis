package externalConnection;
import java.util.ArrayList;
import java.util.Vector;
import options.Options;
import hierarchy.*;

//import com.wolfram.alpha.WAEngine;
//import com.wolfram.alpha.WAException;
//import com.wolfram.alpha.WAPlainText;
//import com.wolfram.alpha.WAPod;
//import com.wolfram.alpha.WAQuery;
//import com.wolfram.alpha.WAQueryResult;
//import com.wolfram.alpha.WASubpod;

import com.wolfram.jlink.*;


//import FrontEnd.Options;
//import Globals.Constants;
//import Representation.BoundedFunction;
//import Representation.Point;
//import Utilities.Utilities;

//
// Implements a singleton pattern
//
public class LocalMathematicaCasInterface extends CasInterface
{
	
	//
    // No public instances allowed
    //
    private LocalMathematicaCasInterface(String path)
    {
        //
        // Open the mathematica link.
        //
        String[] args = {"-linkmode",  "launch",  "-linkname",path }; // "-linkmode",  "launch",  "-linkname",
        //args = {"-linkmode", "launch", "-linkname","c:\\program files\\wolfram research\\mathematica\\10.4\\mathkernel.exe"};

        try
        {
            _mathematicaLink = MathLinkFactory.createKernelLink(args);
            _mathematicaLink.discardAnswer();
            
        }
        catch (MathLinkException e)
        {
            System.err.println("Mathlink");
            e.printStackTrace();
        }
    }

    //
    // The main object-based link to local software
    //
    private KernelLink _mathematicaLink;

    // Singleton instance
    private static LocalMathematicaCasInterface _theInstance;
    
    //
    // We must initialize our CAS interface with a path
    //
    // This method MUST be called if there is a path specified in the command-line.
    //
    public static void initialize(String path)
    {
        _theInstance = new LocalMathematicaCasInterface(path);
    }

    public static LocalMathematicaCasInterface getInstance()
    {
        if (_theInstance != null) return _theInstance;
        
        _theInstance = new LocalMathematicaCasInterface(Options.MATHEMATICA_PATH);
        
        return _theInstance;
    }
    
    public String getFunctionDomain(LimitExpression limExp)
    {
    	try
      {
          //_mathematicaLink.discardAnswer();
          String funcStr = limExp.getWolframFunctionDomainString();
          
          String result = _mathematicaLink.evaluateToOutputForm(funcStr, 0);
          return result;
          
      }
      catch (Exception e)
      {
          System.out.println("Mathlink");
          e.printStackTrace();
      }
    	
    	return "";
    }
       
    //
    // Returns a String-based representation of the two given functions
    //
//    public String getIntersection(BoundedFunction func1, BoundedFunction func2)
//    {
//        try
//        {
//            _mathematicaLink.discardAnswer();
//            String func1Str = func1.toMathematicaString();
//            String func2Str = func2.toMathematicaString();
//
//            String result = _mathematicaLink.evaluateToOutputForm("NSolve[" + func1Str + "==" + func2Str + ",x]", 0);
//            return result;
//            
//        }
//        catch (MathLinkException e)
//        {
//            System.out.println("Mathlink");
//            e.printStackTrace();
//        }
//        return "Invalid Points";
//    }

    //
    // Can we establish the CAS connection with installed Mathematica?
    //
    public boolean connection()
    {
        try
        {
            //_mathematicaLink.waitForAnswer();
            String result = _mathematicaLink.evaluateToOutputForm("2 * 3", 0);
            String expected = "6";
            
            if (!result.equalsIgnoreCase(expected))
            {
                System.err.println("In simple query, expected result of " + expected + "; acquired |" + result + "|");
                return false;
            }
        }
        catch (Exception e)
        {
            System.err.println("Mathlink: ");
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    
//
//  Example usage of Mathematica
//
//	try {
//			KernelLink ml = MathLinkFactory.createKernelLink(argv1);
//			ml.discardAnswer();
//
//            //ml.evaluate("<<MyPackage.m");
//            //ml.discardAnswer();
//
//            //ml.evaluate("solve x^2==x^3");
//            //ml.waitForAnswer();
//
//            //Expr e1 = new Expr(new Expr(Expr.SYMBOL, "Plus"), new Expr[] {new Expr(2), new Expr(new Expr(Expr.SYMBOL, "Plus"), new Expr[]{new Expr(2), new Expr(2)})});
//            //System.out.println(e1.toString());
//            //Expr e1 = new Expr("NSolve[x == x^2, x]");//"Plus[2, Plus[2, 2]]");
//            //String result = ml.getString();
//            //ml.evaluate(e1);
//            //ml.waitForAnswer();
//            String str = ml.evaluateToOutputForm("NSolve[x==-x^2+4,x]", 0);
//            System.out.println("Intersects of f(x) = x and g(x) = -x^2+4 : " + str);


	
//	public String getLagrangePolynomial(Point[] points)
//	{
//		String[] argv1 = {
//				//"-linkmode",  "launch",  "-linkname",
//		"C:\\Program Files\\Wolfram Research\\Mathematica\\10.2\\MathKernel.exe" };
//		try {
//			KernelLink ml = MathLinkFactory.createKernelLink(argv1);
//			ml.discardAnswer();
//			
//			String str = "InterpolatingPolynomial[{";
//			for (Point p : points)
//			{
//				str += "{" + p.getX() + "," + p.getY() + "},";
//			}
//			str = str.substring(0,str.length()-1); //Remove last character because of an extra comma.
//			str += "}, x]";
//            return str;
//		} catch (MathLinkException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Mathlink");
//			e.printStackTrace();
//		}
//		return "Invalid Points for Lagrange Polynomial";
//	}
//	
//	public BoundedFunction parseLagrangePolynomial(String str) {
//		return null;
//	}

}