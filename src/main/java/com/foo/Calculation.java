package com.foo;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/6/12
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Calculation {
    private final int operand1;
    private final int operand2;

    public Calculation(int operand1, int operand2) {

        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public int getOperand1() {
        return operand1;
    }

    public int getOperand2() {
        return operand2;
    }
}
