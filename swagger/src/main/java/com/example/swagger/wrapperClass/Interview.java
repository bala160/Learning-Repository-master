package com.example.swagger.wrapperClass;

import java.util.Arrays;

public class Interview {

    public static void main(String[] args) {
        String name = "balakrishnan weds suchitra";
        String[] arr = name.split(" ");
        Arrays.stream(arr)
                .forEach(e -> System.out.print(e.toUpperCase().charAt(0)+ e.substring(1)+" "));


    }
}
