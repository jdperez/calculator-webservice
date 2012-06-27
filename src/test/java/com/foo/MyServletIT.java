package com.foo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/25/12
 * Time: 12:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyServletIT {
    RequestAndResponse requestAndResponse;

    @Test
    public void successfulPost() throws Exception{
        requestAndResponse =
                doPost("http://localhost:8888/foo",
                        param("operand1", "2"), param("operand2", "2"), param("operator", "ADD"));
        HttpEntity httpEntity = requestAndResponse.response.getEntity();
        assertThat(EntityUtils.toString(httpEntity),equalTo("4"));
        requestAndResponse.request.releaseConnection();
    }
  /*
    @Test
    public void initializeDatabaseSuccessful() throws Exception {
        requestAndResponse = doPost("http://localhost:8888/foo",
                                    param("operand1","40"), param("operand2","2"),param("operator","ADD"));
        requestAndResponse.request.releaseConnection();
        requestAndResponse = doPost("http://localhost:8888/foo",param("operator","HISTORY"));
        HttpEntity httpEntity = requestAndResponse.response.getEntity();
        assertThat(EntityUtils.toString(httpEntity), equalTo("40 ADD 2"));
        requestAndResponse.request.releaseConnection();
    }
 */

    private NameValuePair param(String key, String value) {
        return new BasicNameValuePair(key, value);
    }

    private RequestAndResponse doPost(String uri, NameValuePair... params) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(Arrays.asList(params), "UTF-8");
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        return new RequestAndResponse(httpPost, response);
    }

    private class RequestAndResponse {
        public final HttpRequestBase request;
        public final HttpResponse response;

        public RequestAndResponse(HttpRequestBase request, HttpResponse response) {
            this.request = request;
            this.response = response;
        }
    }

/** Mocking servlet container with ServletUnit
>>>>>>> 5c8b11ee83fd548058c24b453857e0ad424cf4bb

/** Mocking servlet container with ServletUnit
    @Test
    public void successfulPost() throws Exception {
        File file = new File("/home/jose5124/dev/projects/project1/src/main/webapp/WEB-INF/web.xml");
        ServletRunner servletRunner = new ServletRunner(file);
        servletRunner.registerServlet("myServlet", MyServlet.class.getName());
        ServletUnitClient servletUnitClient = servletRunner.newClient();
        WebRequest webRequest = new PostMethodWebRequest("http://localhost/myServlet");
        webRequest.setParameter("operator","ADD");
        webRequest.setParameter("operand1","2");
        webRequest.setParameter("operand2","2");
        InvocationContext invocationContext = servletUnitClient.newInvocation(webRequest);
        MyServlet myServlet = (MyServlet) invocationContext.getServlet();
        myServlet.doPost(invocationContext.getRequest(),invocationContext.getResponse());
        WebResponse webResponse = invocationContext.getServletResponse();
        assertThat(webResponse.getContentType(),equalTo("text/html"));
        assertThat(webResponse.getText(), equalTo("4"));
    }
**/

}
