package Centus;

import java.sql.*;
public class MysqlCon {

    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/mydb","admin","12345678"
        );

            System.out.println("Connected to database");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect");

        }
    }
}
