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
        if (isInteger(operand1) && isInteger(operand2)) {
            return String.valueOf(Integer.parseInt(operand1) + Integer.parseInt(operand2));
        } else {
            double d = Double.parseDouble(operand1) + Double.parseDouble(operand2);
            DecimalFormat tenDecimalForm = new DecimalFormat("0.0###########");
            return tenDecimalForm.format(d);
        }
    }

    private static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        }
        catch(NumberFormatException nfe) {
            return false;
        }
    }
}
