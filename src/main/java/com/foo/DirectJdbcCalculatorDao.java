package com.foo;

import org.slf4j.LoggerFactory;

import java.sql.*;
/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/19/12
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */

public class DirectJdbcCalculatorDao implements CalculatorDao {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DirectJdbcCalculatorDao.class);
    public static final int RESULT_SIZE = 3;
    private String typeOfDAO;
    private String username;
    private String password;

    public DirectJdbcCalculatorDao(){
        this("jdbc:h2:mem:Foo;DB_CLOSE_DELAY=-1","sa","sa");
    }

    public DirectJdbcCalculatorDao(String username, String password) {
        this("jdbc:h2:mem:Foo;DB_CLOSE_DELAY=-1", username, password);
    }

    public DirectJdbcCalculatorDao(String typeOfDAO, String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        this.typeOfDAO = typeOfDAO;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(typeOfDAO, username, password);
    }

    @Override
    public int getCurrentMaxKey() {
        int maxKey = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT key FROM CalculatorDatabase ");
            resultSet = preparedStatement.executeQuery();
            maxKey = findHighestKey(resultSet);

        } catch (SQLException e) {
            LOG.error("there was a problem accessing the database", e);
        }
        return maxKey;
    }

    private int findHighestKey(ResultSet resultSet) throws SQLException {
        int maxKey = 0;
        while(resultSet.next()) {
            int currentKey = resultSet.getInt(1);
            if (currentKey > maxKey) {
                maxKey = currentKey;
            }
        }
        return maxKey;
    }

    @Override
    public int save(String[] databaseInputs) {
        int insertKey = getCurrentMaxKey() + 1;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO CalculatorDatabase VALUES (?,?,?,?)");
            runStatements(databaseInputs, insertKey, preparedStatement);
        } catch (SQLException e) {
            LOG.error("there was a problem accessing the database", e);
        }
        LOG.error("current key # is: ", insertKey);
        return insertKey;
    }

    private void runStatements(String[] databaseInputs, int insertKey, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1,insertKey);
        for (int i = 0; i < databaseInputs.length; i++) {
            if (databaseInputs[i] == null || databaseInputs[i].trim().isEmpty()) {
               throw new IllegalArgumentException("trying to pass empty string into save");
            }
            preparedStatement.setString(i+2,databaseInputs[i]);
        }
        preparedStatement.execute();
    }

    @Override
    public String[] load(int key) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = prepareTheStatement(key, connection);
            resultSet = preparedStatementIterate(preparedStatement);
            return runAndReturnData(resultSet);
        } catch (SQLException e){
            LOG.error("there was a problem accessing the database", e);
        }
        return new String[0];
    }

    @Override
    public Calculation loadCalculation(int key) {
        //TODO:Write Me!
        throw new UnsupportedOperationException("Method not written-com.foo.DirectJdbcCalculatorDao.loadCalculation");
    }

    private PreparedStatement prepareTheStatement(int key, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CalculatorDatabase Where key=?");
        preparedStatement.setInt(1,key);
        return preparedStatement;
    }

    private ResultSet preparedStatementIterate(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet;
    }

    private String[] runAndReturnData(ResultSet resultSet) throws SQLException {
        String[] resultData = new String[RESULT_SIZE];
        for (int i = 0; i < resultData.length; i++) {
            resultData[i] = resultSet.getString(i + 2);
        }
        return resultData;
    }

    @Override
    public void createTable() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS CalculatorDatabase (key INTEGER, Operator VARCHAR(30), Operand1 DECIMAL, Operand2 DECIMAL)");
            statement.execute();

        } catch (SQLException ex) {
             LOG.error("There was a problem accessing the database", ex);
        }

    }

    @Override
    public Calculations loadAll() {
        //TODO:Write Me!
        throw new UnsupportedOperationException("Method not written-com.foo.DirectJdbcCalculatorDao.loadAll");
    }

    @Override
    public int save(Calculation calculation) {
        //TODO:Write Me!
        throw new UnsupportedOperationException("Method not written-com.foo.DirectJdbcCalculatorDao.save");
    }
}
