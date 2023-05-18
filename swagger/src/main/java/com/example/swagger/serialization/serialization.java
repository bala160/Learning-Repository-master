package com.example.swagger.serialization;

import java.io.*;

public class serialization {
    public static void main(String[] a) throws IOException {

        student s = new student(12, "Bala");

        FileOutputStream out = new FileOutputStream("C:\\Users\\Balakrishnan\\Documents\\serialization.txt");
        ObjectOutputStream o = new ObjectOutputStream(out);

        o.writeObject(s);
        o.flush();
        System.out.print("Success");
    }

}
