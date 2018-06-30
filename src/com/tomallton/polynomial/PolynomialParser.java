package com.tomallton.polynomial;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringJoiner;

public class PolynomialParser {

	public static void main(String[] args) {
		System.out.println(infixToReversePolish("1+2*C-D"));
	}

	public static String infixToReversePolish(String infix) {

		infix = infix.replaceAll(" ", "");

		StringJoiner reversePolish = new StringJoiner(" ");

		Stack<Character> operatorStack = new Stack<>();
		Queue<Character> numberQueue = new LinkedList<>();

		char[] chars = infix.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];

			switch (c) {
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
				numberQueue.add(c);
				break;
			default:

				StringBuilder number = new StringBuilder();

				while (!numberQueue.isEmpty()) {
					number.append(numberQueue.poll());
				}

				reversePolish.add(number.toString());
			}

			switch (c) {
			case '+':
			case '-':
			case '*':
			case '/':
			case '^':
				if (!operatorStack.isEmpty())
				System.out.println(operatorStack.peek() + " " + c + " " + (operatorStack.peek() <= c));
				while (!operatorStack.isEmpty() && operatorStack.peek() <= c) {
					reversePolish.add(new String(new char[] { operatorStack.pop() }));
				}
				operatorStack.push(c);
			}

		}

		StringBuilder number = new StringBuilder();

		while (!numberQueue.isEmpty()) {
			number.append(numberQueue.poll());
		}

		reversePolish.add(number.toString());

		while (!operatorStack.isEmpty()) {
			reversePolish.add(new String(new char[] { operatorStack.pop() }));
		}

		return reversePolish.toString();
	}

}