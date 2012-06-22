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
        String result = new String();
        if (operand1.equals("") || operand2.equals("")) {
            return "Not enough operands.";
        }

        switch(operation) {
            case ("ADD"):
                if (isInteger(operand1) && isInteger(operand2)) {
                    result = String.valueOf(Integer.parseInt(operand1) + Integer.parseInt(operand2));
                }
                else {
                    double d = Double.parseDouble(operand1) + Double.parseDouble(operand2);
                    DecimalFormat tenDecimalForm = new DecimalFormat("0.0###########");
                    result = tenDecimalForm.format(d);
                } break;

            case ("SUBTRACT"):
                if (isInteger(operand1) && isInteger(operand2)) {
                    result = String.valueOf(Integer.parseInt(operand1) - Integer.parseInt(operand2));
                }
                else {
                    double d = Double.parseDouble(operand1) - Double.parseDouble(operand2);
                    DecimalFormat tenDecimalForm = new DecimalFormat("0.0###########");
                    result = tenDecimalForm.format(d);
                } break;
        }
        return result;
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
