package com.atm.app;

import com.customer.bo.TransactionDetailsBo;
import com.customer.connection.DataBaseConnection;
import com.customer.transaction.BalanceInquery;
import com.customer.transaction.Banking;
import com.customer.transaction.TransferAmount;
import com.customer.validation.AtmValidation;

import java.util.Scanner;

public class TransactionProcess {
    AtmValidation validation = new AtmValidation();
    public boolean atmBanking(TransactionDetailsBo bo){
        long atmNo;
        int pin,num,cusId;
        Scanner sc = new Scanner(System.in);
        System.out.println("------punch atmCard--------");
        System.out.println("------Enter 8 Digit ATM NUMBER--------");
        atmNo = sc.nextLong();
        try {
            validation.validateAtmNumber(atmNo);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        System.out.print("Enter the CusId: ");
        cusId = sc.nextInt();
        try {
            validation.validateCusId(cusId,atmNo);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        System.out.println("Enter Number between 10 to 99");
        num = sc.nextInt();
        if(!(num >= 10 && num <= 99)){
            System.out.println("Enter Valid Number");
            return false;
        }
        System.out.println("Enter You four Digit Pin");
        pin = sc.nextInt();
        try {
            validation.validateAtmPin(pin,atmNo);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        bo.setAtmNo(atmNo);
        bo.setCusId(cusId);

        return true;
    }

    public void transactionOptions(TransactionDetailsBo bo){
        Banking banking = new Banking();
        TransferAmount transfer = new TransferAmount();
        BalanceInquery balanceInquery = new BalanceInquery();
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("----Enter Your Choice------");
            System.out.println("1.Banking\n2.Balance Inquery\n3.Transfer\n4.Cancel Transaction");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    int bankingOpt;
                    System.out.println("---------------------------------");
                    System.out.println("1.Credit");
                    System.out.println("2.Debit");
                    System.out.println("3.Change pin");
                    System.out.println("4.Transactions Details");
                    System.out.println("5.cancle Transaction");
                    bankingOpt = sc.nextInt();
                    if(bankingOpt == 1){
                        // check saving or current account;
                        System.out.println("-----Account Type------");
                        System.out.println("1.Saving\n2.Current");
                        int accType = sc.nextInt();
                        if(accType == 1){
                            try {
//                                System.out.println(bo.getAtmNo()+" "+bo.getCusId());
                               boolean b = validation.checkSavingOrCurrent(bo.getAtmNo(), bo.getCusId(),"Saving");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else if(accType == 2){
                            try {
                                validation.checkSavingOrCurrent(bo.getAtmNo(), bo.getCusId(),"Current");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            System.out.println("Enter valid choice:");
                            return;
                        }
                        System.out.print("Enter your Pin: ");
                        int pin = sc.nextInt();
                        try {
                            validation.validateAtmPin(pin,bo.getAtmNo());
                        }catch (Exception e){
                            e.printStackTrace();
                            return;
                        }
                        System.out.println("Enter Amount to Credit: ");
                        double amount = sc.nextDouble();
                        // set details first in transaction bo
                        bo.setTransactionType("deposite");
                        bo.setTransactionAmount(amount);
                        try {
                           String s = banking.addAmount(bo.getAtmNo(),pin,bo.getCusId(),amount);
                            System.out.println(s);
                        }catch (Exception e){
                            e.printStackTrace();
                            return;
                        }

                    }
                    else if(bankingOpt == 2){
                        System.out.println("-----Account Type------");
                        System.out.println("1.Saving\n2.Current");
                        int accType = sc.nextInt();
                        if(accType == 1){
                            try {
//                                System.out.println(bo.getAtmNo()+" "+bo.getCusId());
                                boolean b = validation.checkSavingOrCurrent(bo.getAtmNo(), bo.getCusId(),"Saving");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else if(accType == 2){
                            try {
                                validation.checkSavingOrCurrent(bo.getAtmNo(), bo.getCusId(),"Current");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            System.out.println("Enter valid choice:");
                            return;
                        }
                        System.out.println("Enter your Pin: ");
                        int pin = sc.nextInt();
                        try {
                            validation.validateAtmPin(pin,bo.getAtmNo());
                        }catch (Exception e){
                            e.printStackTrace();
                            return;
                        }
                        System.out.println("Enter Amount to Debit: ");
                        double amount = sc.nextDouble();

                        // set details first in transaction bo
                        bo.setTransactionType("wtihdraw");
                        bo.setTransactionAmount(amount);
                        try {
                           String s= banking.withdrawAmount(bo.getAtmNo(),pin,bo.getCusId(),amount);
                            System.out.println(s);
                        }catch (Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }
                    else if(bankingOpt == 3){
                        System.out.print("Enter Your Pin:");
                        int pin = sc.nextInt();
                        try {
                            validation.validateAtmPin(pin,bo.getAtmNo());
                        }catch (Exception e){
                            e.printStackTrace();
                            return;
                        }
                        try {
                           String s = banking.changePin(bo.getAtmNo(),pin);
                            System.out.println(s);
                        }catch (Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }
                    else if(bankingOpt == 4){
                        System.out.print("Enter your Pin: ");
                        int pin = sc.nextInt();
                        try {
                            validation.validateAtmPin(pin,bo.getAtmNo());
                        }catch (Exception e){
                            e.printStackTrace();
                            return;
                        }
                        System.out.println("hi");
                        try{
                            String s = banking.transactionDetails(bo.getAtmNo(),pin);
                            System.out.println(s);
                        }catch (Exception e){
                            e.printStackTrace();
                            return;
                        }
                    }
                    else{
                        System.out.println("Transaction cancel thanku!");
                        System.exit(0);
                    }
                    break;
                case 2:
                    System.out.print("Enter Your pin: ");
                    int pin = sc.nextInt();
                    try {
                        validation.validateAtmPin(pin,bo.getAtmNo());
                    }catch (Exception e){
                        e.printStackTrace();
                        return;
                    }
                    try {
                        double balance = balanceInquery.getAtmAmount(pin,bo.getAtmNo());
                    }catch (Exception e){
                        e.printStackTrace();
                        return;
                    }
                    break;
                case 3:
                    System.out.print("Enter your pin: ");
                    int pin1 = sc.nextInt();
                    long senderAccNo,recieverAccNO;
                    senderAccNo = bo.getAtmNo();
                    try {
                        validation.validateAtmPin(pin1,senderAccNo);
                    }catch (Exception e){
                        e.printStackTrace();
                        return;
                    }
                    System.out.print("Enter reciever AtmNo: ");
                    recieverAccNO = sc.nextLong();
                    try {
                        validation.validateAtmNumber(recieverAccNO);
                    }catch (Exception e){
                        System.out.println("reciver AtmNo is Wrong!");
                        e.printStackTrace();
                        return;
                    }
                    System.out.print("Enter Sending amount: ");
                    double amount = sc.nextDouble();
                    try {
                        String s = transfer.transferAmount(senderAccNo,pin1,recieverAccNO,amount);
                        System.out.println(s);
                    }catch (Exception e){
                        e.printStackTrace();
                        return;
                    }

                    break;
                case 4:
                    System.exit(0);

            }
        }
    }
}
