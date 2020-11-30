package calculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author George
 */
public class NodeTree {
	private final nodeT root;

	private static final List<Character> OPERATORS = List.of('+','-','/','*','^');

	public NodeTree(String expr) {
		root = createTree(expr.replaceAll("\\s", ""));
	}

	private nodeT createTree(String expr) {

		expr = trimParen(expr);
		String currentVal;
		nodeT left, right;

		int operatorIndex = getNextOperatorIndex(expr);
		if (operatorIndex != -1) {
			currentVal = String.valueOf(expr.charAt(operatorIndex));
			left = createTree(operatorIndex == 0 ? "0" : expr.substring(0, operatorIndex));
			right = createTree(expr.substring(operatorIndex + 1, expr.length()));
		} else {
			currentVal = expr;
			left = null;
			right = null;
		}
		
		return new nodeT(currentVal, left, right);
	}

	/**
	 * Removes parenthesis from expression recursively
	 * @param expr
	 * @return
	 */
	private String trimParen(String expr) {
		if (expr.length() < 2) {
			return expr;
		}

		if (expr.charAt(0) != '(' || expr.charAt(expr.length() - 1) != ')') {
			return expr;
		}

		if (!canRemoveParenthesis(expr)) {
			return expr;
		}

		return trimParen(expr.substring(1, expr.length() - 1));

	}

	/**
	 * Checks if removing parenthesis leaves open parenthesis 
	 * in rest of the expression
	 * @param expr
	 * @return 
	 */
	private boolean canRemoveParenthesis(String expr) {

		int open = 0;
		for (int i = 1; i < expr.length() - 1; i++) {
			if (expr.charAt(i) == '(') {
				open++;
			} else if (expr.charAt(i) == ')' && open > 0) {
				open--;
			}
		}
		return open == 0;
	}

	/**
	 * Maps all operators outside of parenthesis and 
	 * returns the index of one with the lowest priority or 
	 * -1 if expression is a number.
	 * Addition has lower priority than Subtraction
	 * in order for negative numbers (they are 
	 * calculated as {0-number}) to work .
	 * @param expr
	 * @return index of operator
	 */
	private int getNextOperatorIndex(String expr) {
		Map<Character, Integer> operatorsMap = new HashMap<>();

		int open = 0;
		
		for (int i = 0; i < expr.length(); i++) {

			if (open == 0 && OPERATORS.contains(expr.charAt(i))) {
				operatorsMap.put(expr.charAt(i), i);
			}

			if (expr.charAt(i) == '(') {
				open++;
			}

			if (expr.charAt(i) == ')') {
				open--;
			}
			
		}

		int pos = -1;
		for(Character op: OPERATORS ){
			if(operatorsMap.containsKey(op)){
				pos = operatorsMap.get(op);
				break;
			}
		}

		return pos;
	}

	/**
	 * Calculate the expression starting from the root
	 * @return
	 */
	public double calculate() {
		return calculate(root);
	}

	/**
	 * Parses tree and return a double if the node is a leaf
	 * of the computation of {(left node) op (right node)}
	 * @param node
	 * @return
	 */
	private double calculate(nodeT node) {
		if (node.getLeft() == null || node.getRight() == null) {
			return Double.parseDouble(node.getVal());
		}

		double left = calculate(node.getLeft());
		double right = calculate(node.getRight());

		switch (node.getVal()) {
			case "+":
				return left + right;
			case "-":
				return left - right;
			case "*":
				return left * right;
			case "/":
				return left / right;
			case "^":
				return Math.pow(left, right);
			default:
				return 0;
		}
	}

	@Override
	public String toString() {
		return toString(root);
	}

	private String toString(nodeT cur) {
		StringBuilder str = new StringBuilder();
		if (cur != null) {

			if (cur.getLeft() != null) {
				str.append("(");
				str.append(toString(cur.getLeft()));
			}

			str.append(cur.getVal());

			if (cur.getRight() != null) {
				str.append(toString(cur.getRight()));
				str.append(")");
			}

		}
		return str.toString();
	}

	/**
	 * Creates the content of the .dot file starting from the root Node
	 * @return
	 */
	public String toDotString() {
		StringBuilder str = new StringBuilder();
		str.append("digraph ArithmeticExpressionTree {\n" + "label=\"Arithmetic Expression\"\n");
		str.append(toDotString(root));
		str.append("}");
		return str.toString();
	}

	private String toDotString(nodeT node) {
		StringBuilder str = new StringBuilder();
		if (node != null) {
			str.append(node.hashCode());
			str.append(" [label=\"");
			str.append(node.getVal());
			str.append("\", shape=circle, color=black]\n");

			if (node.getLeft() != null) {
				str.append(node.hashCode()).append("->").append(node.getLeft().hashCode()).append("\n");
				str.append(toDotString(node.getLeft()));
			}
			if (node.getRight() != null) {
				str.append(node.hashCode()).append(" -> ").append(node.getRight().hashCode()).append("\n");
				str.append(toDotString(node.getRight()));
			}
		}
		return str.toString();
	}

	/**
	 *Private class representing one token of the expression
	 * Values can be numbers or operators
	 * @author George
	 */
	private class nodeT {
		private nodeT left, right;
		private String val;

		public nodeT(String val, nodeT left, nodeT right) {
			this.left = left;
			this.right = right;
			this.val = val;
		}

		protected String getVal() {
			return val;
		}

		protected nodeT getLeft() {
			return left;
		}
		
		protected nodeT getRight() {
			return right;
		}
	}
}
