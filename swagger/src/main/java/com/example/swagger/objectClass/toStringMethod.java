package com.example.swagger.objectClass;

public class toStringMethod {

    int num1;
    String name1;

    toStringMethod(int num, String name) {
        num1 = num;
        name1 = name;
    }

    public String toString(){
        return "Num: "+ num1 + " "+"Name: "+ name1 ;
    }
    public static void main(String[] args) {
        toStringMethod s = new toStringMethod(1, "Bala");
        System.out.println(s);
    }
}
