package com.example.swagger.serialization;

import java.io.Serializable;

public class student implements Serializable {

    int num;
    String name;

    public student(int n, String name){
        this.num = n;
        this.name = name;
    }


}
