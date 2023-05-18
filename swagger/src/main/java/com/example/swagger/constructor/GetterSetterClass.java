package com.example.swagger.constructor;

public class GetterSetterClass {
    private String name;

    GetterSetterClass(String name){
        this.name = name;
        System.out.println("Constructor called :"+ name );
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
