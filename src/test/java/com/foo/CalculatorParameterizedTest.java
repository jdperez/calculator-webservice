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
    private final int operand1;
    private final int operand2;
    private final int expectedResult;

    @Parameterized.Parameters
    public static Collection<Object[]> parameters(){
        return Arrays.asList(
                new Object[]{"ADD", 1, 2, 3},
                new Object[]{"ADD", 5, 10, 15}
        );
    }
    public CalculatorParameterizedTest(String operation, int operand1, int operand2, int expectedResult){

        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.expectedResult = expectedResult;
    }

    @Test
    public void additionSucceeds(){
        Calculator calculator = new Calculator();
        int calculateResult = calculator.calculate(operation,operand1,operand2);
        assertThat(calculateResult,equalTo(expectedResult));
    }
}
