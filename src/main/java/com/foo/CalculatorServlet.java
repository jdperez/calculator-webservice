package com.foo;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/15/12
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */

public class CalculatorServlet extends HttpServlet {
    private Calculator calculator;
    private CalculatorDao calculatorDAO;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        calculator = new DefaultCalculator();
        calculatorDAO = new CalculatorDao();
        //calculatorDAO = new CalculatorDao("jdbc:h2:~/Foo","sa", "sa");
        calculatorDAO.createTable();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        HttpSession httpSession = req.getSession(true);
        Integer startKey = (Integer) httpSession.getAttribute("tracker.startKey");
        if (startKey == null) {
            startKey = 1; //calculatorDAO.getCurrentMaxKey();
        }
        httpSession.setAttribute("tracker.startKey", startKey);
        String operator = req.getParameter("operator");
        if ("HISTORY".equals(operator)) {
            getHistory(startKey, out);
        } else {
            calculate(operator, req, out);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/client_form.jsp").forward(req,resp);
    }

    private void calculate(String operator, HttpServletRequest req, PrintWriter out) {
        String operand1 = req.getParameter("operand1");
        String operand2 = req.getParameter("operand2");
        String[] databaseInputs = {operator,operand1,operand2};
        //currentKey = calculatorDAO.save(databaseInputs);
        calculatorDAO.save(databaseInputs);
        String result = calculator.enumCalculate(operator,operand1,operand2);
        out.print(result);
    }

    private void getHistory(Integer startKey, PrintWriter out) {
        int currentMaxKey = calculatorDAO.getCurrentMaxKey();
        System.out.println(currentMaxKey);
        for (int i = startKey; i <= currentMaxKey; i++) {
            String[] history = calculatorDAO.load(i);
            String operator = history[0];
            String operand1 = history[1];
            String operand2 = history[2];
            out.print(operand1 + " " + operator + " " + operand2 + "<br />");
        }
    }
}

