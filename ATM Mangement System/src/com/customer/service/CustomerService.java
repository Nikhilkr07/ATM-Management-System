package com.customer.service;

import com.customer.dto.AtmCardDto;
import com.customer.dto.CustomerDto;

public interface CustomerService {
    public String addAcount(CustomerDto customerDto) throws Exception;
    public String addAtmCard(AtmCardDto atmCardDto) throws Exception;
}
