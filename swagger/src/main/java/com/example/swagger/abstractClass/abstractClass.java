package com.example.swagger.abstractClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

abstract class abstractClass {
    abstract void employee();
}

class sam extends abstractClass {
    void employee() {
        System.out.print("Developer");
    }
}

class main{
    public static void main(String[] args) {
        abstractClass a = new sam();
        //a.employee();
        List<String> data = new ArrayList<>();
        int i = 0, j = 2;

        do {

            i=++i;
            //System.out.println(i);
            j--;

        } while (j>0);

        System.out.println  (i);

    }
}