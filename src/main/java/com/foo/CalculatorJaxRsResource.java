package com.foo;

import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/6/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("calculator")
public class CalculatorJaxRsResource {
    @POST
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Calculation calculate(Calculation calculation, @Context ServletContext servletContext) {
        CalculatorFacade calculator = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getBean(CalculatorFacade.class);
        String result = calculator.divide(calculation);
        calculation.setResult(result);
        return calculation;
    }
}
