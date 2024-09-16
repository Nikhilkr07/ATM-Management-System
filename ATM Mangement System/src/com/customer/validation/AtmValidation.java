package com.customer.validation;

import com.customer.connection.DataBaseConnection;
import com.mysql.cj.util.StringInspector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AtmValidation {
    DataBaseConnection connection = new DataBaseConnection();
    public void validateName(String name, long accountNo)throws Exception{
        Connection con = connection.getCon();
        String query = "SELECT cusName FROM customer WHERE accountNo = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1,accountNo);

        ResultSet res = ps.executeQuery();

        if(res.next()){
            String check = res.getString(1);
//            System.out.println(check+"  "+name);
            if(check.equals(name)) {
                System.out.println("name Matched");
            }
            else{
                throw new Exception("name Not Matched");
            }
        }
        else{
            throw new Exception("Name Not Matched");
        }
    }

    // check Account Exist or Not
    public void atmCardExistOrNot(long accountNo)throws Exception{
        Connection con = connection.getCon();
        String query = "SELECT accountNo FROM customerAtmCard WHERE accountNo = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1,accountNo);

        ResultSet res = ps.executeQuery();

        if(res.next()){
            throw new Exception("You Have Already Atm Card.");
        }


    }

    public void validateAccountNo(long accountNo)throws Exception{
        Connection con = connection.getCon();
        String query = "SELECT accountNo FROM customer WHERE accountNo = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1,accountNo);

        ResultSet res = ps.executeQuery();

        if(res.next()){
            System.out.println("Account No Matched");
        }
        else{
            throw new Exception("Enter valid accountNo");
        }

    }

    public void validateAtmNumber(long atmNo) throws Exception{
        Connection con = connection.getCon();
        String query = "SELECT atmNo FROM customerAtmCard WHERE atmNo = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1,atmNo);

        ResultSet res = ps.executeQuery();

        if(res.next()){
            System.out.println("AtmNumber Matched");
        }
        else{
            throw new Exception("Enter valid AtmNo");
        }
    }

    public void validateAtmPin(int pin, long atmNo) throws Exception{
        Connection con = connection.getCon();
        String query = "SELECT atmPin FROM customerAtmCard WHERE atmNo = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1,atmNo);

        ResultSet res = ps.executeQuery();

        if(res.next()){
            int check = res.getInt(1);
            if(check == pin) {
                System.out.println("Correct pin");
            }
            else{
                throw new Exception("pin Not Matched");
            }
        }
        else{
            throw new Exception("pin Not Matched");
        }
    }

    public void validateCusId(int cusId, long atmNo)throws Exception{
        Connection con = connection.getCon();
        String query = "SELECT cusId FROM customerAtmCard WHERE atmNo = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1,atmNo);

        ResultSet res = ps.executeQuery();

        if(res.next()){
            int check = res.getInt(1);
            if(check == cusId) {
                System.out.println("CusId Matched");
            }
            else{
                throw new Exception("CusId Not Matched");
            }
        }
        else{
            throw new Exception("CusId Not Matched");
        }

    }

    public boolean checkSavingOrCurrent(long atmNo,int cusId,String accountType) throws Exception{
        long accountNo;
        Connection con = connection.getCon();
        String query = "SELECT accountNo FROM customerAtmCard WHERE atmNo = ? AND cusId = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1,atmNo);
        ps.setInt(2,cusId);
        ResultSet res = ps.executeQuery();
        if(res.next()){
            accountNo = res.getLong(1);
//            System.out.println("mil gya "+accountNo);
            String query1 = "SELECT accountType FROM customer WHERE accountNo = ? AND cusId = ?";
            PreparedStatement ps1 = con.prepareStatement(query1);
            ps1.setLong(1,accountNo);
            ps1.setInt(2,cusId);
            ResultSet res1 = ps1.executeQuery();
            if(res1.next()){
                String s = res1.getString(1);
//                System.out.println(s);
//                System.out.println(accountType);
                if(s.equals(accountType)){
                    return true;
                }
                else {
                    throw new Exception("Account Type mismatched");
                }
            }
            else {
                throw new Exception("Account Type mismatched!!!!!");
            }
        }
        else {
            throw new Exception("Account Type mismatched wiht atmNO!");
        }

    }

}
