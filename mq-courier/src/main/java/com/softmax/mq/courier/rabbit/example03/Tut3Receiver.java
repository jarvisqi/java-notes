package com.softmax.mq.courier.rabbit.example03;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * 发布订阅 - 消费者
 * 定义两个消费者分别监听两个队列
 *
 * @author Jarvis
 * @date 2018/7/31
 */
public class Tut3Receiver {

    @RabbitListener(queues = "#{autoQueue1.name}")
    public void receiver1(String in) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{autoQueue2.name}")
    public void receiver2(String in) throws InterruptedException {
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
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
