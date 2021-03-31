package calculator;

/**
 * Private class representing one token of the expression Values can be numbers
 * or operators
 *
 * @author George
 */
class Node implements TreeNode {
	private final TreeNode left;
	private final TreeNode right;
	private final String val;

	public Node(String val, TreeNode left, TreeNode right) {
		this.left = left;
		this.right = right;
		this.val = val;
	}

	@Override
	public double calculate() {
		double left = this.left.calculate();
		double right = this.right.calculate();

		switch (val) {
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
	public String toDotString() {
		return this.hashCode() + " [label=\"" + val + "\", shape=circle, color=black]\n" +
						this.hashCode() + "->" + left.hashCode() + "\n" +
						left.toDotString() +
						this.hashCode() + "->" + right.hashCode() + "\n" +
						right.toDotString();
	}

	@Override
	public String toString() {
		return left.toString() + "(" + val + right.toString() + ")";
	}
}
