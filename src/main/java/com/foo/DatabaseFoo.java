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
public class DatabaseFoo {
    int insertCount = 0;

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/test", "sa", "");
        try{
            //createTable(connection);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from CalculatorEntries");
            while(resultSet.next()){
                System.out.println(resultSet.getString("Name"));
            }



        }
        finally {
            //server.stop();
            connection.close();
        }
    }

    public static Connection getConnection() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:Foo;DB_CLOSE_DELAY=-1", "sa", "");
        return connection;
    }


    public int save(String[] databaseInputs) {
        insertCount++;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CalculatorDatabase VALUES (?,?,?,?,?)");
            preparedStatement.setInt(1,insertCount);
            for (int i = 0; i < databaseInputs.length; i++) {
                preparedStatement.setString(i+2,databaseInputs[i]);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return insertCount;  //To change body of created methods use File | Settings | File Templates.
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

        return new String[0];  //To change body of created methods use File | Settings | File Templates.
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
        //To change body of created methods use File | Settings | File Templates.
    }

}
