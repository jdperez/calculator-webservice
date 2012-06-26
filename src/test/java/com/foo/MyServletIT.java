package com.foo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
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

    @Test
    public void successfulPost() throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(new BasicNameValuePair("operand1", "2"));
        nameValuePairList.add(new BasicNameValuePair("operand2", "2"));
        nameValuePairList.add(new BasicNameValuePair("operator", "ADD"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
        HttpPost httpPost = new HttpPost("http://localhost:8888/foo");
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        assertThat(EntityUtils.toString(httpEntity),equalTo("4"));
    }



/** Mocking servlet container with ServletUnit

    @Test
    public void successfulPost() throws Exception{
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
*/
}
