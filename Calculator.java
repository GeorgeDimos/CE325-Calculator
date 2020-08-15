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
    
    public Calculator (String input) throws IllegalArgumentException {
	if(!checkValidInput(input)){
	    throw new IllegalArgumentException();
	}
	tree = new NodeTree(input);
    }
    
    @Override
    public String toString(){
	return tree.toString();
    }
    
    public String toDotString(){
	return tree.toDotString();
    }
    
    public double calculate(){
	return tree.calculate();
    }
    
    private boolean checkValidInput(String input){
	final String noWhitespaceInput = input.replaceAll("\\s","");
	String op = "[\\+\\-\\*\\/\\^]";
	String num = "\\d+(.\\d+)?";
	String openParen = "\\(";
	String closeParen = "\\)";
	String sign="[\\+\\-]?";
	String OR = "|";
	
	String myRegex = "(("+sign+openParen+")*"+sign+num+"("+closeParen+OR+"("+op+num+"))*("+op+openParen+")?)+";

	if(!noWhitespaceInput.matches(myRegex)){
	    System.out.println("REJECTED");
	    return false;
	}

	int open = 0;
	for(int i=0;i<noWhitespaceInput.length();i++){
	    if(noWhitespaceInput.charAt(i)=='('){
		open++;
	    }
	    if(noWhitespaceInput.charAt(i)==')'){
		if(open==0){
		    return false;
		}
		open--;
	    }
	}
	return open==0;
    }
    
    /*public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	System.out.print("Enter math expression or \"0\" to stop: ");
	String expr = sc.nextLine();
	Calculator c;
	while(!expr.equals("0")){
	    try{
		c = new Calculator(expr);
		System.out.println("or else is: " + c.toString());
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
		System.out.println("Invalid input. Try again.");
	    }
	    System.out.print("Enter math expression or \"0\" to stop: ");
	    expr = sc.nextLine();
	}
    }*/
    
}
