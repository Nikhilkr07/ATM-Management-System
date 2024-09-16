package com.customer.dto;

import java.sql.Date;

public class AtmCardDto {
    private String Name,atmType;
    private long atmNo,accountNo;
    private int cvv,cusId,atmPin;
    private double totalAmount;

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public int getAtmPin() {
        return atmPin;
    }

    public void setAtmPin(int atmPin) {
        this.atmPin = atmPin;
    }

    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAtmType() {
        return atmType;
    }

    public void setAtmType(String atmType) {
        this.atmType = atmType;
    }

    public long getAtmNo() {
        return atmNo;
    }

    public void setAtmNo(long atmNo) {
        this.atmNo = atmNo;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

}
