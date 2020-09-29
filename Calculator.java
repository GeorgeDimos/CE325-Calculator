package calculator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author George
 */
public class Calculator {
	/**
	 * @param args the command line arguments
	 */
	private NodeTree tree;

	public Calculator(String expr) throws IllegalArgumentException {
		if (!checkValidInput(expr)) {
			throw new IllegalArgumentException();
		}
		tree = new NodeTree(expr);
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
	 * @return
	 */
	public String toDotString() {
		return tree.toDotString();
	}

	/**
	 * Returns the calculated value of the expression
	 * @return double
	 */
	public double calculate() {
		return tree.calculate();
	}

	/**
	 * Checks if the expression consists only of numbers, operators
	 * and parenthesis. Also calls on validParen() in order to
	 *  check if all parenthesis are closed properly.
	 * @param expr
	 * @return
	 */
	private boolean checkValidInput(String expr) {
		final String noWhitespaceInput = expr.replaceAll("\\s", "");
		
		if (!validChars(noWhitespaceInput)) {
			return false;
		}

		if(!validParen(noWhitespaceInput)){
			return false;
		}

		return true;
		
	}

	/**
	 * Checks if expression consists only of valid characters
	 * @param str
	 * @return
	 */
	private boolean validChars(String str){
		String op = "[\\+\\-\\*\\/\\^]";
		String num = "\\d+(.\\d+)?";
		String openParen = "\\(";
		String closeParen = "\\)";
		String sign = "[\\+\\-]?";
		String OR = "|";

		String myRegex = "((" + sign + openParen + ")*" + sign + num + "(" + closeParen + OR + "(" + op + num + "))*("
				+ op + openParen + ")?)+";

		if (!str.matches(myRegex)) {
			System.out.println("REJECTED");
			return false;
		}

		return true;
	}

	/**
	 * Checks if all parenthesis are closed
	 * @param str
	 * @return
	 */
	private boolean validParen(String str){
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

	
	/*public static void main(String[] args) { 
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter math expression or \"0\" to stop: "); 
		String expr =sc.nextLine(); 
		Calculator c; 
		while(!expr.equals("0")){ 
			try{ 
				c = new Calculator(expr); 
				System.out.println("result: " + c.calculate()); 
				try (PrintWriter pfile = new PrintWriter("ArithmeticExpression.dot")) { 
					pfile.println(c.toDotString());
					System.out.println("PRINT DOT FILE OK!"); 
				} 
				catch(FileNotFoundException ex) {
					System.err.println("Unable to write dotString!!!"); 
					System.exit(1); 
				} 
			}
		catch(IllegalArgumentException ex){
			System.out.println("Invalid input. Try again."); }
			System.out.print("Enter math expression or \"0\" to stop: "); expr =
			sc.nextLine(); 
		}
		sc.close();
	}*/
	 

}
