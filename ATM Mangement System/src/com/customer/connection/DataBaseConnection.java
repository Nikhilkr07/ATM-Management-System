package com.customer.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {

    String url = "jdbc:mysql://localhost:3306/Atm";
    String userName = "root";
    String password = "Nikhil@123";


    public Connection getCon()throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,userName,password);
//        System.out.println("connection sucess!!");
        return con;
    }

}
