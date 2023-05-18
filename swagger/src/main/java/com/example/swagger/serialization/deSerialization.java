package com.example.swagger.serialization;

import java.io.*;

public class deSerialization {
    public static void main(String[] a) throws IOException, ClassNotFoundException {

        ObjectInputStream o = new ObjectInputStream(new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\serialization.txt"));
        student s = (student)o.readObject();
        System.out.print(s.num+" "+s.name);
    }
}
