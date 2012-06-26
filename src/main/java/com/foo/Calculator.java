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

    public String calculate(String operation, String operand1, String operand2) {
        // int test = maxValueCheck(operand1,operand2);
        //final String result;
        if ("".equals(operand1) || "".equals(operand2)) {
            return "Not enough operands.";
        } else if (("0".equals(operand1) || "0".equals(operand2)) && ("DIVIDE".equalsIgnoreCase(operation))) {
            return "ERROR, divide by zero";
        }


        DecimalFormat tenDecimalForm = new DecimalFormat("0.0###########");


        return identifyAndPerformOperation(operation, operand1, operand2, tenDecimalForm);
    }

    private String identifyAndPerformOperation(String operation, String operand1, String operand2, DecimalFormat tenDecimalForm) {
        String result;
        if ("ADD".equals(operation)) {
            if (isInteger(operand1) && isInteger(operand2)) {
                result = String.valueOf(Integer.parseInt(operand1) + Integer.parseInt(operand2));
            } else {
                double d = Double.parseDouble(operand1) + Double.parseDouble(operand2);
                result = tenDecimalForm.format(d);
            }

        } else if ("SUBTRACT".equals(operation)) {
            if (isInteger(operand1) && isInteger(operand2)) {
                result = String.valueOf(Integer.parseInt(operand1) - Integer.parseInt(operand2));
            } else {
                double d = Double.parseDouble(operand1) - Double.parseDouble(operand2);
                result = tenDecimalForm.format(d);
            }

        } else if ("MULTIPLY".equals(operation)) {
            if (isInteger(operand1) && isInteger(operand2)) {
                result = String.valueOf(Integer.parseInt(operand1) * Integer.parseInt(operand2));
            } else {
                double d = Double.parseDouble(operand1) * Double.parseDouble(operand2);
                result = tenDecimalForm.format(d);
            }

        } else {
            if (isInteger(operand1) && isInteger(operand2)) {
                result = String.valueOf(Integer.parseInt(operand1) / Integer.parseInt(operand2));
            } else {
                double d = Double.parseDouble(operand1) / Double.parseDouble(operand2);
                result = tenDecimalForm.format(d);
            }
        }
        return result;
    }


    private static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
