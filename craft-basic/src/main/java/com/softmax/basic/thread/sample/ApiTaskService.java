package com.softmax.basic.thread.sample;

/**
 * @author Jarvis
 */
public class ApiTaskService {

    public Good getTaobao() throws InterruptedException {
        Thread.sleep(1);
        return new Good("淘宝", 10);
    }

    public Good getJdong() throws InterruptedException {
        Thread.sleep(2);
        return new Good("京东", 30);
    }

    public Good getPin() throws InterruptedException {
        Thread.sleep(1);

        return new Good("拼夕夕", 15);
    }

    class Good {

        String name;
        int price;

        public Good(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
