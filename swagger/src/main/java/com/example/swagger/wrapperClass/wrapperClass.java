package com.example.swagger.wrapperClass;

public class wrapperClass {
    public static void main(String[] args) {

        int i = 10; //primitive

        Integer i1 = Integer.valueOf(i); //wrapping, (boxing)
        int res = i1.intValue(); //unboxing

        System.out.println(res);

        Integer i3 = 10; //complier automatically compiled into ----- Integer.valueOf(i)  //this is called autoboxing

        int res1 = i3; //complier automatically compiled into ----- i3.intValue()  //this is called autounboxing
    }
}
