package com.example.swagger.constructor;

public class constructor {
    /**
     * Special method
     * Same name as class name
     * No return type
     * Immediately calling when an object is created
     * used for initialize values
     * <p>
     * constructor not have static method
     */
    public static void main(String[] args) {
        GetterSetterClass g = new GetterSetterClass("Bala");
        g.setName("Krish");
        System.out.println(g.getName());
    }
}
