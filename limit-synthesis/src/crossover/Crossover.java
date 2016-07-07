package crossover;

import java.util.ArrayList;

import hierarchy.BinaryOperator;
import hierarchy.Expression;
import hierarchy.UnaryOperator;

public class Crossover {
	private static boolean AVL_BALANCED = false;
	private static boolean EQUAL_SUBTREE_REPLACEMENT = false;
	private static boolean REPLACE_LEAVES = true;
	
	public static Expression crossover(Expression a, Expression b) {
		//Expression nodeToSwitch = randomNode(a);
		
		Expression chosenCrossoverNode;
		Expression crossoverSubtreeNode = getRandomNode(b);
		
		if (REPLACE_LEAVES) {
			chosenCrossoverNode = getRandomLeaf(a);
		} else {
			chosenCrossoverNode = getRandomNode(a);
		}
		
		Expression parentNode = chosenCrossoverNode.getPreviousOperator();
		
		if (parentNode instanceof BinaryOperator) {
			if (((BinaryOperator)parentNode).getExp1().equals(chosenCrossoverNode))
				((BinaryOperator)parentNode).setExp1(crossoverSubtreeNode);
			else
				((BinaryOperator)parentNode).setExp2(crossoverSubtreeNode);
		} else {
			((UnaryOperator)parentNode).setExp(crossoverSubtreeNode);
		}
		
		return a;
	}
	
	private static Expression getRandomLeaf(Expression exp) {
		ArrayList<Expression> leaves = getAllLeaves(exp);
		
		return leaves.get((int)(Math.random() * leaves.size()));
	}
	
	private static ArrayList<Expression> getAllLeaves(Expression exp) {
		ArrayList<Expression> leaves = new ArrayList<>();
		
		if (exp instanceof BinaryOperator && ((BinaryOperator)exp).getExp1() == null && ((BinaryOperator)exp).getExp2() == null) {
			leaves.add(exp);
		} else if (exp instanceof UnaryOperator && ((UnaryOperator)exp).getExp() == null) {
			leaves.add(exp);
		} else if (exp instanceof BinaryOperator) {
			ArrayList<Expression> left = new ArrayList<>();
			ArrayList<Expression> right = new ArrayList<>();
			if (((BinaryOperator)exp).getExp1() != null) {
				left = getAllLeaves(((BinaryOperator)exp).getExp1());
			}
			if (((BinaryOperator)exp).getExp2() != null) {
				right = getAllLeaves(((BinaryOperator)exp).getExp2());
			}
			leaves.addAll(left);
			leaves.addAll(right);
		} else {
			if (((UnaryOperator)exp).getExp() != null) {
				leaves = getAllLeaves(((UnaryOperator)exp).getExp());
			}
		}
		return leaves;
	}
	
	private static Expression getRandomNode(Expression exp) {
		ArrayList<Expression> list = new ArrayList<>();
		
		list = getList(exp, list);
		
		return list.get((int) (Math.random() * list.size()));
	}
	
	private static ArrayList<Expression> getList(Expression node, ArrayList<Expression> list) {
		list.add(node);
		
		if (node instanceof UnaryOperator) {
			if (((UnaryOperator)node).getExp() != null) {
				return getList(((UnaryOperator)node).getExp(), list);
			} else
				return list;
		} else {
			ArrayList<Expression> list1 = new ArrayList<>();
			ArrayList<Expression> list2 = new ArrayList<>();
			if (((BinaryOperator)node).getExp1() != null) {
				list1 = getList(((BinaryOperator)node).getExp1(), list1);
			}
			if (((BinaryOperator)node).getExp2() != null) {
				list2 = getList(((BinaryOperator)node).getExp2(), list2);
			}
			list1.addAll(list2);
			return list1;
		}
	}
	
	private static int sizeOf(Expression exp) {
		if (exp instanceof UnaryOperator) {
			int size = 0;
			if (((UnaryOperator)exp).getExp() != null) {
				size = 1 + sizeOf(((UnaryOperator)exp).getExp());
			}
			return 1 + size;
		} else {
			int leftSize = 0; 
			int rightSize = 0;
			if (((BinaryOperator)exp).getExp1() != null){
				leftSize = sizeOf(((BinaryOperator)exp).getExp1());
			}
			if (((BinaryOperator)exp).getExp2() != null) {
				rightSize = sizeOf(((BinaryOperator)exp).getExp2());
			}
			return 1 + leftSize + rightSize;
		}
	}
}
