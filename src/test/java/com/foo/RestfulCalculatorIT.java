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
import org.junit.Test;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/6/12
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestfulCalculatorIT {

    //RESTish tests
    @Test
    public void divisionSuccessfulUsingRest() throws Exception{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        StringEntity entity = new StringEntity(
                "<calculation>" +
                        "<operand1>10</operand1>"+
                        "<operand2>2</operand2>"+
                        "</calculation>", ContentType.APPLICATION_XML);
        HttpPost httpPost = new HttpPost("http://localhost:8888/foo/api/calculator");
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
        WebResource resource = Client.create().resource("http://localhost:8888/foo/api");
        ClientResponse response = resource.path("calculator").type(APPLICATION_JSON).entity(requestJson).accept(APPLICATION_JSON).post(ClientResponse.class);
        assertThat(response.getEntity(String.class),containsString("\"value\": 5"));
        assertThat(response.getStatus(),equalTo(200));
        assertThat(response.getHeaders().getFirst("Content-Type"),containsString("application/json"));
    }

    @Test
    public void divisionSuccessfulSendingJsonAndReceivingXml() {
        String requestJson = "{ \"operand1\": 10, \"operand2\": 2 }";
        WebResource resource = Client.create().resource("http://localhost:8888/foo/api");
        ClientResponse response = resource.path("calculator").type(APPLICATION_JSON).entity(requestJson).accept(APPLICATION_XML).post(ClientResponse.class);
        assertThat(response.getEntity(String.class),containsString("<result>5</result>"));
        assertThat(response.getStatus(),equalTo(200));
        assertThat(response.getHeaders().getFirst("Content-Type"),containsString("application/xml"));
    }

    @Test
    public void badRequestResponseBasedOnInsufficientOperands() throws Exception {
        String requestJson = "{ \"operand1\": 10 }";
        WebResource resource = Client.create().resource("http://localhost:8888/foo/api");
        ClientResponse response = resource.path("calculator").type(APPLICATION_JSON).entity(requestJson).accept(APPLICATION_JSON).post(ClientResponse.class);
        assertThat(response.getStatus(),equalTo(400));
    }
}
