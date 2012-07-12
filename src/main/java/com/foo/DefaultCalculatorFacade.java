package com.foo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/3/12
 * Time: 10:08 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DefaultCalculatorFacade implements CalculatorFacade {
    private Calculator calculator;
    private CalculatorDao calculatorDao;
    private static final int MAX_VALUE = 10;
    private static final int MIN_VALUE = 0;

    @Autowired
    public DefaultCalculatorFacade(Calculator calculator, CalculatorDao calculatorDao) {
        this.calculator = calculator;
        this.calculatorDao = calculatorDao;
        createTable();
    }

    @PostConstruct
    public void startingUp() {
    }

    @PreDestroy
    public void shutDown() {
    }

    @Override
    /* In order to get back the key from the save() call, I would need to introduce rethink our logic. One possibility is to
     add a key field in Calculation.*/
    public int divide(Calculation calculation) {
        if (calculation.getOperand1() == null || calculation.getOperand2() == null) {
            throw new BadUserInputException("Not enough operands");
        }
        String operand1String = String.valueOf(calculation.getOperand1());
        String operand2String = String.valueOf(calculation.getOperand2());
        String result = calculator.enumCalculate("DIVIDE",operand1String,operand2String);
        if (("not enough operands or divide by zero").equals(result)) {
            throw new BadUserInputException("Division by zero");
        }
        try {
            if (Integer.valueOf(result) > MAX_VALUE) {
                throw new BadUserInputException("Result greater than 10");
            } else if (Integer.valueOf(result) < MIN_VALUE) {
                throw new BadUserInputException("Result less than 0");
            }
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Unexpected string from calculator: "+ result,e);
        }
        calculatorDao.save(calculation);
        return Integer.parseInt(result);  //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public Calculations loadAllCalculations() {
        return calculatorDao.loadAll();
    }

    private void createTable() {
        calculatorDao.createTable();
    }
}
