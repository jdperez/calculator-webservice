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
                //new Object[]{"ADD", "1000000000", "2000000000","3000000000"},
                //Decimal tests
                new Object[]{"ADD", "0.0", "0.0", "0.0"},
                new Object[]{"ADD", "-1.0", "-0.5", "-1.5"},
                new Object[]{"ADD", "5.1", "10.2", "15.3"},
                new Object[]{"ADD", "-5.0", "5.0", "0.0"},
                new Object[]{"ADD", "0.00000000001", "0.00000000001", "0.00000000002"},
                //Mixed operands tests
                new Object[]{"ADD", "10", "10.0", "20.0"},
                new Object[]{"ADD", "-10.0","5","-5.0"}

        );
    }
    public CalculatorParameterizedTest(String operation, String operand1, String operand2, String expectedResult){

        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.expectedResult = expectedResult;
    }

    @Test
    public void additionSucceeds(){
        Calculator calculator = new Calculator();
        String calculateResult = calculator.calculate(operation,operand1,operand2);
        assertThat(calculateResult,equalTo(expectedResult));
    }
}
