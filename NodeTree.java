package calculator;

/**
 *
 * @author George
 */
public class NodeTree {
	private final nodeT root;

	public NodeTree(String input) {
		root = createTree(trimParen(input.replaceAll("\\s", "")));
	}

	private nodeT createTree(String input) {

		input = trimParen(input);
		String currentVal;
		nodeT left, right;

		int operatorIndex = getNextOperatorIndex(input);
		if (operatorIndex != -1) {
			currentVal = String.valueOf(input.charAt(operatorIndex));
			left = createTree(operatorIndex == 0 ? "0" : input.substring(0, operatorIndex));
			right = createTree(input.substring(operatorIndex + 1, input.length()));
		} else {
			currentVal = input;
			left = null;
			right = null;
		}

		return new nodeT(currentVal, left, right);
	}

	private String trimParen(String input) {
		if (input.length() < 2) {
			return input;
		}

		if (input.charAt(0) != '(' || input.charAt(input.length() - 1) != ')') {
			return input;
		}

		if (!canRemoveParenthesis(input)) {
			return input;
		}

		return trimParen(input.substring(1, input.length() - 1));

	}

	private boolean canRemoveParenthesis(String input) {

		int open = 0;
		for (int i = 1; i < input.length() - 1; i++) {
			if (input.charAt(i) == '(') {
				open++;
			} else if (input.charAt(i) == ')' && open > 0) {
				open--;
			}
		}
		return open == 0;
	}

	private int getNextOperatorIndex(String input) {
		int open = 0;
		int[] index = new int[5];
		for (int i = 0; i < 5; i++) {
			index[i] = -1;
		}
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '(') {
				open++;
			}
			if (input.charAt(i) == ')') {
				open--;
			}
			if (open == 0) {
				switch (input.charAt(i)) {
					case '+':
						index[0] = i;
						break;
					case '-':
						index[1] = i;
						break;
					case '/':
						index[2] = i;
						break;
					case '*':
						index[3] = i;
						break;
					case '^':
						index[4] = i;
						break;
					default:
						break;
				}
			}
		}
		for (int i = 0; i < 5; i++) {
			if (index[i] != -1) {
				return index[i];
			}
		}
		return -1;
	}

	public double calculate() {
		return calculate(root);
	}

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

		protected void setVal(String val) {
			this.val = val;
		}

		protected void setLeft(nodeT left) {
			this.left = left;
		}

		protected void setRight(nodeT right) {
			this.right = right;
		}
	}
}
