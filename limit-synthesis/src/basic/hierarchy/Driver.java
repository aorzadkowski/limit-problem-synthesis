package hierarchy;

import java.util.*;

public class Driver
{
    public static void main(String[] args)
    {
//************************************TEST0        
        Variable x1 = new Variable("x1");
        Variable x2 = new Variable("x2");
        Map<Variable,Double> variableMap = new HashMap<Variable,Double>();
        variableMap.put(x1, 1.0);
        variableMap.put(x2, 3.0);
        System.out.println(x1.unParse());
        System.out.println(x1.getName());
        System.out.println("x1 = " + x1.evaluate(variableMap));
        System.out.println(x2.unParse());
        System.out.println("x2 = " + x2.evaluate(variableMap));
        
//        //--------Unary
        UnaryAbsVal u1 = new UnaryAbsVal(x1);
        System.out.println(u1.unParse());
        System.out.println("u1 = " + u1.evaluate(variableMap));
        
        UnaryCos u2 = new UnaryCos(x1);
        System.out.println(u2.unParse());
        System.out.println("u2 = " + u2.evaluate(variableMap));
        
        UnaryMinus u3 = new UnaryMinus(x1);
        System.out.println(u3.unParse());
        System.out.println("u3 = " + u3.evaluate(variableMap));
        
        UnaryPosIntPowers u4 = new UnaryPosIntPowers(x1,3);
        System.out.println(u4.unParse());
        System.out.println("u4 = " + u4.evaluate(variableMap));
        
        UnarySin u5 = new UnarySin(x1);
        System.out.println(u5.unParse());
        System.out.println("u5 = " + u5.evaluate(variableMap));
        
        UnarySquareRoot u6 = new UnarySquareRoot(x1);
        System.out.println(u6.unParse());
        System.out.println("u6 = " + u6.evaluate(variableMap));
        
        UnarySquared u7 = new UnarySquared(x1);
        System.out.println(u7.unParse());
        System.out.println("u7 = " + u7.evaluate(variableMap));
        
        UnaryTan u8 = new UnaryTan(x1);
        System.out.println(u8.unParse());
        System.out.println("u8 = " + u8.evaluate(variableMap));
        
        UnaryReciprocal u9 = new UnaryReciprocal(x1);
        System.out.println(u9.unParse());
        System.out.println("u9 = " + u9.evaluate(variableMap));
        
        //---------Binary
        
        BinaryDivideBy b1 = new BinaryDivideBy(x1,x2);
        System.out.println(b1.unParse());
        System.out.println("b1 = " + b1.evaluate(variableMap));
        
        BinaryMinus b2 = new BinaryMinus(x1,x2);
        System.out.println(b2.unParse());
        System.out.println("b2 = " + b2.evaluate(variableMap));
        
        BinaryMult b3 = new BinaryMult(x1,x2);
        System.out.println(b3.unParse());
        System.out.println("b3 = " + b3.evaluate(variableMap));
        
        BinaryPlus b4 = new BinaryPlus(x1,x2);
        System.out.println(b4.unParse());
        System.out.println("b4 = " + b4.evaluate(variableMap));
        
        BinaryExponent b5 = new BinaryExponent(x1,x2);
        System.out.println(b5.unParse());
        System.out.println("b5 = " + b5.evaluate(variableMap));
      
        
//************************************TEST1         
        //sin^2(t) + cos^2(t)
//        Variable t = new Variable(1);
//        
//        UnarySin b1 = new UnarySin(t);
//        UnaryCos b2 = new UnaryCos(t);
//        
//        UnarySquared c1 = new UnarySquared(b1);
//        UnarySquared c2 = new UnarySquared(b2);
//        
//        BinaryPlus d1 = new BinaryPlus(c1,c2);
//        
//        Map<Variable,Double> variableMap = new HashMap<Variable,Double>();
//        variableMap.put(t, Math.PI);
//        variableMap.put(t, 0.0);
//        variableMap.put(t, 0.1);
//        variableMap.put(t, 9999.0);
//                
//        System.out.println(d1.evaluate(variableMap));
//************************************TEST2       
        //sin^2(t1) + cos^2(t2)
//        Variable t1 = new Variable("t1");
//        Variable t2 = new Variable("t2");
//        
//        UnarySin b1 = new UnarySin(t1);
//        UnaryCos b2 = new UnaryCos(t2);
//        
//        UnarySquared c1 = new UnarySquared(b1);
//        UnarySquared c2 = new UnarySquared(b2);
//        
//        BinaryPlus d1 = new BinaryPlus(c1,c2);
//        
//        Map<Variable,Double> variableMap = new HashMap<Variable,Double>();
//        variableMap.put(t1, Math.PI);
//        variableMap.put(t2, 0.0);
//                
//        System.out.println(d1.evaluate(variableMap));
//        System.out.println(d1.toString());
//****************************TEST3
        
        
        
    }
}
