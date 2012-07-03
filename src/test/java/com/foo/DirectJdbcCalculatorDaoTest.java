package com.foo;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/21/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class DirectJdbcCalculatorDaoTest extends AbstractCalculatorDaoTest {

    protected DirectJdbcCalculatorDao getDaoInstance() {
        return new DirectJdbcCalculatorDao();
    }
}
