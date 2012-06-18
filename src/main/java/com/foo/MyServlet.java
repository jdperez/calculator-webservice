package com.foo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/15/12
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */

public class MyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Must set content type first
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        Enumeration params = req.getParameterNames();
        String paramName = null;
        String[] paramValues = null;

        while(params.hasMoreElements()){
            paramName = (String) params.nextElement();
            paramValues = req.getParameterValues(paramName);
            out.println("\nParameter name is " + paramName);
            for (int i=0; i<paramValues.length; i++){
                out.println(", value " + i + " is " + paramValues[i].toString());
            }
        }


    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

}
