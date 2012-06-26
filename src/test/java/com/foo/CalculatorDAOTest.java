package com.foo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/21/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorDAOTest {
    CalculatorDAO databaseFoo = new CalculatorDAO("sa","sa");

    @Before
    public void setUp() {
        databaseFoo.createTable();
    }

    @Test
    public void confirmUsernameAndPassword() {
        String[] userInfo = new String[2];
        userInfo[0] = databaseFoo.getUsername();
        userInfo[1] = databaseFoo.getPassword();
        String[] actualInfo = {"sa","sa"};
        assertThat(userInfo,equalTo(actualInfo));
    }

    @Test
    public void emptyPasswordThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        CalculatorDAO databaseFoo = new CalculatorDAO("sa","");
    }

    @Test
    public void emptyUsernameThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        new CalculatorDAO("","sa");
    }

    @Test
    public void emptyUsernamePasswordThrowsException() {
        expectedException.expect(IllegalArgumentException.class);
        new CalculatorDAO("","");
    }

    @Test
    public void emptyStringThrowsExceptionInSave() {
        expectedException.expect(IllegalArgumentException.class);
        String[] databaseInputs = {"","","",""};
        int key = databaseFoo.save(databaseInputs);
    }

    @Test
    public void successfulSave() {
        String[] databaseInputs = {"ADD","1","2","3/6/2012"};
        int key = databaseFoo.save(databaseInputs);
        String[] loadedOutputs = databaseFoo.load(key);
        assertThat(loadedOutputs, equalTo(databaseInputs));
    }

    @Test
    public void successfulDuplicateSaves(){
        String[] databaseInputs1 = {"ADD","1","2","3/6/2012"};
        String[] databaseInputs2 = {"ADD","1","2","3/6/2012"};
        databaseFoo.save(databaseInputs1);
        int key = databaseFoo.save(databaseInputs2);
        String[] loadedOutput = databaseFoo.load(key);
        assertThat(loadedOutput, equalTo(databaseInputs2));
    }

    @Test
    public void severalSuccessfulUniqueSaves() {
        ArrayList<String[]> inputStrings = new ArrayList<String[]>();
        String[] databaseInputs1 = {"ADD","5","9","3/6/1954"};
        String[] databaseInputs2 = {"ADD","3","4","4/7/1944"};
        String[] databaseInputs3 = {"ADD","2","3","4/5/1895"};
        String[] databaseInputs4 = {"ADD","1","2","12/7/1955"};
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
