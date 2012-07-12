package com.foo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
            if (("").equals(databaseInput)) {
                throw new IllegalArgumentException("Empty operand in save");
            }
        }
        int currentKey = getCurrentMaxKey() +1;
        Object[] statementParameters = new Object[4];
        System.arraycopy(databaseInputs,0,statementParameters,1,databaseInputs.length);
        statementParameters[0] = currentKey;
        jdbcOperations.update("INSERT INTO CalculatorDatabase VALUES (?,?,?,?)", statementParameters);
        return currentKey;
    }

    @Override
    public int save(Calculation calculation) {
        //TODO: Not storing operation currently
        if (calculation.getOperand1() == null || calculation.getOperand2() == null) {
            throw new IllegalArgumentException("Empty operand in save");
        }
        Object[] statementParameters = new Object[4];
        Integer currentKey = getCurrentMaxKey()+1;
        statementParameters[0] = currentKey;
        //TODO: THIS IS BOLLUCKS
        statementParameters[1] = "DIVIDE";
        statementParameters[2] = calculation.getOperand1();
        statementParameters[3] = calculation.getOperand2();
        jdbcOperations.update("INSERT INTO CalculatorDatabase VALUES (?,?,?,?)", statementParameters);
        return currentKey;
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
    public Calculation loadCalculation(int key) {
        Calculation result = jdbcOperations.queryForObject("SELECT * FROM CalculatorDatabase Where key=?", new Object[]{key}, new RowMapper<Calculation>() {
            @Override
            public Calculation mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Calculation(rs.getInt(3),rs.getInt(4));
            }
        });
        return result;
    }

    @Override
    public Calculations loadAll() {
        List<Calculation> calculationList = jdbcOperations.query("SELECT * FROM CalculatorDatabase", new RowMapper() {
            @Override
            public Calculation mapRow(ResultSet rs, int rowNum) throws SQLException {
                Calculation calculation = new Calculation();
                calculation.setOperand1(rs.getInt(3));
                calculation.setOperand2(rs.getInt(4));
                return calculation;
            }
        });
        Calculations calculations = new Calculations();
        calculations.setCalculations(calculationList);
        return calculations;
    }

    @Override
    public void createTable() {
        jdbcOperations.execute("CREATE TABLE IF NOT EXISTS CalculatorDatabase (key INTEGER, Operator VARCHAR(30), Operand1 DECIMAL, Operand2 DECIMAL)");
    }
}
