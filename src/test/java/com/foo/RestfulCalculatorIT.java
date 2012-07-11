package com.foo;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/6/12
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestfulCalculatorIT {

    private WebResource resource;

    @Before
    public void setUp() throws Exception {
        resource = Client.create().resource("http://localhost:8888/foo/api");
    }

    //RESTish tests
    @Test
    public void divisionSuccessfulUsingRest() throws Exception{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        StringEntity entity = new StringEntity(
                "<calculation>" +
                        "<operand1>10</operand1>"+
                        "<operand2>2</operand2>"+
                        "</calculation>", ContentType.APPLICATION_XML);
        HttpPost httpPost = new HttpPost("http://localhost:8888/foo/api/calculation");
        httpPost.setHeader("accept","application/xml");
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        assertThat(response.getEntity().getContentType().getValue(),containsString("application/xml"));
        assertThat(IOUtils.toString(response.getEntity().getContent()),containsString("<result>5</result>"));
        assertThat(response.getStatusLine().getStatusCode(),equalTo(200));
    }

    @Test
    public void divisionSuccessfulUsingRestJson() {
        String requestJson = "{ \"operand1\": 10, \"operand2\": 2 }";
        ClientResponse response = resource.path("calculation").type(APPLICATION_JSON).entity(requestJson).accept(APPLICATION_JSON).post(ClientResponse.class);
        assertThat(response.getEntity(String.class),containsString("\"result\":\"5\""));
        assertThat(response.getStatus(),equalTo(200));
        assertThat(response.getHeaders().getFirst("Content-Type"),containsString("application/json"));
    }

    @Test
    public void divisionSuccessfulSendingJsonAndReceivingXml() {
        String requestJson = "{ \"operand1\": 10, \"operand2\": 2 }";
        ClientResponse response = resource.path("calculation").type(APPLICATION_JSON).entity(requestJson).accept(APPLICATION_XML).post(ClientResponse.class);
        assertThat(response.getEntity(String.class),containsString("<result>5</result>"));
        assertThat(response.getStatus(),equalTo(200));
        assertThat(response.getHeaders().getFirst("Content-Type"),containsString("application/xml"));
    }

    @Test
    public void badRequestResponseBasedOnInsufficientOperands() throws Exception {
        String requestJson = "{ \"operand1\": 10 }";
        ClientResponse response = resource.path("calculation").type(APPLICATION_JSON).entity(requestJson).accept(APPLICATION_JSON).post(ClientResponse.class);
        assertThat(response.getStatus(),equalTo(400));
    }

    @Test
    public void getHistoryAfterCalculationFromRestApi() throws Exception {
        resource.path("calculation").entity(new Calculation(10, 2)).post();
        ClientResponse response = resource.path("calculation").get(ClientResponse.class);
        assertThat(response.getStatus(), equalTo(200));
        Calculations calculations = response.getEntity(Calculations.class);
        assertThat(calculations.getCalculations(),hasItem(calculation(10, 2, 5)));
    }

    @Test
    public void divisionByZeroGeneratesResponse() throws Exception {
        ClientResponse response = resource.path("calculation").entity(new Calculation(10,0)).post(ClientResponse.class);
        assertThat(response.getStatus(),equalTo(400));
        assertThat(response.getEntity(String.class), containsString("Division by zero"));

    }

    @Test
    public void checkIfValueGreaterThanTen() throws Exception {
        ClientResponse response = resource.path("calculation").entity(new Calculation(11,1)).post(ClientResponse.class);
        assertThat(response.getStatus(), equalTo(400));
        assertThat(response.getEntity(String.class), containsString("greater than 10"));
    }

    @Test
    public void checkIfValueLessThanZero() throws Exception {
        ClientResponse response = resource.path("calculation").entity(new Calculation(-1,1)).post(ClientResponse.class);
        assertThat(response.getStatus(), equalTo(400));
        assertThat(response.getEntity(String.class), containsString("less than 0"));
    }

    private Matcher<Calculation> calculation(final int operand1, final int operand2, final int result) {
        return new TypeSafeMatcher<Calculation>() {
            @Override
            public boolean matchesSafely(Calculation calculation) {
                return ((operand1 == calculation.getOperand1()) && (operand2 == calculation.getOperand2()) && (result == calculation.getResult()));
            }

            @Override
            public void describeTo(Description description) {
                String msg = String.format("A calculation with operands %s and %s and result %s", operand1, operand2, result);
                description.appendText(msg);
            }
        };
    }


}
