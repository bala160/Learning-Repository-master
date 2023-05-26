package com.example.swagger.interFace;

public interface RidableInterface {

    void ride();
    default void call(){
        System.out.println("Inside Interface called");
    }
}

