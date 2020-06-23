/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

/**
 *
 * @author George
 */
public class NodeTree {
    private final nodeT root;
        
    public NodeTree(String input){
	root = createTree(trimParen(input.replaceAll("\\s", "")));
    }
    
    private nodeT createTree(String input){
	int pos;
	nodeT cur = new nodeT();
	if((pos = opPosition(input))!=0){
	    cur.setVal(String.valueOf(input.charAt(pos)));
	    cur.setLeft(createTree(trimParen(input.substring(0, pos))));
	    cur.setRight(createTree(trimParen(input.substring(pos+1, input.length()))));
	}
	else{
	    cur.setVal(input);
	    cur.setLeft(null);
	    cur.setRight(null);
	}
	return cur;
    }
   
    private String trimParen(String input){
	if(input.charAt(0)!='(' || input.charAt(input.length()-1)!=')'){
	    return input;
	}
	int open=0;
	for(int i=1; i<input.length()-1;i++){
	    if( input.charAt(i)=='('){
		open++;
	    }
	    if(input.charAt(i)==')' && open>0){
		open--;
	    }
	}
	if(open!=0){
	    return input;
	}
	else{
	    return input.substring(1, input.length()-1);
	}
    }
    
    private int opPosition(String input){
	int open = 0;
	int[] index = new int[5];
	for(int i=0; i<input.length(); i++){
	    if( input.charAt(i)=='('){
		open++;
	    }
	    if( input.charAt(i)==')'){
		open--;
	    }
	    if(open==0){
		switch (input.charAt(i)) {
		    case '+':
			index[0] = i;
			break;
		    case '-':
			index[1] = i;
			break;
		    case '*':
			index[2] = i;
			break;
		    case '/':
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
	for(int i=0;i<5;i++){
	    if(index[i]>0){
		return index[i];
	    }
	}
	return 0;
    }
    
    public double calculate(){
	return calculate(root);
    }
    
    private double calculate(nodeT node){
	
	if(node.getLeft()==null || node.getRight()==null){
	    return Double.parseDouble(node.getVal());
	}
	
	double left=calculate(node.getLeft());
	double right=calculate(node.getRight());
	switch (node.getVal()){
	    case "+":
		return left+right;
	    case "-":
		return left-right;
	    case "*":
		return left*right;
	    case "/":
		return left/right;
	    case "^":
		return Math.pow(left,right);
	}
	return 0;
    }
    
    @Override
    public String toString(){
	return toString(root);
    }
    
    private String toString(nodeT cur){
	StringBuilder str = new StringBuilder();
	if(cur!=null){
	    
	    if(cur.getLeft()!=null){
		str.append("(");
		str.append(toString(cur.getLeft()));
	    }
	    
	    str.append(cur.getVal());
	    
	    if(cur.getRight()!=null){
		str.append(toString(cur.getRight()));
		str.append(")");
	    }
	    
	}
	return str.toString();
    }
    
    public String toDotString(){
	StringBuilder str = new StringBuilder();
	str.append("digraph ArithmeticExpressionTree {\n" + "label=\"Arithmetic Expression\"\n");
	str.append(toDotString(root));
	str.append("}");
	return str.toString();
    }
    
    private String toDotString(nodeT node){
	StringBuilder str = new StringBuilder();
	if(node!=null){
	    str.append(node.hashCode());
	    str.append(" [label=\"");
	    str.append(node.getVal());
	    str.append("\", shape=circle, color=black]\n");
	    
	    if(node.getLeft()!=null){
		str.append(node.hashCode()).append("->").append(node.getLeft().hashCode()).append("\n");
		str.append(toDotString(node.getLeft()));
	    }
	    if(node.getRight()!=null){
		str.append(node.hashCode()).append(" -> ").append(node.getRight().hashCode()).append("\n");
		str.append(toDotString(node.getRight()));
	    }
	}
	return str.toString();
    }
    
    private class nodeT {
	private nodeT left, right;
	private String val;
	
	public String getVal(){
	    return val;
	}
	
	public nodeT getLeft(){
	    return left;
	}
	
	public nodeT getRight(){
	    return right;
	}
	
	public void setVal(String val){
	    this.val = val;
	}
	
	public void setLeft(nodeT left){
	    this.left = left;
	}
	
	public void setRight(nodeT right){
	    this.right = right;
	}
    }
}
