package com.softmax.netty.example;

/**
 * @author Jarvis
 * @date 2019/3/18
 */
public class Run {

    public static void main(String[] args) throws InterruptedException {
        HttpServer server = new HttpServer(3002);
        server.start();
    }
}
