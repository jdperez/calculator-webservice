package com.foo;

import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/18/12
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Calculator {
    private static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public String enumCalculate(String operation, String operand1, String operand2) {
        if ("".equals(operand1) || "".equals(operand2)) {
            return "Not enough operands.";
        } else if (("0".equals(operand1) || "0".equals(operand2)) && ("DIVIDE".equalsIgnoreCase(operation))) {
            return "ERROR, divide by zero";
        }
        return findEnumOperator(operation, operand1, operand2);
    }

    private String findEnumOperator(String operation,String operand1, String operand2) {
        String result;
        if ((isInteger(operand1) && isInteger(operand2))) {
            int intOperand1 = Integer.parseInt(operand1);
            int intOperand2 = Integer.parseInt(operand2);
            result = String.valueOf(Operations.valueOf(operation).calculate(intOperand1,intOperand2));
        }  else {
            DecimalFormat tenDecimalForm = new DecimalFormat("0.0###########");
            double doubleOperand1 = Double.parseDouble(operand1);
            double doubleOperand2 = Double.parseDouble(operand2);
            double calculateDouble = Operations.valueOf(operation).calculate(doubleOperand1,doubleOperand2);
            result = tenDecimalForm.format(calculateDouble);
        }
        return result;
    }
}
