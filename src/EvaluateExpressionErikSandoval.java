/*
Erik Sandoval
COSC 2336-01
Instructor: Professor Liu
Programming Assignment 3
Due: Feb 14th, 2022 - 11:59PM
Submitted: Feb 15th, 2022 - 12:05AM
This program will evaluate an expression entered by the user. It will use a stack to evaluate the expression.
It will also check for errors in the expression.
Amazon Corretto 17.0.2
Java
 */

import java.util.Stack; // import the Stack class
import java.util.Scanner; // import the Scanner class

// Class: EvaluateExpressionErikSandoval
public class EvaluateExpressionErikSandoval {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // create scanner object

        System.out.print("Enter the expression to be evaluated: "); // prompt user for expression
        String userInput = input.nextLine(); // read user input and store in string

        try { // try to evaluate the expression
            System.out.println(userInput + " = " +
                    evaluateExpression(userInput));
        }
        catch (Exception ex) { // catch any exceptions
            System.out.println("Wrong expression: " + userInput); // print error message
        }
    }
    // evaluate expression method
    public static int evaluateExpression(String expression) {
        Stack<Integer> operandStack = new Stack<>(); // create operand stack
        Stack<Character> operatorStack = new Stack<>(); // create operator stack

        expression = insertBlanks(expression); // insert blanks between operators

        String[] tokens = expression.split(" "); // split expression into tokens

        // process tokens
        for (String token: tokens) {
            if (token.length() == 0) // ignore empty tokens in string
                continue; // extract next token
            else if (token.charAt(0) == '+' || token.charAt(0) == '-') { // if token is an operator
                // process operator
                while (!operatorStack.isEmpty() && // while operator stack is not empty
                                (operatorStack.peek() == '+' || // and operator at top of stack is + or -
                                operatorStack.peek() == '-' ||
                                operatorStack.peek() == '*' || // or * or / or ^ or %
                                operatorStack.peek() == '/' ||
                                operatorStack.peek() == '%' ||
                                operatorStack.peek() == '^')) { // or ^
                    processAnOperator(operandStack, operatorStack); // process operator
                }

                // push operator on operator stack
                operatorStack.push(token.charAt(0));
            }
            else if (token.charAt(0) == '*' || // if token is * or / or ^ or %
                    token.charAt(0) == '/' || token.charAt(0) == '%') {
                // Process all *, /, % in the top of the operator stack
                while (!operatorStack.isEmpty() && // while operator stack is not empty
                        (operatorStack.peek() == '*' || // and operator at top of stack is * or / or ^ or %
                                operatorStack.peek() == '/' ||
                                operatorStack.peek() == '%' )) {
                    processAnOperator(operandStack, operatorStack); // process operator
                }

                // push operator on operator stack
                operatorStack.push(token.charAt(0));
            }
            else if (token.charAt(0) == '^') { // if token is ^
                // process all ^ in the top of the operator stack
                while (!operatorStack.isEmpty() && // while operator stack is not empty
                        operatorStack.peek() == '^') { // and operator at top of stack is ^
                    processAnOperator(operandStack, operatorStack); // process operator
                }
                // push operator on operator stack
                operatorStack.push(token.charAt(0));
            }
            else if (token.trim().charAt(0) == '(') { // if token is (
                operatorStack.push('('); // push ( on operator stack
            }
            else if (token.trim().charAt(0) == ')') { // if token is )
                // process all operators in the top of the operator stack
                while (operatorStack.peek() != '(') { // while operator at top of stack is not (
                    processAnOperator(operandStack, operatorStack); // process operator
                }

                operatorStack.pop(); // pop from operator stack
            }
            else { // if token is an operand
                operandStack.push(new Integer(token)); // push operand on operand stack
            }
        }

        // process all remaining operators in the top of the operator stack
        while (!operatorStack.isEmpty()) { // while operator stack is not empty
            processAnOperator(operandStack, operatorStack); // process operator
        }


        return operandStack.pop(); // return result
    }

    // process an operator
    public static void processAnOperator(
            Stack<Integer> operandStack, Stack<Character> operatorStack) { // operand stack and operator stack
        char op = operatorStack.pop(); // pop operator from operator stack
        int op1 = operandStack.pop(); // pop first operand from operand stack
        int op2 = operandStack.pop(); // pop second operand from operand stack
        if (op == '+') // if operator is +
            operandStack.push(op2 + op1); // push (op2 + op1) on operand stack
        else if (op == '-') // if operator is -
            operandStack.push(op2 - op1); // push (op2 - op1) on operand stack
        else if (op == '*') // if operator is *
            operandStack.push(op2 * op1); // push (op2 * op1) on operand stack
        else if (op == '/') // if operator is /
            operandStack.push(op2 / op1); // push (op2 / op1) on operand stack
        else if (op == '^') // if operator is ^
            operandStack.push((int)Math.pow(op2, op1)); // push (op2 ^ op1) on operand stack
        else if (op == '%') // if operator is %
            operandStack.push(op2 % op1); // push (op2 % op1) on operand stack
    }

    public static String insertBlanks(String s) { // insert blanks between tokens
        String result = ""; // result string

        for (int i = 0; i < s.length(); i++) { // for each token in s
            if (s.charAt(i) == '(' || s.charAt(i) == ')' || // if token is ( or ) or
                    s.charAt(i) == '+' || s.charAt(i) == '-' || // + or - or
                    s.charAt(i) == '*' || s.charAt(i) == '/' || // * or / or
                    s.charAt(i) == '^' || s.charAt(i) == '%') // ^ or %
                result += " " + s.charAt(i) + " "; // insert blank before and after token
            else // if token is not (, ), +, -, *, /, ^, %
                result += s.charAt(i); // append token to result
        }

        return result; // return result
    }
}