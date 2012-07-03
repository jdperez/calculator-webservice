package com.foo;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}
