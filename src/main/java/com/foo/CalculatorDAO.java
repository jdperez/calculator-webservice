package com.foo;

import org.h2.tools.Server;
import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 6/19/12
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorDAO {
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
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT key FROM CalculatorDatabase ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int currentKey = resultSet.getInt(1);
                if (currentKey > maxKey) maxKey = currentKey;
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return maxKey;
    }

    public int save(String[] databaseInputs) {
        int insertKey = getCurrentMaxKey() + 1;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CalculatorDatabase VALUES (?,?,?,?,?)");

            preparedStatement.setInt(1,insertKey);
            for (int i = 0; i < databaseInputs.length; i++) {
                preparedStatement.setString(i+2,databaseInputs[i]);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return insertKey;
    }

    public String[] load(int key) {
        String[] resultData = new String[4];
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CalculatorDatabase Where key=?");
            preparedStatement.setInt(1,key);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            for (int i = 0; i < resultData.length; i++) {
                resultData[i] = resultSet.getString(i + 2);
            }
            return resultData;
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage());
        }
        return new String[0];
    }

    public void createTable() {
         try {
            Connection connection = getConnection();
             String createString = "CREATE TABLE IF NOT EXISTS CalculatorDatabase (key INTEGER, Operator VARCHAR(30), Operand1 INTEGER, Operand2 INTEGER, date VARCHAR(30))";
             Statement statement = connection.createStatement();
            statement.executeUpdate(createString);
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
