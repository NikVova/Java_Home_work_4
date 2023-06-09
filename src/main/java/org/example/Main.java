package org.example;

import java.util.*;


public class Main {

    public static class InfixToPostfix {
        private static final Map<Character, Integer> OPERATOR_PRIORITY;

        static {
            OPERATOR_PRIORITY = new HashMap<>();
            OPERATOR_PRIORITY.put('+', 1);
            OPERATOR_PRIORITY.put('-', 1);
            OPERATOR_PRIORITY.put('*', 2);
            OPERATOR_PRIORITY.put('/', 2);
            OPERATOR_PRIORITY.put('^', 3);
        }

        public static String infixToPostfix(String infix) {
            StringBuilder postfix = new StringBuilder();
            Stack<Character> stack = new Stack<>();
            for (char c : infix.toCharArray()) {
                if (Character.isDigit(c)) {
                    postfix.append(c);
                } else if (OPERATOR_PRIORITY.containsKey(c)) {
                    while (!stack.empty() && stack.peek() != '(' && OPERATOR_PRIORITY.get(c) <= OPERATOR_PRIORITY.get(stack.peek())) {
                        postfix.append(stack.pop());
                    }
                    stack.push(c);
                } else if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    while (!stack.empty() && stack.peek() != '(') {
                        postfix.append(stack.pop());
                    }
                    stack.pop();
                }
            }
            while (!stack.empty()) {
                postfix.append(stack.pop());
            }
            return postfix.toString();
        }

        public static double evaluatePostfix(String postfix) {
            Stack<Double> stack = new Stack<>();
            for (char c : postfix.toCharArray()) {
                if (Character.isDigit(c)) {
                    stack.push((double) (c - '0'));
                } else {
                    double operand2 = stack.pop();
                    double operand1 = stack.pop();
                    switch (c) {
                        case '+':
                            stack.push(operand1 + operand2);
                            break;
                        case '-':
                            stack.push(operand1 - operand2);
                            break;
                        case '*':
                            stack.push(operand1 * operand2);
                            break;
                        case '/':
                            stack.push(operand1 / operand2);
                            break;
                        case '^':
                            stack.push(Math.pow(operand1, operand2));
                            break;
                    }
                }
            }
            return stack.pop();
        }

        public static void main(String[] args) {
            String infix = "(2^3 * (10 / (5 - 3)))^(Math.sin(Math.PI))";
            String postfix = infixToPostfix(infix);
            System.out.println("Постфиксная запись: " + postfix);
            double result = evaluatePostfix(postfix);
            System.out.println("Результат вычисления: " + result);
        }
    }
}