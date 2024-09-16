package com.customer.bo;

public class TransactionDetailsBo {
    private int cusId;
    private long atmNo;
    private String transactionType;
    private double transactionAmount, remainingBalance;

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        System.out.println(cusId+"---");
        this.cusId = cusId;
    }

    public long getAtmNo() {
        return atmNo;
    }

    public void setAtmNo(long atmNo) {
        System.out.println(atmNo+"---");
        this.atmNo = atmNo;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    /*transactionId INT PRIMARY KEY AUTO_INCREMENT,
    cusId INT,
    atmNo BIGINT,
    transactionType VARCHAR(10),  -- Example: 'withdraw', 'deposit'
    transactionAmount DOUBLE,
    transactionDate DATETIME DEFAULT NOW(),
    remainingBalance DOUBLE,
    FOREIGN KEY (cusId) REFERENCES customer(cusId),
    FOREIGN KEY (atmNo) REFERENCES customerAtmCard(atmNo)*/
}
