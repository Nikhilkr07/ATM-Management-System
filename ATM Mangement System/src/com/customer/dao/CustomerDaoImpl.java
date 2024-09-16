package com.customer.dao;

import com.customer.bo.AtmCardBo;
import com.customer.bo.CustomerBo;
import com.customer.bo.TransactionDetailsBo;
import com.customer.connection.DataBaseConnection;

import java.sql.*;
import java.util.Calendar;

public class CustomerDaoImpl implements CustomerDao{
    DataBaseConnection connection = new DataBaseConnection();
    @Override
    public int customerRegister(CustomerBo bo) throws Exception {
        final String query = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        // Account Creation Date
        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
        Connection con = connection.getCon();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,0);
        ps.setLong(2,bo.getAccountNo());
        ps.setString(3,bo.getIfscCode());
        ps.setString(4,bo.getAccountType());
        ps.setString(5,bo.getName());
        ps.setString(6,bo.getDob());
        ps.setString(7,bo.getMobile());
        ps.setString(8,bo.getAddress());
        ps.setString(9,bo.getPinCode());
        ps.setDouble(10,bo.getTotalAmount());
        ps.setDate(11,date);

        int res = ps.executeUpdate();

        String cusIdQuery = "select cusId from customer where accountNo = ?";
        PreparedStatement pscusId = con.prepareStatement(cusIdQuery);
        pscusId.setLong(1,bo.getAccountNo());
        ResultSet resultSet = pscusId.executeQuery();
        if (resultSet.next()){
            System.out.println("Please NOTE YOUR CUS-ID: "+resultSet.getInt(1));
        }
        return res;
    }

    @Override
    public int customerAtmCreation(AtmCardBo bo) throws Exception {
        String insertQuery = "INSERT INTO customerAtmCard VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        Connection con = connection.getCon();

        long millis=System.currentTimeMillis();
        Date validFrom = new Date(millis);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(validFrom);
        // Add 5 years to the creation date
        calendar.add(Calendar.YEAR, 5);
        Date validUpto = new Date(calendar.getTimeInMillis());

        PreparedStatement ps = con.prepareStatement(insertQuery);
        //By default bank is SBI
        String bank = "SBI";

        ps.setInt(1,bo.getCusId());
        ps.setString(2,bank);
        ps.setLong(3,bo.getAccountNo());
        ps.setLong(4,bo.getAtmNo());
        ps.setInt(5,bo.getCvv());
        ps.setString(6,bo.getAtmType());
        ps.setString(7,bo.getName());
        ps.setDate(8,validFrom);
        ps.setDate(9,validUpto);
        ps.setInt(10,bo.getAtmPin());
        ps.setDouble(11,bo.getTotalAmount());

        int res = ps.executeUpdate();
        return res;
    }

    public int transactionUpdate(TransactionDetailsBo bo)throws Exception{

        return 0;
    }
}
