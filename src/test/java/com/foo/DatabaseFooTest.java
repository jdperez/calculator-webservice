package com.foo;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/21/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseFooTest {
    DatabaseFoo databaseFoo = new DatabaseFoo();

    @Before
    public void setUp(){
        //databaseFoo.startDatabaseFooServer();
        //databaseFoo.getConnection();
        databaseFoo.createTable();
    }

    @Test
    public void successfulLoad(){
        String[] databaseInputs = {"ADD","1","2","3/6/2012"};
        int key = databaseFoo.save(databaseInputs);
        String[] loadedOutputs = databaseFoo.load(key);
        assertThat(loadedOutputs, equalTo(databaseInputs));

    }


}
