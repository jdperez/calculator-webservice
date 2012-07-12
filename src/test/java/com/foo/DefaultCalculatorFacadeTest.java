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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/3/12
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCalculatorFacadeTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    CalculatorFacade calculatorFacade;
    private Calculator calculator;
    private CalculatorDao calculatorDao;

    @Before
    public void setUp() throws Exception {
        calculator = mock(Calculator.class);
        calculatorDao = mock(CalculatorDao.class);
        calculatorFacade = new DefaultCalculatorFacade(calculator, calculatorDao);
    }

    @Test
    public void dividesFirstOperandBySecond() throws Exception {
        when(calculator.enumCalculate("DIVIDE","10654654","265484")).thenReturn("9");
        int result = calculatorFacade.divide(calculation(10654654, 265484));
        assertThat(result, equalTo(9));
        when(calculator.enumCalculate("DIVIDE","10","1")).thenReturn("3");
        result = calculatorFacade.divide(calculation(10,1));
        assertThat(result,equalTo(3));
    }

    @Test
    public void throwsExceptionIfGreaterThanTen() throws Exception {
        expectedException.expect(sameClass(BadUserInputException.class));
        when(calculator.enumCalculate("DIVIDE","1","1")).thenReturn("11");
        calculatorFacade.divide(calculation(1, 1));

    }

    @Test
    public void throwsExceptionIfLessThanZero() throws Exception {
        expectedException.expect(sameClass(BadUserInputException.class));
        when(calculator.enumCalculate(anyString(),anyString(),anyString())).thenReturn("-1");
        calculatorFacade.divide(calculation(0, 0));
    }

    @Test
    public void throwsExceptionOnDivideByZero() throws Exception {
        expectedException.expect(sameClass(BadUserInputException.class));
        expectedException.expectMessage("Division by zero");
        when(calculator.enumCalculate(anyString(), anyString(), anyString())).thenReturn("not enough operands or divide by zero");
        calculatorFacade.divide(calculation(0,0));
    }

    @Test
    public void throwsExceptionOnArbitraryString() throws Exception {
        expectedException.expect(sameClass(IllegalStateException.class));
        when(calculator.enumCalculate(anyString(),anyString(),anyString())).thenReturn("blarg");
        calculatorFacade.divide(calculation(1,2));
    }

    @Test
    public void missingBothOperandsThrowsException () throws Exception {
        expectedException.expect(sameClass(BadUserInputException.class));
        calculatorFacade.divide(calculation(null,null));
    }

    @Test
    public void missingOperandOneThrowsException () throws Exception {
        expectedException.expect(sameClass(BadUserInputException.class));
        calculatorFacade.divide(calculation(null,123));
    }

    @Test
     public void missingOperandTwoThrowsException () throws Exception {
        expectedException.expect(sameClass(BadUserInputException.class));
        calculatorFacade.divide(calculation(123,null));
    }

    @Test
    public void successfulStoreOfCalculation() throws Exception {
        when(calculator.enumCalculate("DIVIDE","10","2")).thenReturn("5");
        Calculation calculation = new Calculation(10,2);
        calculatorFacade.divide(calculation);
        verify(calculatorDao).save(calculation);
    }

    @Test
    public void createTableSuccessfullyCalledInConstructor() throws Exception {
        calculatorDao = mock(CalculatorDao.class);
        new DefaultCalculatorFacade(calculator,calculatorDao);
        verify(calculatorDao).createTable();
    }

    @Test
    public void loadAllSavedCalculationsFromDao() throws Exception {
        calculatorFacade.loadAllCalculations();
        verify(calculatorDao).loadAll();
    }

    private Calculation calculation(Integer operand1, Integer operand2) {
        return new Calculation(operand1, operand2);
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
