package com.foo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/6/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("calculation")
public class CalculatorJaxRsResource {
    private CalculatorFacade calculatorFacade;

    @Autowired
    public CalculatorJaxRsResource(CalculatorFacade calculatorFacade) {
        this.calculatorFacade = calculatorFacade;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Calculation calculate(Calculation calculation) {
        Integer result = calculatorFacade.divide(calculation);
        calculation.setResult(result);
        return calculation;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Calculations getHistory() {
        return calculatorFacade.loadAllCalculations();
    }
}
