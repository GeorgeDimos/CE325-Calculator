/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
	final String input1 = input.replaceAll("\\s","");
	String regex = "(\\(+(\\d|\\d+.\\d+)|(\\d|\\d+.\\d+))([\\+\\-\\*\\/\\^](\\d|\\d+.\\d+)|\\)+[\\+\\-\\*\\/\\^](\\d|\\d+.\\d+)|[\\+\\-\\*\\/\\^]\\(+(\\d|\\d+.\\d+)|\\)+[\\+\\-\\*\\/\\^]\\(+(\\d|\\d+.\\d+))*\\)*";
	if(!input1.matches(regex)){
	    return false;
	}

	int open = 0;
	for(int i=0;i<input1.length();i++){
	    if(input1.charAt(i)=='('){
		open++;
	    }
	    if(input1.charAt(i)==')'){
		if(open==0){
		    return false;
		}
		open--;
	    }
	}
	return open==0;
    }
    
    public static void main(String[] args) {
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
    }
    
}
