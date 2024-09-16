package com.customer.validation;

public interface CustomerValidate {
    public void validateName(String name) throws Exception;
    public void validatePincode(String pincode) throws Exception;
    public void validateMobile(String mobile) throws Exception;
    public void validateCusId(int cusId, long accountNo)throws Exception;
}
