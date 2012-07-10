package com.foo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/3/12
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SpringJdbcCalculatorDaoTest extends AbstractCalculatorDaoTest {
    @Autowired
    private SpringJdbcCalculatorDao dao;

    @Override
    protected CalculatorDao getDaoInstance() {
        return dao;
    }

    @Test
    public void successfulSaveUsingCalculationObject() throws Exception {
        Calculation input = new Calculation(10,2);
        int key = databaseFoo.save(input);
        Calculation output = databaseFoo.loadCalculation(key);
        assertThat(new Object[]{output.getOperand1(),output.getOperand2()},equalTo(new Object[]{input.getOperand1(),input.getOperand2()}));
    }

    @Test
    public void successfulLoadOfAllRecords() throws Exception {
        databaseFoo.save(new Calculation(25,5));
        databaseFoo.save(new Calculation(6,3));
        databaseFoo.save(new Calculation(12,2));
        Calculations output = databaseFoo.loadAll();
        List<Calculation> outputCalculations = output.getCalculations();
        assertThat(new Object[] {outputCalculations.get(0).getOperand1(),outputCalculations.get(0).getOperand2()},equalTo(new Object[]{25, 5}));
        assertThat(new Object[] {outputCalculations.get(1).getOperand1(),outputCalculations.get(1).getOperand2()},equalTo(new Object[]{6, 3}));
        assertThat(new Object[] {outputCalculations.get(2).getOperand1(),outputCalculations.get(2).getOperand2()},equalTo(new Object[]{12, 2}));
    }
}
