package com.foo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/15/12
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */

public class MyServlet extends HttpServlet {
    Calculator calculator = new Calculator();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        String operator = req.getParameter("operator");
        if (!operator.equals("HISTORY")) {
            String operand1 = req.getParameter("operand1");
            String operand2 = req.getParameter("operand2");
            String result = calculate(operator,operand1,operand2);
            out.print(result);
        } else {
            //getHistory(out);
        }

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/client_form.jsp").forward(req,resp);
    }

    private String calculate(String operator, String operand1, String operand2) {
        String result = calculator.enumCalculate(operator,operand1,operand2);
        return result;
    }


}

