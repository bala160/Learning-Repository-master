package com.example.swagger.wrapperClass;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HasMapP {
    public static void main(String[] args) {
        HashMap<Integer,String> data = new HashMap<>();
        data.put(1,"Java");
        data.put(2,"MYSQL");

        data.forEach((k,v) -> System.out.println(k+" "+v));
    }
}
