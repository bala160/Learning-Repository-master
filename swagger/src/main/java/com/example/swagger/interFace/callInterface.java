package com.example.swagger.interFace;

public class callInterface {
    public static void main(String[] args) {
        RidableInterface i = () -> System.out.println("Vehical riding");
        i.call();
        i.ride();
    }
}
