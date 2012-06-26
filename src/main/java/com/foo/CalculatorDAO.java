package com.foo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/19/12
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorDAO {
    private static final Logger LOG = LoggerFactory.getLogger(CalculatorDAO.class);
    public static final int RESULT_SIZE = 4;
    private String typeOfDAO;
    private String username;
    private String password;

    public CalculatorDAO(){
        this("jdbc:h2:mem:Foo;DB_CLOSE_DELAY=-1","sa","sa");
    }

    public CalculatorDAO(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        typeOfDAO =  "jdbc:h2:mem:Foo;DB_CLOSE_DELAY=-1";
        this.username = username;
        this.password = password;
    }

    public CalculatorDAO(String typeOfDAO, String username, String password) {
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

    private int getCurrentMaxKey() {
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


    public int save(String[] databaseInputs) {
        int insertKey = getCurrentMaxKey() + 1;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO CalculatorDatabase VALUES (?,?,?,?,?)");
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

    public void createTable() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS CalculatorDatabase (key INTEGER, Operator VARCHAR(30), Operand1 INTEGER, Operand2 INTEGER, date VARCHAR(30))");
            statement.execute();

        } catch (SQLException ex) {
             LOG.error("There was a problem accessing the database!!!", ex);
        }

    }

}
