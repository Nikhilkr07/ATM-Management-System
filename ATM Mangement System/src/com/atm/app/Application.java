package com.atm.app;

import com.customer.bo.TransactionDetailsBo;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        CreateAccount createAccount = new CreateAccount();
        GenerateAtmCard generateAtmCard = new GenerateAtmCard();
        TransactionProcess transactionProcess = new TransactionProcess();
        TransactionDetailsBo transactionDetailsBo = new TransactionDetailsBo();
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("--------------Enter Your Choice----------");
        System.out.println("1.Proceed Transaction\n2.Cancel");
        choice = sc.nextInt();
        while (true) {
            switch (choice) {
                case 1:
                int haveAccOpt;
                System.out.println("-------Enter Your Choice:--------");
                System.out.println("1.You have Account\n2.Apply For Account Creation");
                haveAccOpt = sc.nextInt();
                if (haveAccOpt == 1) {
                    System.out.println("-------Enter your Choice------");
                    System.out.println("1.You have ATM Card");
                    System.out.println("2.Apply ATM Card");
                    int atmOpt = sc.nextInt();
                    if (atmOpt == 1) {
                        boolean b = transactionProcess.atmBanking(transactionDetailsBo);
                        if(b){
                            transactionProcess.transactionOptions(transactionDetailsBo);
                        }
                        else {
                            return;
                        }
                    }
                    else {
                        generateAtmCard.atmGeneration();
                    }
                }
                else {
                    createAccount.accountCreation();
                }
                break;

                case 2:
                    System.exit(0);

            }

        }

    }
}
