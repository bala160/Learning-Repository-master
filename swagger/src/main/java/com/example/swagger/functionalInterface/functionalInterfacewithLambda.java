package com.example.swagger.functionalInterface;

/*
interface have only one abstract method is called Functional Interfacce
 */
interface funct {
    void meth();//abstract method
}

public class functionalInterfacewithLambda{


    public static void main(String[] args) {

        funct i = () -> System.out.println("success");//lambda expression using only for funactional interface
        i.meth();
    }
}
