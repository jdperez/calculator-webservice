package com.foo;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/3/12
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorFacadeTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    CalculatorFacade calculatorFacade;
    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = mock(Calculator.class);
        calculatorFacade = new DefaultCalculatorFacade(calculator);
    }

    @Test
    public void dividesFirstOperandBySecond() throws Exception {
        when(calculator.enumCalculate("DIVIDE","10654654","265484")).thenReturn("9");
        String result = calculatorFacade.divide(10654654, 265484);
        assertThat(result, equalTo("9"));
        when(calculator.enumCalculate("DIVIDE","10","1")).thenReturn("3");
        result = calculatorFacade.divide(10,1);
        assertThat(result,equalTo("3"));
    }

    @Test
    public void returnWarningIfGreaterThanTen() throws Exception {
        when(calculator.enumCalculate("DIVIDE","1","1")).thenReturn("11");
        String result = calculatorFacade.divide(1,1);
        assertThat(result,equalTo("Result greater than 10"));

    }

    @Test
    public void returnWaringIfLessThanZero() throws Exception {
        when(calculator.enumCalculate(anyString(),anyString(),anyString())).thenReturn("-1");
        String result = calculatorFacade.divide(0,0);
        assertThat(result,equalTo("Result less than zero"));
    }

    @Test
    public void throwsExceptionOnDivideByZero() throws Exception {
        expectedException.expect(sameClass(IllegalArgumentException.class));
        expectedException.expectMessage("Division by zero");
        when(calculator.enumCalculate(anyString(), anyString(), anyString())).thenReturn("not enough operands or divide by zero");
        calculatorFacade.divide(0,0);
    }

    @Test
    public void throwsExceptionOnArbitraryString() throws Exception {
        expectedException.expect(sameClass(IllegalStateException.class));

        when(calculator.enumCalculate(anyString(),anyString(),anyString())).thenReturn("blarg");
        calculatorFacade.divide(1,2);
    }

    private Matcher<?> sameClass(final Class<?> clazz) {
        return new BaseMatcher<Object>() {
            @Override
            public boolean matches(Object o) {
                return clazz.equals(o.getClass());  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("the same class as "+ clazz);
            }
        };
    }

}
