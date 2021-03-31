package calculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author George
 */
public class ExpressionTree {
	private final TreeNode root;

	private static final List<Character> OPERATORS = List.of('+', '-', '/', '*', '^');

	public ExpressionTree(String expr) {
		root = createTree(expr.replaceAll("\\s", ""));
	}

	private TreeNode createTree(String expr) {

		expr = trimParen(expr);
		String currentVal;
		TreeNode left, right;

		int operatorIndex = getNextOperatorIndex(expr);
		if (operatorIndex != -1) {
			currentVal = String.valueOf(expr.charAt(operatorIndex));
			left = createTree(operatorIndex == 0 ? "0" : expr.substring(0, operatorIndex));
			right = createTree(expr.substring(operatorIndex + 1));
			return new Node(currentVal, left, right);
		} else {
			currentVal = expr;
			return new Leaf(currentVal);
		}

	}

	/**
	 * Removes parenthesis from expression recursively
	 *
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
	 * Checks if removing parenthesis leaves open parenthesis in rest of the
	 * expression
	 *
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
	 * Maps all operators outside of parenthesis and returns the index of one with
	 * the lowest priority or -1 if expression is a number. Addition has lower
	 * priority than Subtraction in order for negative numbers (they are calculated
	 * as {0-number}) to work .
	 *
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
		for (Character op : OPERATORS) {
			if (operatorsMap.containsKey(op)) {
				pos = operatorsMap.get(op);
				break;
			}
		}

		return pos;
	}

	/**
	 * Calculate the expression starting from the root
	 *
	 * @return
	 */
	public double calculate() {
		return root.calculate();
	}

	@Override
	public String toString() {
		return root.toString();
	}

	/**
	 * Creates the content of the .dot file starting from the root Node
	 *
	 * @return
	 */
	public String toDotString() {
		return "digraph ArithmeticExpressionTree {\n" + "label=\"Arithmetic Expression\"\n" + root.toDotString() + "}";
	}

}
