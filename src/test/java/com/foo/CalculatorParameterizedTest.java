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
                new Object[]{"ADD", "1", "2", "3"},
                new Object[]{"ADD", "5", "10", "15"},
                new Object[]{"ADD", "5.1", "10.2", "15.3"}
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
