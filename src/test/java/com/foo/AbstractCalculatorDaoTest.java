package com.foo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Iterator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/3/12
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCalculatorDaoTest {
    CalculatorDao databaseFoo;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    protected abstract CalculatorDao getDaoInstance();

    @Before
    public void setUp() {
        databaseFoo = getDaoInstance();
        databaseFoo.createTable();
    }

    @Test
    public void successfulCallToConstructor() throws Exception {
        DirectJdbcCalculatorDao calculatorDao = new DirectJdbcCalculatorDao("sa", "sa");
        int key = calculatorDao.getCurrentMaxKey();
        assertThat(key, greaterThanOrEqualTo(0));
    }

    @Test
    public void emptyPasswordThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        new DirectJdbcCalculatorDao("sa","");
    }

    @Test
    public void emptyUsernameThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        new DirectJdbcCalculatorDao("","sa");
    }

    @Test
    public void emptyUsernamePasswordThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        new DirectJdbcCalculatorDao("","");
    }

    @Test
    public void emptyStringThrowsExceptionInSave() {
        expectedException.expect(IllegalArgumentException.class);
        String[] databaseInputs = {"","","",""};
        databaseFoo.save(databaseInputs);
    }

    @Test
    public void successfulSave() {
        String[] databaseInputs = {"ADD","1","2"};
        int key = databaseFoo.save(databaseInputs);
        String[] loadedOutputs = databaseFoo.load(key);
        assertThat(loadedOutputs, equalTo(databaseInputs));
    }

    @Test
    public void successfulDuplicateSaves(){
        String[] databaseInputs1 = {"ADD","1","2"};
        String[] databaseInputs2 = {"ADD","1","2"};
        databaseFoo.save(databaseInputs1);
        int key = databaseFoo.save(databaseInputs2);
        String[] loadedOutput = databaseFoo.load(key);
        assertThat(loadedOutput, equalTo(databaseInputs2));
    }

    @Test
    public void severalSuccessfulUniqueSaves() {
        ArrayList<String[]> inputStrings = new ArrayList<String[]>();
        String[] databaseInputs1 = {"ADD","5","9"};
        String[] databaseInputs2 = {"ADD","3","4"};
        String[] databaseInputs3 = {"ADD","2","3"};
        String[] databaseInputs4 = {"ADD","1","2"};
        inputStrings.add(databaseInputs1);
        inputStrings.add(databaseInputs2);
        inputStrings.add(databaseInputs3);
        inputStrings.add(databaseInputs4);
        Iterator<String[]> iterator = inputStrings.iterator();
        while (iterator.hasNext()) {
            String[] databaseInput = iterator.next();
            int key = databaseFoo.save(databaseInput);
            String[] loadedOutput = databaseFoo.load(key);
            assertThat(loadedOutput,equalTo(databaseInput));
        }
    }
}
