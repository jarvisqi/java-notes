package com.softmax.basic.ellipse;

public class KrigingDemo {


    public static void main(String[] args) {

        int CPU_COUNT = Runtime.getRuntime().availableProcessors();
        System.out.println(Math.max(2, Math.min(CPU_COUNT - 1, 4)));

    }

}
