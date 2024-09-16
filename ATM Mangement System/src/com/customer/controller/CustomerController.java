package com.customer.controller;

import com.customer.dto.AtmCardDto;
import com.customer.dto.CustomerDto;
import com.customer.service.CustomerService;
import com.customer.service.CustomerServiceImpl;
import com.customer.vo.AtmCardVo;
import com.customer.vo.CustomerVo;

import java.sql.Date;

public class CustomerController {
    CustomerService customerService;
    public String processCustomer(CustomerVo customerVo) throws Exception{

        CustomerDto customerDto = null;
        long accNo = Long.parseLong(customerVo.getAccountNo());
        String ifcsCode = customerVo.getIfscCode();
        String name = customerVo.getName();
        String dob = customerVo.getDob();
        String mobile = customerVo.getMobile();
        String address = customerVo.getAddress();
        String pincode = customerVo.getPinCode();
        String accountType = customerVo.getAccountType();
        double totalAmount = Double.parseDouble(customerVo.getTotalAmount());

        customerDto = new CustomerDto();

        customerDto.setAccountNo(accNo);
        customerDto.setIfscCode(ifcsCode);
        customerDto.setName(name);
        customerDto.setDob(dob);
        customerDto.setMobile(mobile);
        customerDto.setAddress(address);
        customerDto.setPinCode(pincode);
        customerDto.setAccountType(accountType);
        customerDto.setTotalAmount(totalAmount);

        customerService = new CustomerServiceImpl();
        String s = customerService.addAcount(customerDto);

        return s;
    }

    public String atmCardProcess(AtmCardVo vo) throws Exception{
        AtmCardDto dto = new AtmCardDto();
        long accountNo = Long.parseLong(vo.getAccountNo());
        long atmNo = Long.parseLong(vo.getAtmNo());
        int cusId = Integer.parseInt(vo.getCusId());
        int cvv = Integer.parseInt(vo.getCvv());
        int atmpin = Integer.parseInt(vo.getAtmPin());
        double totalAmount = Double.parseDouble(vo.getTotalAmount());

        dto.setAccountNo(accountNo);
        dto.setAtmNo(atmNo);
        dto.setName(vo.getName());
        dto.setAtmType(vo.getAtmType());
        dto.setCusId(cusId);
        dto.setCvv(cvv);
        dto.setAtmPin(atmpin);
        dto.setTotalAmount(totalAmount);

        customerService = new CustomerServiceImpl();
        String s = customerService.addAtmCard(dto);

        return s;
    }
}
