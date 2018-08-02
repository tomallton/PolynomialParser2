package com.tomallton.polynomial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringJoiner;

public class PolynomialParser {

	public static final Map<Character, Integer> OPERATOR_PRECEDENCES = new HashMap<>();

	static {
		OPERATOR_PRECEDENCES.put('+', 1);
		OPERATOR_PRECEDENCES.put('-', 1);
		OPERATOR_PRECEDENCES.put('*', 2);
		OPERATOR_PRECEDENCES.put('/', 2);
		OPERATOR_PRECEDENCES.put('^', 3);
	}

	public static void main(String[] args) {
		System.out.println(evaluateReversePolish(infixToReversePolish("((15 / (7 - (1 + 1))) * 3) - (2 + (1 + 1))")));
	}

	public static double evaluateReversePolish(String reversePolish) {
		return evaluateReversePolish0(reversePolish.split(" "));
	}

	private static double evaluateReversePolish0(String[] reversePolish) {

		Stack<String> stack = new Stack<>();

		for (String token : reversePolish) {

			if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^")) {
				double b = Double.valueOf(stack.pop());
				double a = Double.valueOf(stack.pop());

				double answer = 0;

				if (token.equals("+")) {
					answer = a + b;
				} else if (token.equals("-")) {
					answer = a - b;
				} else if (token.equals("*")) {
					answer = a * b;
				} else if (token.equals("/")) {
					answer = a / b;
				} else if (token.equals("^")) {
					answer = Math.pow(a, b);
				}

				stack.push(String.valueOf(answer));
			} else {
				stack.push(token);
			}

		}

		return Double.parseDouble(stack.pop());
	}

	public static String infixToReversePolish(String infix) {
		infix = infix.replaceAll(" ", "");

		List<String> list = new ArrayList<>();

		char[] chars = infix.toCharArray();

		int i = 0;

		StringBuilder number = new StringBuilder();

		while (i < chars.length) {

			char token = chars[i];

			switch (token) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				number.append(token);
				break;
			default:

				// add number and reset
				if (!number.toString().isEmpty()) {
					list.add(number.toString());
					number = new StringBuilder();
				}

				// add operator
				list.add(new StringBuilder().append(token).toString());
			}
			i++;
		}

		if (!number.toString().isEmpty()) {
			list.add(number.toString());
		}

		return infixToReversePolish0(list.toArray(new String[list.size()]));
	}

	private static String infixToReversePolish0(String[] infix) {

		StringJoiner reversePolish = new StringJoiner(" ");

		Stack<Character> operatorStack = new Stack<>();

		for (String string : infix) {
			char token = string.toCharArray()[0];

			switch (token) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				// add number straight to output
				reversePolish.add(string);
				break;
			case '+':
			case '-':
			case '*':
			case '/':
			case '^':
				while (!operatorStack.isEmpty() && operatorStack.peek() != '(' && hasGreaterPrecedence(operatorStack.peek(), token)) {
					reversePolish.add(new String(new char[] { operatorStack.pop() }));
				}
				operatorStack.push(token);
				break;
			case '(':
				operatorStack.push(token);
				break;
			case ')':
				while (operatorStack.peek() != '(') {
					reversePolish.add(new String(new char[] { operatorStack.pop() }));
				}
				operatorStack.pop();
				break;
			}

		}

		while (!operatorStack.isEmpty()) {
			reversePolish.add(new String(new char[] { operatorStack.pop() }));
		}

		return reversePolish.toString();
	}

	private static boolean hasGreaterPrecedence(char operator1, char operator2) {
		return OPERATOR_PRECEDENCES.get(operator1) >= OPERATOR_PRECEDENCES.get(operator2);
	}

}