package symbolicSets;

import hierarchy.*;

public class FunctionMapping 
{
	private Domain _domain;
	private Range _range;
	private LimitExpression _limExp;
	
	public FunctionMapping()
	{
		_limExp = new LimitExpression();
		_limExp.generateLimitExpression();
		_domain = null;
		_range = null;
	}
	
	public FunctionMapping(LimitExpression limExp)
	{
		_limExp = limExp;
		System.out.println("do Domain and Range First");
		_domain = new Domain(limExp);
		
	}
	
}
