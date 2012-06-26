package com.foo;

import org.h2.tools.Server;
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
    private String typeOfDAO;
    private String username;
    private String password;

    public CalculatorDAO(String typeOfDAO, String username, String password) {
        this.typeOfDAO = typeOfDAO;
        this.username = username;
        this.password = password;
    }

    public CalculatorDAO(String username, String password) {
        this.username = username;
        this.password = password;
        typeOfDAO =  "jdbc:h2:mem:Foo;DB_CLOSE_DELAY=-1";
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(typeOfDAO, username, password);
        return connection;
    }

    public int getCurrentMaxKey() {
        int maxKey = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT key FROM CalculatorDatabase ");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int currentKey = resultSet.getInt(1);
                if (currentKey > maxKey) {
                    maxKey = currentKey;
                }
            }

        } catch (SQLException e) {
            LOG.error("there was a problem accessing the database", e);
        }
        finally {
            try{
            preparedStatement.close();
            resultSet.close();
            } catch (SQLException ex) {
                LOG.error("there was a problem closing the connection", ex);
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

            preparedStatement.setInt(1,insertKey);
            for (int i = 0; i < databaseInputs.length; i++) {
                preparedStatement.setString(i+2,databaseInputs[i]);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            LOG.error("there was a problem accessing the database", e);
        }
        finally {
            try{
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                LOG.error("there was a problem closing the connection", ex);
            }
        }
        LOG.error("current key # is: ", insertKey);
        return insertKey;
    }

    public String[] load(int key) {
        String[] resultData = new String[4];
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM CalculatorDatabase Where key=?");
            preparedStatement.setInt(1,key);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            for (int i = 0; i < resultData.length; i++) {
                resultData[i] = resultSet.getString(i + 2);
            }
            return resultData;
        } catch (SQLException e){
            LOG.error("there was a problem accessing the database", e);
        }
        finally {
            try{
                preparedStatement.close();
                connection.close();
                resultSet.close();
            } catch (SQLException ex) {
                LOG.error("there was a problem closing the connection", ex);
            }
        }
        return new String[0];
    }

    public void createTable() {
        Connection connection = null;
        Statement statement = null;
         try {
            connection = getConnection();
             String createString = "CREATE TABLE IF NOT EXISTS CalculatorDatabase (key INTEGER, Operator VARCHAR(30), Operand1 INTEGER, Operand2 INTEGER, date VARCHAR(30))";
             statement = connection.createStatement();
            statement.executeUpdate(createString);
        } catch (SQLException ex) {
             LOG.error("There was a problem accessing the database", ex);
        }

         finally {
             try{
                 connection.close();
                 statement.close();
             } catch (SQLException ex) {
                 LOG.error("there was a problem closing the connection", ex);
             }
         }

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
