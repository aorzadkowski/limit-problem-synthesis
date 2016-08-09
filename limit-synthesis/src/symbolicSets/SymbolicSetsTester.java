package symbolicSets;

import hierarchy.*;

public class SymbolicSetsTester {

	public static void main(String[] args)
	{
		EqualTo in1 = new EqualTo(new Variable("x"), new hierarchy.Number(4));
		
		System.out.println(in1.toString());
		System.out.println(in1.contains(4));
		System.out.println(in1.contains(0));
		System.out.println(in1.contains(4.0001));
		
		NotEqualTo in2 = new NotEqualTo(new Variable("x"), new hierarchy.Number(4));
		
		System.out.println("NotEqualTo");
		System.out.println(in2.toString());
		System.out.println(in2.contains(4));
		System.out.println(in2.contains(0));
		System.out.println(in2.contains(4.0001));
		
		GreaterThan in3 = new GreaterThan(new Variable("x"), new hierarchy.Number(4));
		
		System.out.println(in3.toString());
		System.out.println(in3.contains(4));
		System.out.println(in3.contains(0));
		System.out.println(in3.contains(4.0001));
		
		GreaterThanOrEqualTo in4 = new GreaterThanOrEqualTo(new Variable("x"), new hierarchy.Number(4));
		
		System.out.println(in4.toString());
		System.out.println(in4.contains(4));
		System.out.println(in4.contains(0));
		System.out.println(in4.contains(4.0001));
		
		LessThan in5 = new LessThan(new Variable("x"), new hierarchy.Number(4));
		
		System.out.println(in5.toString());
		System.out.println(in5.contains(4));
		System.out.println(in5.contains(0));
		System.out.println(in5.contains(4.0001));
		
		LessThanOrEqualTo in6 = new LessThanOrEqualTo(new Variable("x"), new hierarchy.Number(4));
		
		System.out.println(in6.toString());
		System.out.println(in6.contains(4));
		System.out.println(in6.contains(0));
		System.out.println(in6.contains(4.0001));
		
		LessThanLessThan in7 = new LessThanLessThan(new hierarchy.Number(0),new Variable("x"), new hierarchy.Number(4));
	
		System.out.println(in7.toString());
		System.out.println("4" + in7.contains(4));
		System.out.println("0" + in7.contains(0));
		System.out.println("4.00001" + in7.contains(4.0001));
		System.out.println("-1" + in7.contains(-1));
		System.out.println("2" + in7.contains(2));
		
		GreaterThanGreaterThan in8 = new GreaterThanGreaterThan(new hierarchy.Number(4),new Variable("x"), new hierarchy.Number(0));
		
		System.out.println(in8.toString());
		System.out.println("4" + in8.contains(4));
		System.out.println("0" + in8.contains(0));
		System.out.println("4.00001" + in8.contains(4.0001));
		System.out.println("-1" + in8.contains(-1));
		System.out.println("2" + in8.contains(2));
	
		LessThanOrEqualToLessThan in9 = new LessThanOrEqualToLessThan(new hierarchy.Number(0),new Variable("x"), new hierarchy.Number(4));
		
		System.out.println(in9.toString());
		System.out.println("4" + in9.contains(4));
		System.out.println("0" + in9.contains(0));
		System.out.println("4.00001" + in9.contains(4.0001));
		System.out.println("-1" + in9.contains(-1));
		System.out.println("2" + in9.contains(2));

		LessThanLessThanOrEqualTo in10 = new LessThanLessThanOrEqualTo(new hierarchy.Number(0),new Variable("x"), new hierarchy.Number(4));
		
		System.out.println(in10.toString());
		System.out.println("4" + in10.contains(4));
		System.out.println("0" + in10.contains(0));
		System.out.println("4.00001" + in10.contains(4.0001));
		System.out.println("-1" + in10.contains(-1));
		System.out.println("2" + in10.contains(2));

		GreaterThanGreaterThanOrEqualTo in11 = new GreaterThanGreaterThanOrEqualTo(new hierarchy.Number(4),new Variable("x"), new hierarchy.Number(0));
		
		System.out.println(in11.toString());
		System.out.println("4" + in11.contains(4));
		System.out.println("0" + in11.contains(0));
		System.out.println("4.00001" + in11.contains(4.0001));
		System.out.println("-1" + in11.contains(-1));
		System.out.println("2" + in11.contains(2));
		
		GreaterThanOrEqualToGreaterThan in12 = new GreaterThanOrEqualToGreaterThan(new hierarchy.Number(4),new Variable("x"), new hierarchy.Number(0));
		
		System.out.println(in12.toString());
		System.out.println("4" + in12.contains(4));
		System.out.println("0" + in12.contains(0));
		System.out.println("4.00001" + in12.contains(4.0001));
		System.out.println("-1" + in12.contains(-1));
		System.out.println("2" + in12.contains(2));
		
		ElementOf in13 = new ElementOf(new Variable("x"),new Reals());
		
		System.out.println(in13.toString());
		System.out.println("4" + in13.contains(4));
		System.out.println("0" + in13.contains(0));
		System.out.println("4.00001" + in13.contains(4.0001));
		System.out.println("-1" + in13.contains(-1));
		System.out.println("2" + in13.contains(2));
		
		NotElementOf in14 = new NotElementOf(new Variable("x"),new Integers());
		
		System.out.println(in14.toString());
		System.out.println("4" + in14.contains(4));
		System.out.println("0" + in14.contains(0));
		System.out.println("4.00001" + in14.contains(4.0001));
		System.out.println("-1" + in14.contains(-1));
		System.out.println("2" + in14.contains(2));
		
		System.out.println("\n---------------LogicStatements----------\n");
		
		And and1 = new And(in7, in8);
		
		System.out.println(and1.toString());
		System.out.println("4" + and1.contains(4));
		System.out.println("0" + and1.contains(0));
		System.out.println("4.00001" + and1.contains(4.0001));
		System.out.println("-1" + and1.contains(-1));
		System.out.println("2" + and1.contains(2));
		
		Or or1 = new Or(in1, in2);
		
		System.out.println(or1.toString());
		System.out.println("4" + or1.contains(4));
		System.out.println("0" + or1.contains(0));
		System.out.println("4.00001" + or1.contains(4.0001));
		System.out.println("-1" + or1.contains(-1));
		System.out.println("2" + or1.contains(2));
		
		Or or2 = new Or(and1, or1);
		
		System.out.println(or2.toString());
		System.out.println("4" + or2.contains(4));
		System.out.println("0" + or2.contains(0));
		System.out.println("4.00001" + or2.contains(4.0001));
		System.out.println("-1" + or2.contains(-1));
		System.out.println("2" + or2.contains(2));
		
		System.out.println("\n---------------DOMAIN_TEST----------\n");
		
		Domain d = new Domain(or2);
		
		System.out.println(d.toString());
		System.out.println("4" + d.isOnDomain(4));
		System.out.println("0" + d.isOnDomain(0));
		System.out.println("4.0001" + d.isOnDomain(4.0001));
		System.out.println("-1" + d.isOnDomain(-1));
		System.out.println("2" + d.isOnDomain(2));
	}

}
