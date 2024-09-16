package com.customer.transaction;

public interface AtmTransaction {
    public double getAmount(long accountNo, int cusId) throws Exception;
}
