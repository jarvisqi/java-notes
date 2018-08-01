package com.mq.rabbit.tutorial.tutorial06;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * rpc 服务端
 *
 * @author Jarvis
 * @date 2018/8/1
 */
public class Tut6Server {

    @RabbitListener(queues = "tut6.rpc.requests")
    public int process(int in) {
        System.out.println(" [x] Received request for " + in);
        int result = fib(in);
        System.out.println(" [.] Returned " + result);
        return result;
    }

    /**
     * 斐波那契数
     *
     * @param i
     * @return
     */
    private int fib(int i) {
        return (i == 0 || i == 1) ? i : (fib(i - 2) + fib(i - 1));
    }

}
