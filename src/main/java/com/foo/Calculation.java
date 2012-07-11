package com.foo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/6/12
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class Calculation {
    private Integer operand1;
    private Integer operand2;
    private Integer result;

    public Calculation() {
    }

    public Calculation(Integer operand1, Integer operand2) {

        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public Integer getOperand1() {
        return operand1;
    }

    public void setOperand1(Integer operand1) {
        this.operand1 = operand1;
    }

    public Integer getOperand2() {
        return operand2;
    }

    public void setOperand2(Integer operand2) {
        this.operand2 = operand2;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Calculation{" +
                "operand1=" + operand1 +
                ", operand2=" + operand2 +
                ", result=" + result +
                '}';
    }
}
