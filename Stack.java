package labb3;

public class Stack {
	
	String[] stack = new String[0];
	public static String operands = "0123456789";
	

	public String[] push(String pushObject) {
		String[] stackPush = new String[stack.length + 1];
		if(stack.length > 0) {	
			for(int n=0; n < stack.length; n++) {
				stackPush[n] = stack[n];
			}
			stackPush[stack.length] = pushObject;
		}
		else if(stack.length == 0) {	
			stackPush[stack.length] = pushObject;	
		}
		stack = stackPush;
		return stack;
	}
	
	public String pop() {
		String[] stackPop = new String[stack.length - 1];
		String popValue;
		popValue = stack[stack.length - 1];
		for(int i=0; i < stack.length - 1; i++) {
			stackPop[i] = stack[i];
		}
		stack = stackPop;
		return popValue;
	}
	
	public String peek() {
		if (stack.length == 0) {
			String[] newStack = new String[1];
			newStack[0] = "temp";
			stack = newStack;
		}
		return stack[stack.length-1];
	}
	
	public boolean ifEmpty() {
		boolean value = false;
		if (stack.length == 0) {
			value = true;
		}
		return value;
	}
	
	public int size() {
		return stack.length;
	}
	public String[] getStack() {
		return stack;
	}
	
	public int checkPrio(String input) {
		int prio = 0;
		if (memberOf(input.charAt(0), operands)) {
			prio = 5;
		}
		else if (input.equals("~")) {
			prio = 4;
		}
		else if (input.equals(")") || input.equals("(")) {
			prio = 3;
		}
		else if (input.equals("*") || input.equals("/")) {
			prio = 2;
		}
		else if (input.equals("+") || input.equals("-")) {
			prio = 1;			
		}
		else if (input.equals("temp")) {
			prio = -1;
		}
		return prio;
	}
	
	public static boolean memberOf(char c, String s) {
	    for(int i=0; i<s.length(); i++) {
	    	if(s.charAt(i) == c) {
	    		return true;
	    	}
	    }
	    return false;
	}
	
	public int position(String operator) {
		int position = 0;
		for (int i=0; i < stack.length; i++) {
			if (memberOf(operator.charAt(0), stack[i])) {
				position = i;	
			}		
		}
		return position;
	}
	public void remove(int position) {
		String[] stackRemove = new String[stack.length-1];
		if(stack.length != 1) {
			for (int a=0; a < position; a++) {
				stackRemove[a] = stack[a];
			}
		}
		stack = stackRemove;
	}
}
