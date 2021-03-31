package calculator;

/**
 *
 * @author George
 */
public class Calculator {
	/**
	 * @param args the command line arguments
	 */
	private final ExpressionTree tree;

	public Calculator(String expr) throws IllegalArgumentException {
		if (!checkValidInput(expr)) {
			throw new IllegalArgumentException();
		}
		tree = new ExpressionTree(expr);
	}

	/**
	 * Returns the expression as it was parsed into the tree
	 */
	@Override
	public String toString() {
		return tree.toString();
	}

	/**
	 * Returns a String for a dot file
	 * 
	 * @return
	 */
	public String toDotString() {
		return tree.toDotString();
	}

	/**
	 * Returns the calculated value of the expression
	 * 
	 * @return double
	 */
	public double calculate() {
		return tree.calculate();
	}

	/**
	 * Checks if the expression consists only of numbers, operators and parenthesis.
	 * Also calls on validParen() in order to check if all parenthesis are closed
	 * properly.
	 * 
	 * @param expr
	 * @return
	 */
	private boolean checkValidInput(String expr) {
		final String noWhitespaceInput = expr.replaceAll("\\s", "");

		if (!validChars(noWhitespaceInput)) {
			return false;
		}

		return validParen(noWhitespaceInput);

	}

	/**
	 * Checks if expression consists only of valid characters
	 * 
	 * @param str
	 * @return
	 */
	private boolean validChars(String str) {
		String op = "[+\\-*/^]";
		String num = "\\d+(.\\d+)?";
		String openParen = "\\(";
		String closeParen = "\\)|";
		String sign = "[+\\-]?";

		String myRegex = "((" + sign + openParen + ")*" + sign + num + "(" + closeParen + "(" + op + num + "))*(" + op
				+ openParen + ")?)+";

		return str.matches(myRegex);
	}

	/**
	 * Checks if all parenthesis are closed
	 * 
	 * @param str
	 * @return
	 */
	private boolean validParen(String str) {
		int open = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') {
				open++;
			}
			if (str.charAt(i) == ')') {
				if (open == 0) {
					return false;
				}
				open--;
			}
		}
		return open == 0;
	}

}
