package com.foo;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/3/12
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CalculatorDao {
    int getCurrentMaxKey();

    int save(String[] databaseInputs);

    String[] load(int key);

    void createTable();
}
