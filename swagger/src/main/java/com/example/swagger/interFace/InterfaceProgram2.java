package com.example.swagger.interFace;

interface vehicle {
    void run();

    default void display() {
        System.out.println("Success");
    }

}

class car implements vehicle {
    public void run() {
        System.out.println("Car");
    }
}

class bike implements vehicle {
    public void run() {
        System.out.println("Bike");
    }
}

class Mechanic {
    void run(vehicle b) {
        b.run();
    }
}

public class InterfaceProgram2 {
    public static void main(String[] args) {
        vehicle v = new car();
        Mechanic m = new Mechanic();
        bike b = new bike();
        car c = new car();
        v.display();
        m.run(b);
        m.run(c);
    }
}
