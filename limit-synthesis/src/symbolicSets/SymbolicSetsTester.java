package symbolicSets;

public class SymbolicSetsTester {

	public static void main(String[] args)
	{
		Domain tester = new Domain();
		
		tester.add(new IntervalOpen(-1,2));
		tester.add(new IntervalLeftHalfOpen(2,4));
		tester.add(new IntervalOpen(10,200));
		tester.add(new IntervalOpen(4,10));
		tester.add(new IntervalRightHalfOpen(400, Double.POSITIVE_INFINITY));
		tester.add(new IntervalLeftHalfOpen(200,400));
		tester.add(new IntervalOpen(Double.NEGATIVE_INFINITY,-1));
		
		System.out.println(tester.toString());
		
	}

}
