package com.foo;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/3/12
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CalculatorFacade {
    int divide(Calculation calculation) throws IllegalArgumentException;

    Calculations loadAllCalculations();
}
