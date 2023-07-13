package com.softmax.mq.courier.rabbit.example05;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * @author Jarvis
 * @date 2018/7/31
 */
public class Tut5Receiver {

    @RabbitListener(queues = {"#{autoQueue1}"})
    public void recevier1(String in) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = {"#{autoQueue2}"})
    public void recevier2(String in) throws InterruptedException {
        receive(in, 2);
    }


    private void receive(String in, int instance) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + instance + " [x] Received '" + in + "'");
        doWork(in);
        watch.stop();
        System.out.println("instance " + instance + " [x] Done in "
                + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        for (char c : in.toCharArray()) {
            if (c == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
