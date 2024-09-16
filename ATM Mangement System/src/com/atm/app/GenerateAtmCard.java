package com.atm.app;

import com.customer.controller.CustomerController;
import com.customer.transaction.AtmTransaction;
import com.customer.transaction.AtmTransactionImpl;
import com.customer.validation.AccountNumberGenerationValidate;
import com.customer.validation.AtmValidation;
import com.customer.validation.CustomerValidate;
import com.customer.validation.CustomerValidateImpl;
import com.customer.vo.AtmCardVo;

import java.util.Random;
import java.util.Scanner;

public class GenerateAtmCard {
    public void atmGeneration(){
        AccountNumberGenerationValidate generationValidate = new AccountNumberGenerationValidate();
        AtmValidation atmValidation = new AtmValidation();
        AtmTransaction atmTransaction = new AtmTransactionImpl();
        CustomerValidate validate = new CustomerValidateImpl();
        Scanner sc = new Scanner(System.in);
        String name,atmType;
        int cusId,atmPin,cvv;
        long accountNo,atmNo;
        double totalAmount = 0;
        Random rad = new Random();
        atmNo = rad.nextLong(100000000);
        atmNo = generationValidate.atmNoValidate(atmNo);
        atmPin = rad.nextInt(10000);
        cvv = rad.nextInt(1000);

        System.out.println("----please fill information----");
        System.out.print("Enter your accountNo: ");
        accountNo = sc.nextLong();
        try {
            atmValidation.validateAccountNo(accountNo);
            atmValidation.atmCardExistOrNot(accountNo);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        System.out.print("Enter your CusId: ");
        cusId = sc.nextInt();
        try {
            validate.validateCusId(cusId,accountNo);
        }catch (Exception e){
            e.printStackTrace();
        }
        sc.nextLine(); // Consumes the leftover newline ("using this
        //only for resolve the problem of nexint to another sting input)
        System.out.print("Enter your Name: ");
        name = sc.nextLine();
        try {
            atmValidation.validateName(name,accountNo);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("------Atm type------");
        System.out.println("1.Debit\n2.Credit");
        int atmTypeOpt = sc.nextInt();
        if(atmTypeOpt == 1){
            atmType = "Debit";
        }
        else{
            atmType = "Credit";
        }
        // add amount from account to atm
        try {
            totalAmount = atmTransaction.getAmount(accountNo,cusId);
        }catch (Exception e){
            e.printStackTrace();
        }

        AtmCardVo vo = new AtmCardVo();
        vo.setAccountNo(accountNo+"");
        vo.setAtmNo(atmNo+"");
        vo.setAtmPin(atmPin+"");
        vo.setName(name);
        vo.setCusId(cusId+"");
        vo.setCvv(cvv+"");
        vo.setAtmType(atmType);
        vo.setTotalAmount(totalAmount+"");

        CustomerController customerController = new CustomerController();
        try {
            String s = customerController.atmCardProcess(vo);
            System.out.println(s);
        }catch (Exception e){
            System.out.println("Something wrong!");
            e.printStackTrace();
        }
        System.out.println("Please note Your ATM Number: "+atmNo);
        System.out.println("Please not your one time atmPin: "+atmPin);
        System.out.println("cvv "+cvv);
    }
}
