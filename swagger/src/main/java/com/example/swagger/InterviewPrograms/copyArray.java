package com.example.swagger.InterviewPrograms;

public class copyArray {
    public static void main(String[] a){
        int[] arr = {1,2,3,4,5};
        copyArray c = new copyArray();
        c.method(arr);
    }

    void method(int[] a){
        int[] dup = new int[a.length];
        for(int i = 0;i<a.length;i++){
            dup[i] = a[i];
        }

        for(int i = 0;i<dup.length;i++) {

        }
        System.out.print(dup[2]);
    }
}
