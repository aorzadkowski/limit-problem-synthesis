package fitness;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import hierarchy.BinaryOperator;
import hierarchy.Expression;
import hierarchy.UnaryOperator;
import hierarchy.Variable;

public class TreeAnalysis {
	Expression expr;
	Map<Expression, Integer> elementMap;
	
	public TreeAnalysis(Expression expr) {
		this.expr = expr;
		elementMap = new HashMap<>();
	}
	
	public void readExpression() {
		readExpression(expr);
	}
	
	private void readExpression (Expression node) {
		if (elementMap.containsKey(node)) {
			elementMap.put(node, ((Integer) elementMap.get(node)) + 1);
		}
		else {
			elementMap.put(node, 0);
		}
		
		if (node instanceof BinaryOperator) {
			readExpression(((BinaryOperator) node).getLeftExpression());
			readExpression(((BinaryOperator) node).getRightExpression());
		} else if (node instanceof UnaryOperator) {
			readExpression(((UnaryOperator) node).getNextExpression());
		} else if (node instanceof Variable || node instanceof hierarchy.Number) {
			return; //Let's be verbose. 
		}
	}
	
	@Override
	public String toString() {
		return elementMap.toString();
	}
	
	public int getHeight() {
		return getHeight(expr);
	}
	
	private int getHeight(Expression node) {
		if (node == null) {
			return 0;
		} else if (node instanceof BinaryOperator){
			int left = getHeight(((BinaryOperator) node).getLeftExpression());
			int right = getHeight(((BinaryOperator) node).getRightExpression());
			return 1 + (left > right ? left : right);
		} else if (node instanceof UnaryOperator){
			return 1 + getHeight(((UnaryOperator) node).getNextExpression());
		} else {
			return 1;
		}
	}
	
	public int getWidth() {
		return getWidth(expr);
	}
	private int maxWidth = 0;
	
	private int getWidth(Expression node) {
		Queue q = new LinkedList<>();
		int levelNodes =0;
		if(node==null) return 0;
		q.add(node);

		while(!q.isEmpty()){
			levelNodes = q.size();
			if(levelNodes>maxWidth){
				maxWidth = levelNodes;
			}
			while(levelNodes>0){
				Expression n = (Expression)q.remove();
				if (n instanceof UnaryOperator) {
					if (((UnaryOperator)n).getNextExpression() != null) q.add(((UnaryOperator)n).getNextExpression());
				} else if (n instanceof BinaryOperator) {
					if(((BinaryOperator)n).getLeftExpression()!=null) q.add(((BinaryOperator)n).getLeftExpression());
					if(((BinaryOperator)n).getRightExpression()!=null) q.add(((BinaryOperator)n).getRightExpression());
				}
				levelNodes--;
			}
		}
		return maxWidth;
	}
	
	public int countOperators() {
		return countOperators(expr);
	}
	
	private int countOperators(Expression node) {
		if (node == null || (!(node instanceof BinaryOperator) && !(node instanceof UnaryOperator))) {
			return 0;
		} else if (node instanceof BinaryOperator) {
			return 1 + countOperators(((BinaryOperator)node).getLeftExpression()) + countOperators(((BinaryOperator)node).getRightExpression());
		} else {
			return 1 + countOperators(((UnaryOperator)node).getNextExpression());
		}
	}
	
	public int countOperands() {
		return countOperands(expr);
	}
	
	private int countOperands(Expression node) {
		if (node == null) {
			return 0;
		}
		
		if (node instanceof BinaryOperator) {
			return countOperands(((BinaryOperator)node).getLeftExpression()) + countOperands(((BinaryOperator)node).getRightExpression());
		} else if (node instanceof UnaryOperator) {
			return countOperands(((UnaryOperator)node).getNextExpression());
		} else /* if (node instanceof Variable || node instanceof Number) */ {
			return 1;
		}
	}
	
	public String toCSV() {
		return "" + countOperators() + ", " + countOperands() + ", " + getWidth() + ", " + getHeight();
	}
}
