package com.customer.transaction;

import com.customer.connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AtmTransactionImpl implements AtmTransaction{
    DataBaseConnection connection = new DataBaseConnection();
    @Override
    public double getAmount(long accountNo, int cusId) throws Exception {
        Connection con = connection.getCon();
        String query = "SELECT totalAmount FROM customer WHERE accountNo = ? AND cusId = ?";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1,accountNo);
        ps.setInt(2,cusId);

        ResultSet res = ps.executeQuery();
        double totalAmount;
        if(res.next()){
            totalAmount = res.getDouble(1);
            System.out.println(totalAmount);
        }
        else {
            throw new Exception("Something wrong!");
        }
        return totalAmount;
    }

}
