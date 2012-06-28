package com.foo;

import com.sun.org.apache.xml.internal.serialize.Printer;

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
    CalculatorDAO calculatorDAO = new CalculatorDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String operator = req.getParameter("operator");
        if (operator.equals("HISTORY")) {
            getHistory(out);
        } else {
            calculate(operator, req, out);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/client_form.jsp").forward(req,resp);
    }

    private void calculate(String operator, HttpServletRequest req, PrintWriter out) {
        calculatorDAO.createTable();
        String operand1 = req.getParameter("operand1");
        String operand2 = req.getParameter("operand2");
        String[] databaseInputs = {operator,operand1,operand2};
        calculatorDAO.save(databaseInputs);
        String result = calculator.enumCalculate(operator,operand1,operand2);
        out.print(result);
    }

    private void getHistory(PrintWriter out) {
        int maxKey = calculatorDAO.getCurrentMaxKey();
        String[] history = calculatorDAO.load(maxKey);
        String operator = history[0];
        String operand1 = history[1];
        String operand2 = history[2];
        out.print(operand1+" "+operator+" "+operand2);
    }
}

