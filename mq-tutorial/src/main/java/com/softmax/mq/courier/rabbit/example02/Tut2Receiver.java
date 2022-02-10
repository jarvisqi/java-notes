package com.softmax.mq.courier.rabbit.example02;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * 发布订阅 - 消费者
 *
 * @author Jarvis
 * @date 2018/7/31
 */
@RabbitListener(queues = "works-queues")
public class Tut2Receiver {

    private int instance;

    public Tut2Receiver(int instance) {
        this.instance = instance;
    }

    @RabbitHandler
    public void receiver(String inMsg) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + this.instance +
                " [x] Received '" + inMsg + "'");
        doWork(inMsg);
        watch.stop();
        System.out.println("instance " + this.instance +
                " [x] Done in " + watch.getTotalTimeSeconds() + "s");

    }

    private void doWork(String inMsg) throws InterruptedException {
        for (char ch : inMsg.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
//
//    /**
//     * 更改 特定消费者
//     * 然后在特定的消费者上指定 containerFactory 为自定义的bean, prefetchTenRabbitListenerContainerFactory
//     *
//     * @param in
//     */
//    @RabbitListener(queues = "hello", containerFactory = "prefetchTenRabbitListenerContainerFactory")
//    public void receive(String in) {
//        System.out.println(" [x] Received '" + in + "'");
//    }
}
