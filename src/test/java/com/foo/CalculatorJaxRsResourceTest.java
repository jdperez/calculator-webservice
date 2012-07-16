package com.foo;

import com.rackspace.monitoring.DelegateProxyInitializer;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.web.context.ContextLoaderListener;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorJaxRsResourceTest extends JerseyTest {
    @Mock
    private CalculatorFacade calculatorFacade;

    @Override
    protected AppDescriptor configure() {
        return new WebAppDescriptor.Builder()
                .contextListenerClass(ContextLoaderListener.class)
                .contextParam("contextConfigLocation", "classpath:/com/foo/CalculatorJaxRsResourceTest-context.xml")
                .servletClass(SpringServlet.class)
                .build();
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        DelegateProxyInitializer.initProxies(this);
    }

    @Test
    public void postedCalculationProcessedAndReturned() throws Exception {
        when(calculatorFacade.divide(any(Calculation.class))).thenReturn(22);
        Calculation calculation = new Calculation(10,5);
        ClientResponse response = resource().path("calculation").entity(calculation).post(ClientResponse.class);
        assertThat(response.getStatus(),equalTo(200));
        Calculation calculationResult = response.getEntity(Calculation.class);
        assertThat(calculationResult.getResult(), equalTo(22));
    }
}
