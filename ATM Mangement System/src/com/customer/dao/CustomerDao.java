package com.customer.dao;

import com.customer.bo.AtmCardBo;
import com.customer.bo.CustomerBo;

import java.sql.Connection;

public interface CustomerDao {
    public int customerRegister(CustomerBo bo) throws Exception;
    public int customerAtmCreation(AtmCardBo bo) throws Exception;
}
