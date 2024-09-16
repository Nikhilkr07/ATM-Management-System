package com.atm.app;

import com.customer.controller.CustomerController;
import com.customer.validation.AccountNumberGenerationValidate;
import com.customer.validation.CustomerValidate;
import com.customer.validation.CustomerValidateImpl;
import com.customer.vo.CustomerVo;

import java.util.Random;
import java.util.Scanner;

public class CreateAccount {
    AccountNumberGenerationValidate generationValidate = new AccountNumberGenerationValidate();
    public void accountCreation(){
        Scanner sc = new Scanner(System.in);
        CustomerValidate validate = new CustomerValidateImpl();
            String ifsc,accno,name,dob,mobile,address,pincode,accountType,totalAmount;
            int accountTypeOption;
            ifsc = "SBI10006";
            Random rad = new Random();
            accno = rad.nextLong(1000000000)+"";
            accno = generationValidate.accNoValidate(Long.parseLong(accno))+"";
            System.out.println("Enter your account Type: ");

            System.out.println("1.Saving \n2.Current");
            accountTypeOption = sc.nextInt();
            if(accountTypeOption == 1){
                accountType = "Saving";
            }
            else if(accountTypeOption == 2){
                accountType = "current";
            }
            else {
                System.out.println("Enter valid accountType");
                return;
            }

            sc.nextLine(); // Consumes the leftover newline ("using this
//        only for resolve the problem of nexint to another sting input)

            System.out.print("Enter your name: ");
            name = sc.nextLine();

            try {
                validate.validateName(name);
            }catch (Exception e){
                System.out.println("something wrong!");
                e.printStackTrace();
            }
            System.out.print("Enter dob (dd//mm//yyyy): ");
            dob = sc.nextLine();

            System.out.print("Enter mobile: ");
            mobile = sc.nextLine();
            try {
                validate.validateMobile(mobile);
            }catch (Exception e){
                System.out.println("something wrong!");
                e.printStackTrace();
            }

            System.out.print("Enter address full: ");
            address = sc.nextLine();

            System.out.print("Enter pincode: ");
            pincode = sc.nextLine();
            try {
                validate.validatePincode(pincode);
            }catch (Exception e){
                System.out.println("something wrong!");
                e.printStackTrace();
            }
            System.out.print("Enter Amount atleast 500: ");
            totalAmount = sc.nextLine();
            double amount = Double.parseDouble(totalAmount);
            if(amount < 500.0){
                System.out.println("NOT a Sufficient amount: ");
                return;
            }

            CustomerVo vo = new CustomerVo();
            vo.setAccountNo(accno);
            vo.setIfscCode(ifsc);
            vo.setAccountType(accountType);
            vo.setName(name);
            vo.setDob(dob);
            vo.setMobile(mobile);
            vo.setAddress(address);
            vo.setPinCode(pincode);
            vo.setTotalAmount(totalAmount);

            CustomerController controller = new CustomerController();
            try {
                String status = controller.processCustomer(vo);
                System.out.println(status+"\nAccount number please note: "+accno);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
}
