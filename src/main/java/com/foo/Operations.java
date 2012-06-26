package com.foo;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/26/12
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Operations {
    ADD {
        public double calculate(double operand1, double operand2) { return operand1 + operand2; }
        public int calculate(int operand1, int operand2) {return operand1 + operand2;}
    },
    SUBTRACT {
        public double calculate(double operand1, double operand2) { return operand1 - operand2; }
        public int calculate(int operand1, int operand2) { return operand1 - operand2;}
    },
    MULTIPLY {
        public double calculate(double operand1, double operand2) { return operand1 * operand2; }
        public int calculate(int operand1, int operand2) { return operand1 * operand2; }
    },
    DIVIDE {
        public double calculate(double operand1, double operand2) { return operand1 / operand2; }
        public int calculate(int operand1, int operand2) { return operand1 / operand2; }
    };

    public abstract double calculate(double operand1, double operand2);
    public abstract int calculate(int operand1, int operand2);
}
