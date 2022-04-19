package com.softmax.design.iterator;

public class Client {
    public static void main(String[] args) {
        DrivingRecorder dr = new DrivingRecorder();
        for (int i = 0; i < 12; i++) {
            dr.append("视频_" + i);
        }
        dr.display();

        dr.displayInOrder();
    }
}
