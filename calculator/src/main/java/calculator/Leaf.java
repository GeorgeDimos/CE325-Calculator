package calculator;

class Leaf implements TreeNode {
	private final String val;

	public Leaf(String val) {
		this.val = val;
	}

	@Override
	public double calculate() {
		return Double.parseDouble(val);
	}

	@Override
	public String toDotString() {
		return this.hashCode() + " [label=\"" + val + "\", shape=circle, color=black]\n";
	}

	@Override
	public String toString() {
		return val;
	}
}
