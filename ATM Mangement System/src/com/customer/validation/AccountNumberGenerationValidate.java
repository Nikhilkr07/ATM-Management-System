package com.customer.validation;

public class AccountNumberGenerationValidate {
    public long accNoValidate(long accountNo){
        String s = accountNo+"";
        if(s.length() < 9){
            if (s.length() == 8){
                s+=1;
            } else if (s.length() == 7) {
                s+=22;
            }
            else if (s.length() == 6) {
                s+=333;
            }
        }
        long accNo = Long.parseLong(s);
        return accNo;
    }

    public long atmNoValidate(long atmNo){
        String s = atmNo+"";
        if(s.length() < 8){
            if (s.length() == 7){
                s+=1;
            } else if (s.length() == 6) {
                s+=22;
            }
            else if (s.length() == 5) {
                s+=333;
            }
        }
        long num = Long.parseLong(s);
        return num;
    }
}
