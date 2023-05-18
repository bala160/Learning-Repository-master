package com.example.swagger.exceptionHandling;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class tryCatch {

    public static void main(String[] args) throws FileNotFoundException {
        int a = 10,b = 0;
        int c = 0;

        try{
            c = a/b;

        }catch (Exception e){
            System.err.println(e);
        }
        int bal = 3000,withdraw = 6000;
        try {
            if(bal<withdraw){
                throw new InsufficientFundException(withdraw-bal);//throw
            }
        }catch (InsufficientFundException e){
            System.err.print("Not Enough Money");
        }
        FileOutputStream fs = new FileOutputStream("bala.txt");// throws
        System.out.print(c);
    }
}
