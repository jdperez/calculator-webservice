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
    public static void main(String[] args) throws SQLException {
        Server server = Server.createTcpServer().start();
        Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/test", "sa", "");
        try{
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from foo");
            while(resultSet.next()){
                System.out.println(resultSet.getString("Name"));
            }
        }
        finally {
            server.stop();
            connection.close();
        }
    }
}
