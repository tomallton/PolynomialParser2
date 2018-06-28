package com.tomallton.polynomial;

import java.util.Stack;
import java.util.StringJoiner;

public class PolynomialParser {
	
	public static void main(String[] args) {
		System.out.println(infixToReversePolish("33+21"));
	}

	public static String infixToReversePolish(String infix) {

		StringJoiner reversePolish = new StringJoiner(" ");

		Stack<String> stack = new Stack<>();
		StringBuilder number = new StringBuilder();

		infix = infix.replaceAll(" ", "");

		char[] chars = infix.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];

			switch (c) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				number.append(c);
				break;
			case '+':
			case '-':
			case '*':
			case '/':
				reversePolish.add(number.toString());
				
				
				stack.push(number.toString());
				number = new StringBuilder();

				reversePolish.add(stack.pop());
			}

		}

		return reversePolish.toString();
	}

}