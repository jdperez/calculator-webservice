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

    @Autowired
    public DefaultCalculatorFacade(Calculator calculator, CalculatorDao calculatorDao) {
        this.calculator = calculator;
        this.calculatorDao = calculatorDao;
    }

    @PostConstruct
    public void startingUp() {
        System.out.println("CalculatorFacade is starting up");
    }

    @PreDestroy
    public void shutDown() {
        System.out.println("CalculatorFacade is shutting down");
    }

    @Override
    public int divide(Calculation calculation) {
        if (calculation.getOperand1() == null || calculation.getOperand2() == null) {
            throw new BadUserInputException("Not enough operands");
        }
        String operand1String = String.valueOf(calculation.getOperand1());
        String operand2String = String.valueOf(calculation.getOperand2());
        String result = calculator.enumCalculate("DIVIDE",operand1String,operand2String);
        if (result.equals("not enough operands or divide by zero")) {
            throw new BadUserInputException("Division by zero");
        }
        try {
            if (Integer.valueOf(result) > 10) {
                throw new BadUserInputException("Result greater than 10");
            } else if (Integer.valueOf(result) < 0) {
                throw new BadUserInputException("Result less than 0");
            }
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Unexpected string from calculator: "+ result,e);
        }
        calculatorDao.save(calculation);
        return Integer.parseInt(result);  //To change body of created methods use File | Settings | File Templates.
    }
}
