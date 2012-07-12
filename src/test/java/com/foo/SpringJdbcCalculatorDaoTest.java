package com.foo;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

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
    public void saveMissingOperand1ThrowsException() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        databaseFoo.save(new Calculation(null,1));
    }

    @Test
    public void saveMissingOperand2ThrowsException() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        databaseFoo.save(new Calculation(1,null));
    }

    @Test
    public void saveMissingBothOperandsThrowsException() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        databaseFoo.save(new Calculation(null,null));
    }

    @Test
    public void successfulSaveUsingCalculationObject() throws Exception {
        Calculation input = new Calculation(10,2);
        int key = databaseFoo.save(input);
        Calculation output = databaseFoo.loadCalculation(key);
        assertThat(new Object[]{output.getOperand1(),output.getOperand2()},equalTo(new Object[]{input.getOperand1(),input.getOperand2()}));
    }

    @Test
    public void successfulLoadOfSeveralRecords() throws Exception {
        //TODO: assert is too strict
        databaseFoo.save(new Calculation(25,5));
        databaseFoo.save(new Calculation(6,3));
        databaseFoo.save(new Calculation(12,2));
        Calculations output = databaseFoo.loadAll();
        assertThat(output.getCalculations(),hasItem(calculation(25,5)));
        assertThat(output.getCalculations(),hasItem(calculation(6,3)));
        assertThat(output.getCalculations(),hasItem(calculation(12,2)));
    }

    private Matcher<Calculation> calculation(final int operand1, final int operand2) {
        return new TypeSafeMatcher<Calculation>() {
            @Override
            public boolean matchesSafely(Calculation calculation) {
                return ((operand1 == calculation.getOperand1()) && (operand2 == calculation.getOperand2()));
            }

            @Override
            public void describeTo(Description description) {
                String msg = String.format("A calculation with operands %s and %s", operand1, operand2);
                description.appendText(msg);
            }
        };
    }
}
