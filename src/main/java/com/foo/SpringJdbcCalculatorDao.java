package com.foo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/3/12
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class SpringJdbcCalculatorDao implements CalculatorDao {
    private JdbcOperations jdbcOperations;

    @Autowired
    public SpringJdbcCalculatorDao(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public int getCurrentMaxKey() {
        return jdbcOperations.queryForInt("SELECT max(key) FROM CalculatorDatabase");
    }

    @Override
    public int save(String[] databaseInputs) {
        for (int i = 0; i < databaseInputs.length; i++) {
            String databaseInput = databaseInputs[i];
            if (databaseInput.equals("")) {
                throw new IllegalArgumentException("Empty operand in save");
            }
        }
        int currentKey = getCurrentMaxKey() +1;
        Object[] statementParameters = new Object[4];
        System.arraycopy(databaseInputs,0,statementParameters,1,databaseInputs.length);
        statementParameters[0] = currentKey;
        jdbcOperations.update("INSERT INTO CalculatorDatabase VALUES (?,?,?,?)", statementParameters);
        return currentKey;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String[] load(int key) {
        String[] result = jdbcOperations.queryForObject("SELECT * FROM CalculatorDatabase Where key=?", new Object[]{key}, new RowMapper<String[]>() {
            @Override
            public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new String[]{rs.getString(2), rs.getString(3), rs.getString(4)};
            }
        });
        return result;
    }

    @Override
    public void createTable() {
        jdbcOperations.execute("CREATE TABLE IF NOT EXISTS CalculatorDatabase (key INTEGER, Operator VARCHAR(30), Operand1 DECIMAL, Operand2 DECIMAL)");
    }

    @Override
    public int save(Calculation calculation) {
        //TODO:Write Me!
        throw new UnsupportedOperationException("Method not written-com.foo.SpringJdbcCalculatorDao.save");
    }
}
