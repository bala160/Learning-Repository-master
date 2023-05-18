package com.example.swagger.annonymousClass;

class cycle {
    void display() {
        System.out.println("Cycle");
    }
}

public class annonymsClass {
    public static void main(String[] args) {

        //anonyms class to override the same method.
        cycle c = new cycle() {
            void display() {
                System.out.println("Tricycle");
            }
        };
        c.display();
    }
}
