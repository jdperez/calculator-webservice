package com.foo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/18/12
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(Parameterized.class)
public class CalculatorParameterizedTest {
    private final String operation;
    private final String operand1;
    private final String operand2;
    private final String expectedResult;

    @Parameterized.Parameters
    public static Collection<Object[]> parameters(){
        return Arrays.asList(
                //Integer tests
                new Object[]{"ADD", "1", "2", "3"},
                new Object[]{"ADD", "5", "10", "15"},
                new Object[]{"ADD", "0", "0", "0"},
                new Object[]{"ADD", "-1", "2", "1"},
                new Object[]{"ADD", "-5", "-2", "-7"},
                new Object[]{"ADD", "", "", "Not enough operands."},
                new Object[]{"ADD", "5", "", "Not enough operands."},
                new Object[]{"ADD", "", "3", "Not enough operands."},
                new Object[]{"SUBTRACT", "2", "1", "1"},
                new Object[]{"SUBTRACT", "5", "5", "0"},
                new Object[]{"SUBTRACT", "-3", "-2", "-1"},
                new Object[]{"MULTIPLY", "10", "2", "20"},
                new Object[]{"MULTIPLY", "4", "2", "8"},
                new Object[]{"DIVIDE", "6", "2", "3"},
                new Object[]{"DIVIDE", "10", "5", "2"},
                //new Object[]{"ADD", "1000000000", "2000000000","3000000000"},
                //Decimal tests
                new Object[]{"ADD", "0.0", "0.0", "0.0"},
                new Object[]{"ADD", "-1.0", "-0.5", "-1.5"},
                new Object[]{"ADD", "5.1", "10.2", "15.3"},
                new Object[]{"ADD", "-5.0", "5.0", "0.0"},
                new Object[]{"ADD", "0.00000000001", "0.00000000001", "0.00000000002"},
                new Object[]{"SUBTRACT", "0.00000004", "0.00000002", "0.00000002"},
                new Object[]{"SUBTRACT", "-1.0", "3.5", "-4.5"},
                new Object[]{"MULTIPLY", "5.0", "3.0", "15.0"},
                new Object[]{"MULTIPLY", "5.3", "1.6", "8.48"},
                new Object[]{"DIVIDE", "0", "0", "ERROR, divide by zero"},
                new Object[]{"DIVIDE", "3", "0", "ERROR, divide by zero"},
                new Object[]{"DIVIDE", "3.0", "6", "0.5"},
                //Mixed operands tests
                new Object[]{"ADD", "10", "10.0", "20.0"},
                new Object[]{"ADD", "-10.0","5","-5.0"},
                new Object[]{"SUBTRACT", "2.0", "1", "1.0"},
                new Object[]{"SUBTRACT", "3.2", "6", "-2.8"},
                new Object[]{"SUBTRACT", "5", "4.1", "0.9"},
                new Object[]{"MULTIPLY", "5", "1.7", "8.5"},
                new Object[]{"MULTIPLY", "-5.0", "3.1", "-15.5"},
                new Object[]{"DIVIDE", "-6", "2.0", "-3.0"},
                new Object[]{"DIVIDE", "10.0", "2.0", "5.0"}



        );
    }
    public CalculatorParameterizedTest(String operation, String operand1, String operand2, String expectedResult){

        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.expectedResult = expectedResult;
    }


    @Test
    public void enumOperationSucceeds(){
        Calculator calculator = new Calculator();
        String calculateResult = calculator.enumCalculate(operation, operand1,operand2);
        assertThat(calculateResult, equalTo(expectedResult));
    }
}
