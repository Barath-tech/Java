package JDBC.src;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyJDBC {
    public static void main(String[] args) {    
    try{

        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "password");
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select * from people");

        while(resultSet.next()) {
            System.out.println(resultSet.getString("firstName"));
        }
    }catch(Exception e){
        e.printStackTrace();
    }
}
}
