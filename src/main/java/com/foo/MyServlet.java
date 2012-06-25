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
        Calculator calculator = new Calculator();
        String operator = req.getParameter("operator");
        String operand1 = req.getParameter("operand1");
        String operand2 = req.getParameter("operand2");

        // Must set content type first
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();



        out.println("<TITLE>Result</TITLE>");
        out.println("<H1>Result<H1>");
        out.println(operand1 + "&nbsp" + operator + "&nbsp" + operand2 + " = " + calculator.calculate(operator, operand1, operand2));
        out.println("</br>");
        out.println("<A HREF=\"/project1-1.0/foo\">Back</A>");






        //out.print(calculator.calculate(operator, operand1, operand2));
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/client_form.jsp").forward(req,resp);
    }
}

