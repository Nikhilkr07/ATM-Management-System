package com.customer.validation;

import com.customer.connection.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerValidateImpl implements CustomerValidate{
    DataBaseConnection connection = new DataBaseConnection();
    @Override
    public void validateName(String name) throws Exception{
        if(name.length() == 0){
            throw new Exception("valid name1");
        }
        for(int i = 0; i < name.length(); i++){
            char c = name.charAt(i);
            if(!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == ' ')){
                throw new Exception("valid name2");
            }
        }

    }

    @Override
    public void validatePincode(String pincode)throws Exception {
        if(pincode.length() != 6){
            throw new Exception("valid pincode");
        }
        for(int i = 0; i < pincode.length(); i++){
            if(!Character.isDigit(pincode.charAt(i))){
                throw new Exception("valid pincode");
            }
        }

    }

    @Override
    public void validateMobile(String mobile) throws Exception{
        if(mobile.length() != 10){
            throw new Exception("valid mobile");
        }
        for(int i = 0; i < mobile.length(); i++){
            if(!Character.isDigit(mobile.charAt(i))){
                throw new Exception("valid mobile");
            }
        }

    }

    public void validateCusId(int cusId, long accountNo)throws Exception{
        Connection con = connection.getCon();
        String query = "SELECT cusId FROM customer WHERE accountNo = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1,accountNo);

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
}
