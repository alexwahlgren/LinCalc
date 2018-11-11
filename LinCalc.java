package labb3;

import java.util.Scanner;
import lincalc.LinCalcJohn;

public class LinCalc {

	public static String operators = "+-*/()~";
	public static String pluMin = "+-";
	public static String unaMin = "~";
	public static String mulDiv = "*/";
	public static String commas = ",.";
	public static String operands = "0123456789";
	public static String parLeft = "(";
	public static String parRight = ")";
	public static Stack calcStack = new Stack();

	public static void printArray(String[] array) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
			sb.append(", ");
		}
		// Replace the last ", " with "]"
		sb.replace(sb.length() - 2, sb.length(), "]");
		System.out.println(sb);
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String expression;

		double result;

		System.out.print("Enter expression: ");
		expression = input.nextLine();
		do {
			result = evaluate(expression);
			System.out.println("Result was: " + result);
			System.out.print("Enter expression: ");
		} while (!"".equals(expression = input.nextLine()));
	}

	public static double calcPopValue(String operator) {
		/*
		 * Den här metoden tar in en operator och poppar två värden i stacken som den
		 * sedan operar på. Resultet returneras som "value"
		 */
		double a = Double.parseDouble(calcStack.pop());
		double value = 0;
		if (operator.equals("~"))
			value = -a;
		else {
			double b = Double.parseDouble(calcStack.pop());
			if (operator.equals("+"))
				value = b + a;
			if (operator.equals("-"))
				value = b - a;
			if (operator.equals("*"))
				value = b * a;
			if (operator.equals("/"))
				value = b / a;
		}
		return value;
	}

	public static double calc(String[] lexedPostfix) {
		/*
		 * Den här metoden använder det returnerade värdet ifrån metoden "calcPopValue"
		 * som den pushar till stacken. Detta värdet används igen utav samma metod och
		 * bildar nya värden för att tillslut bilda resultatet.
		 */
		// Scannar igenom elementen i postfix-stringen
		for (int i = 0; i < lexedPostfix.length; i++) {
			String element = lexedPostfix[i];
			// Olika fall beroende på vilket element som kommer
			if (element.equals("+"))
				calcStack.push(String.valueOf(calcPopValue("+")));
			else if (element.equals("-"))
				calcStack.push(String.valueOf(calcPopValue("-")));
			else if (element.equals("*"))
				calcStack.push(String.valueOf(calcPopValue("*")));
			else if (element.equals("/"))
				calcStack.push(String.valueOf(calcPopValue("/")));
			else if (element.equals("~"))
				calcStack.push(String.valueOf(calcPopValue("~")));
			else if (memberOf(element.charAt(0), operands))
				calcStack.push(element);
		}
		String stringResult = calcStack.pop();
		// Parsar resultatet ifrån en string till en double
		double result = Double.parseDouble(stringResult);
		return result;
	}

	public static String[] toPostfix(String[] inputData) {
		Stack stack = new Stack();
		Queue queue = new Queue();
		for (int i = 0; i < inputData.length; i++) {
			String inputDataPos = inputData[i];
			switch (stack.checkPrio(inputDataPos)) {
			case 1:
				while ((stack.checkPrio(stack.peek()) != 3)) {
					if (stack.checkPrio(stack.peek()) == -1)
						break;
					queue.add(stack.pop());
				}
				stack.push(inputDataPos);
				break;
			case 2:
				while (stack.checkPrio(stack.peek()) == 2)
					queue.add(stack.pop());
				stack.push(inputDataPos);
				break;
			case 3:
				if (memberOf(inputDataPos.charAt(0), ")")) {
					for (int n = (stack.position("(") + 1); n < stack.size(); n++)
						queue.add(stack.pop());
					stack.remove(stack.position("("));
				} else
					stack.push(inputDataPos);
				break;
			case 4:
				stack.push(inputDataPos);
				break;
			case 5:
				queue.add(inputDataPos);
				if (stack.checkPrio(stack.peek()) == 4)
					queue.add(stack.pop());
				break;
			}
		}
		int stackSize = stack.size();
		for (int j = 0; j < stackSize; j++)
			queue.add(stack.pop());
		queue.removeTemp(queue.size() - 1);
		return queue.getQueue();
	}

	public static double evaluate(String expression) {
		String[] lexedInfix = lex(expression);
		String[] lexedPostfix = toPostfix(lexedInfix);
		return calc(lexedPostfix);
	}

	public static boolean memberOf(char c, String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == c) {
				return true;
			}
		}
		return false;
	}

	public static String[] lex(String expr) {
		String[] expr_seperated = new String[(expr.length() + 10)];
		String currentNumber = "";
		int counter = 0;
		for (int i = 0; i < expr.length(); i++) {
			char currentChar = expr.charAt(i);
			if (memberOf(currentChar, operands))
				currentNumber += currentChar;
			else if (memberOf(currentChar, commas))
				currentNumber += currentChar;
			else if (memberOf(currentChar, operators)) {
				if (currentChar == '-' && (i == 0 || (memberOf(expr.charAt(i - 1), operators)))) {
					expr_seperated[counter] = String.valueOf(currentChar);
					counter++;
				} else {
					if (!(currentNumber == "")) {
						expr_seperated[counter] = currentNumber;
						counter++;
						currentNumber = "";
					}
					expr_seperated[counter] = String.valueOf(currentChar);
					counter++;
				}
			}
		}
		if (!currentNumber.isEmpty()) {
			expr_seperated[counter] = currentNumber;
			counter++;
		}
		String[] expr_seperated_rightlength = new String[counter];
		for (int j = 0; j < counter; j++)
			expr_seperated_rightlength[j] = expr_seperated[j];
		return expr_seperated_rightlength;
	}
}
