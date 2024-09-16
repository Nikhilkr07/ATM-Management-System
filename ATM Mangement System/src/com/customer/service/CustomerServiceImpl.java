package com.customer.service;

import com.customer.bo.AtmCardBo;
import com.customer.bo.CustomerBo;
import com.customer.dao.CustomerDao;
import com.customer.dao.CustomerDaoImpl;
import com.customer.dto.AtmCardDto;
import com.customer.dto.CustomerDto;

public class CustomerServiceImpl implements  CustomerService{
    CustomerDao customerDao;
    @Override
    public String addAcount(CustomerDto customerDto) throws Exception {

        CustomerBo customerBo = new CustomerBo();
        customerBo.setAccountNo(customerDto.getAccountNo());
        customerBo.setIfscCode(customerDto.getIfscCode());
        customerBo.setName(customerDto.getName());
        customerBo.setDob(customerDto.getDob());
        customerBo.setMobile(customerDto.getMobile());
        customerBo.setAddress(customerDto.getAddress());
        customerBo.setPinCode(customerDto.getPinCode());
        customerBo.setAccountType(customerDto.getAccountType());
        customerBo.setTotalAmount(customerDto.getTotalAmount());

        customerDao = new CustomerDaoImpl();
        int row = customerDao.customerRegister(customerBo);

        if(row > 0) {
            return "Account Created Successfully !";
        }
        else {
            return "Account not created !";
        }
    }

    @Override
    public String addAtmCard(AtmCardDto atmCardDto) throws Exception {

        AtmCardBo bo = new AtmCardBo();
        bo.setCusId(atmCardDto.getCusId());
        bo.setAccountNo(atmCardDto.getAccountNo());
        bo.setAtmNo(atmCardDto.getAtmNo());
        bo.setName(atmCardDto.getName());
        bo.setCvv(atmCardDto.getCvv());
        bo.setAtmType(atmCardDto.getAtmType());
        bo.setAtmPin(atmCardDto.getAtmPin());
        bo.setTotalAmount(atmCardDto.getTotalAmount());
        customerDao = new CustomerDaoImpl();
        int row = customerDao.customerAtmCreation(bo);

        if(row > 0) {
            return "atm card generated Successfully !";
        }
        else {
            return "atm not generated !";
        }
    }
}
