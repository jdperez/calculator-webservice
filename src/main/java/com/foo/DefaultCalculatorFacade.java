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

    @Autowired
    public DefaultCalculatorFacade(Calculator calculator) {
        this.calculator = calculator;
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
    public String divide(Calculation calculation) {
        String operand1String = String.valueOf(calculation.getOperand1());
        String operand2String = String.valueOf(calculation.getOperand2());
        String result = calculator.enumCalculate("DIVIDE",operand1String,operand2String);
        if (result.equals("not enough operands or divide by zero")) {
            throw new IllegalArgumentException("Division by zero");
        }
        try {
            if (Integer.valueOf(result) > 10) {
                return "Result greater than 10";
            } else if (Integer.valueOf(result) < 0) {
                return "Result less than zero";
            }
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Unexpected string from calculator: "+ result,e);
        }
        return result;  //To change body of created methods use File | Settings | File Templates.
    }
}
