package com.foo;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/6/12
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestfulCalculatorServlet extends HttpServlet{
    private CalculatorFacade calculator;
    private CalculatorDao calculatorDao;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        calculator = context.getBean(CalculatorFacade.class);
        calculatorDao = context.getBean(CalculatorDao.class);
        calculatorDao.createTable();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        BufferedReader reader = req.getReader();
        if (req.getHeader("accept").matches("application/xml")){
            resp.setContentType("application/xml");
            xmlPost(out, reader, resp);
        }  else if (req.getHeader("accept").matches("application/json")) {
            resp.setContentType("application/json");
            jsonPost(out, reader, resp);

        }
    }

    private void jsonPost(PrintWriter out, BufferedReader reader, HttpServletResponse response) throws IOException {
        String line;
        Pattern operand1Pattern = Pattern.compile("\"operand1\": (.+),");
        Pattern operand2Pattern = Pattern.compile("\"operand2\": (.+) ");
        String operand1 = null, operand2 = null;

        while ((line = reader.readLine()) != null) {
            Matcher matcher1 = operand1Pattern.matcher(line);
            Matcher matcher2 = operand2Pattern.matcher(line);
            if (matcher1.find()) {
                operand1 = matcher1.group(1);
            }
            if (matcher2.find()) {
                operand2 = matcher2.group(1);
            }
        }
        if (operand1 == null || operand2 == null) {
            response.sendError(400, "Not enough operands.");
        }
        //String result = calculator.divide(new Calculation(Integer.valueOf(operand1),Integer.valueOf(operand2)));
        String resultJson = "\"value\": 5";
        out.print(resultJson);

    }

    private void xmlPost(PrintWriter out, BufferedReader reader, HttpServletResponse response) throws IOException {
        String line;
        Pattern Operand1Pattern = Pattern.compile("<operand1>(.+)</operand1>");
        Pattern Operand2Pattern = Pattern.compile("<operand2>(.+)</operand2>");
        String operand1 = null, operand2 = null;

        while ((line = reader.readLine()) != null) {
            Matcher matcher1 = Operand1Pattern.matcher(line);
            Matcher matcher2 = Operand2Pattern.matcher(line);
            if (matcher1.find()) {
                operand1 = matcher1.group(1);
            }
            if (matcher2.find()) {
                operand2 = matcher2.group(1);
            }
        }
        if (operand1 == null || operand2 == null) {
            response.sendError(400, "Not enough operands.");
        }
        //String result = calculator.divide(new Calculation(Integer.valueOf(operand1),Integer.valueOf(operand2)));
        //String resultXml = "<result>"+result+"</result>";
        //out.print(resultXml);
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/client_form.jsp").forward(req,resp);
    }
}