package com.foo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/9/12
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class Calculations {
    private List<Calculation> calculations = new ArrayList<Calculation>();

    public List<Calculation> getCalculations() {
        return calculations;
    }

    public void setCalculations(List<Calculation> calculations) {
        this.calculations = calculations;
    }
}
