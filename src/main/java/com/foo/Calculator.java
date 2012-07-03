package com.foo;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/3/12
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Calculator {
    String enumCalculate(String operation, String operand1, String operand2);

    String findEnumOperator(String operation, String operand1, String operand2);
}
