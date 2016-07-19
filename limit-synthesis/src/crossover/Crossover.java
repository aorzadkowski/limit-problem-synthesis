package crossover;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import hierarchy.BinaryOperator;
import hierarchy.Expression;
import hierarchy.UnaryOperator;
import hierarchy.Variable;

public class Crossover {
	private static final boolean BALANCED = true;
	private static final boolean EQUAL_SUBTREE_REPLACEMENT = false;
	private static final boolean REPLACE_LEAVES = true;
	private static final int MAXIMUM_DIFFERENCE_BETWEEN_PATHS = 2;

	
	public static Expression crossover(Expression a, Expression b) {
		//Expression nodeToSwitch = randomNode(a);
		
		Expression chosenCrossoverNode;
		Expression crossoverSubtreeNode;
		
		if (REPLACE_LEAVES) {
			if (BALANCED)
				chosenCrossoverNode = getShortestPath(a);
			else 
				chosenCrossoverNode = getRandomLeaf(a);
		} else {
			chosenCrossoverNode = getRandomNode(a);
		}

		int dLength = getLongestPathLength(chosenCrossoverNode) - getShortestPathLength(chosenCrossoverNode);
		
		if (BALANCED) {
			crossoverSubtreeNode = getSubtreeOfLength(b, dLength);
		} else {
			crossoverSubtreeNode = getRandomNode(b);
		}
		
		Expression parentNode = chosenCrossoverNode.getPreviousOperator();
		
		System.out.println(crossoverSubtreeNode.unParse());
		System.out.println(chosenCrossoverNode.unParse());
		System.out.println(parentNode.unParse());
		
		if (parentNode instanceof BinaryOperator) {
			if (((BinaryOperator)parentNode).getExp1().equals(chosenCrossoverNode))
				((BinaryOperator)parentNode).setExp1(crossoverSubtreeNode);
			else
				((BinaryOperator)parentNode).setExp2(crossoverSubtreeNode);
		} else {
			((UnaryOperator)parentNode).setExp(crossoverSubtreeNode);
		}
		
		
		return getRootOf(parentNode);
	}
	
	private static Expression getRootOf(Expression e) {
		Expression next = e;
		int count = 0;
		while (next.getPreviousOperator() != null) {
			count++;
			next = next.getPreviousOperator();
		}
		return next;
	}
	
	private static Expression getSubtreeOfLength(Expression e, int desired) {
		return getSubtreeOfLength(e, desired, 0);
	}
	
	private static Expression getSubtreeOfLength(Expression e, int desired, int current) {
		if (current == desired) 
			return e;
		
		if (e instanceof UnaryOperator) {
			return getSubtreeOfLength(((UnaryOperator) e).getExp(), desired, current + 1);
		} else if (e instanceof BinaryOperator){
			Expression left = ((BinaryOperator) e).getExp1();
			Expression right = ((BinaryOperator) e).getExp2();
			
			if (left != null) left = getSubtreeOfLength(((BinaryOperator) e).getExp1(), desired, current + 1);
			if (right != null) right = getSubtreeOfLength(((BinaryOperator) e).getExp2(), desired, current + 1);
			
			if (getPathLength(left) >= getPathLength(right)) return left;
			else return right;
		} else {
			return e;
		}
	}
	
	private static int getLongestPathLength(Expression e) {
		ArrayList<Expression> leaves = getAllLeaves(e);
		Expression longestPath = leaves.get(0); 
		int longestPathInt = getPathLength(longestPath);
		for (int i = 0; i < leaves.size(); i++) {
			int path = getPathLength(leaves.get(i));
			if (path < longestPathInt) {
				longestPathInt = path;
				longestPath = leaves.get(i);
			}
		}
		return longestPathInt;
	}
	
	private static Expression getLongestPath(Expression e) {
		ArrayList<Expression> leaves = getAllLeaves(e);
		Expression longestPath = leaves.get(0); 
		int longestPathInt = getPathLength(longestPath);
		for (int i = 0; i < leaves.size(); i++) {
			int path = getPathLength(leaves.get(i));
			if (path > longestPathInt) {
				longestPathInt = path;
				longestPath = leaves.get(i);
			}
		}
		return longestPath;
	}
	
	private static int getShortestPathLength(Expression e) {
		ArrayList<Expression> leaves = getAllLeaves(e);
		Expression shortestPath = leaves.get(0); 
		int shortestPathInt = getPathLength(shortestPath);
		for (int i = 0; i < leaves.size(); i++) {
			int path = getPathLength(leaves.get(i));
			if (path < shortestPathInt) {
				shortestPathInt = path;
				shortestPath = leaves.get(i);
			}
		}
		return shortestPathInt;
	}
	
	//Give the root node of the expression.
	private static Expression getShortestPath(Expression e) {
		ArrayList<Expression> leaves = getAllLeaves(e);
		Expression shortestPath = leaves.get(0); 
		int shortestPathInt = getPathLength(shortestPath);
		for (int i = 0; i < leaves.size(); i++) {
			int path = getPathLength(leaves.get(i));
			if (path < shortestPathInt) {
				shortestPathInt = path;
				shortestPath = leaves.get(i);
			}
		}
		return shortestPath;
	}
	
	private static int getPathLength(Expression e) {
		Expression next = e;
		int count = 0;
		while (next != null) {
			count++;
			next = next.getPreviousOperator();
		}
		return count;
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
		} else if (exp instanceof UnaryOperator) {
			if (((UnaryOperator)exp).getExp() != null) {
				leaves = getAllLeaves(((UnaryOperator)exp).getExp());
			}
		} else {
			leaves.add(exp);
		}
		return leaves;
	}
	
	private static Expression getRandomNode(Expression exp) {
		ArrayList<Expression> list = new ArrayList<>();
		
		list = getList(exp, list);
		Expression chosen = list.get((int) (Math.random() * list.size()));
		System.out.println(chosen.unParse());
		return chosen;
	}
	
	private static ArrayList<Expression> getList(Expression node, ArrayList<Expression> list) {
		list.add(node);
		
		if (node instanceof UnaryOperator) {
			if (((UnaryOperator)node).getExp() != null) {
				return getList(((UnaryOperator)node).getExp(), list);
			} else
				return list;
		} else if (node instanceof BinaryOperator) {
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
		} else {
			return list;
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
	
	public static void print(Expression e) {
        print(e, "", true);
    }

    private static void print(Expression e, String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "\\-- " : "|-- ") + e.getExpressionString());
        if (e instanceof BinaryOperator) {
        	if (((BinaryOperator) e).getExp1() != null) print(((BinaryOperator) e).getExp1(),prefix + (isTail ? "    " : "|   "), false);
        	if (((BinaryOperator) e).getExp2() != null) print(((BinaryOperator) e).getExp2(),prefix + (isTail ? "    " : "|   "), false);
        } else if (e instanceof UnaryOperator) {
        	if (((UnaryOperator) e).getExp() != null) print(((UnaryOperator) e).getExp(), prefix + (isTail ? "    " : "|   "), false);
        }
    }
}
