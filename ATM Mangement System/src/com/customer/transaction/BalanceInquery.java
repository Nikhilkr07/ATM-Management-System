package com.customer.transaction;

import com.customer.connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BalanceInquery {
    DataBaseConnection connection = new DataBaseConnection();
    public double getAtmAmount(int pin, long atmNo) throws Exception {
        Connection con = connection.getCon();

        String query = "SELECT cusName, atmNo, totalAmount FROM customerAtmCard WHERE atmNo = ? AND atmPin = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, atmNo);
        ps.setInt(2, pin);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String name = rs.getString("cusName");
            long atm = rs.getLong("atmNo");
            double balance = rs.getDouble("totalAmount");

            System.out.println("Name: " + name + ", ATM No: " + atm + ", Balance: " + balance);
            return balance;
        } else {
            throw new Exception("Invalid ATM number or PIN.");
        }
    }

}
