package com.example.swagger.interFace;

import java.sql.SQLOutput;

public class interfaceProgram implements met, met1 {

    @Override
    public void met() {
        int num = 1;
        System.out.println("Success------->" + num);
    }

    public void met1() {
        System.out.println("Success2");
    }


    public static void main(String[] args) {
        interfaceProgram m = new interfaceProgram();
        m.met();
        m.met1();
    }
}
