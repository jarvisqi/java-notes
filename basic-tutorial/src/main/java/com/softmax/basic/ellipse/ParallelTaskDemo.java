package com.softmax.basic.ellipse;

import java.util.concurrent.CompletableFuture;

public class ParallelTaskDemo {

    public static void main(String[] args) {

        ParallelTaskDemo futureOpt = new ParallelTaskDemo();
//        Instant start = Instant.now();
//        for (int i = 0; i < 1000; i++) {
//            futureOpt.useSerial();
//        }
//        Instant finish = Instant.now();
//        long timeElapsed = Duration.between(start, finish).toMillis();
//        System.out.println("串行执行耗时：" + timeElapsed / 1000 + "s");
//
//        Instant start1 = Instant.now();
//        for (int i = 0; i < 1000; i++) {
//            futureOpt.useParallel();
//        }
//
//        Instant finish1 = Instant.now();
//        long timeElapsed1 = Duration.between(start1, finish1).toMillis();
//        System.out.println("并行执行耗时：" + timeElapsed1 / 1000 + "s");

        futureOpt.nonResult();


    }


    public void nonResult() {
        CompletableFuture drt = CompletableFuture.runAsync(() -> this.calculateDis("Apple"));
        CompletableFuture dhw = CompletableFuture.runAsync(() -> this.calculateAge("Apple"));
        CompletableFuture.allOf(drt, dhw).join();
    }

    public void useSerial() {
        Double rate = this.calculateRate("Apple");
        Double price = this.calculatePrice("Apple");
        Info info = new Info(price, rate);
    }

    /**
     * 并行执行
     */
    public void useParallel() {
        CompletableFuture<Double> price = CompletableFuture.supplyAsync(() -> this.calculatePrice("Apple"));
        CompletableFuture<Double> rate = CompletableFuture.supplyAsync(() -> this.calculateRate("Apple"));
        CompletableFuture<Info> infoCompletableFuture = price.thenCombine(rate, (p, r) -> new Info(p, r));
        Info join = infoCompletableFuture.join();
    }

    private Double calculatePrice(String product) {
        Double value = 0.0;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (product) {
            case "Apple":
                value = 10.0;
                break;
            case "Banana":
                value = 5.0;
                break;
            default:
                break;
        }
        return value;
    }

    private Double calculateRate(String product) {
        Double value = 0.0;
        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (product) {
            case "Apple":
                value = 0.8;
                break;
            case "Banana":
                value = 0.5;
                break;
            default:
                break;
        }
        return value;
    }


    private void calculateDis(String product) {
        Double value = 0.0;
        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (product) {
            case "Apple":
                value = 0.8;
                break;
            case "Banana":
                value = 0.5;
                break;
            default:
                break;
        }
        System.out.println(value);
    }

    private void calculateAge(String product) {
        Double value = 0.0;
        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (product) {
            case "Apple":
                value = 0.8;
                break;
            case "Banana":
                value = 0.5;
                break;
            default:
                break;
        }
        System.out.println(value);
    }


    static class Info {
        private Double price;
        private Double rate;

        public Info(Double price, Double rate) {
            this.price = price;
            this.rate = rate;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "price=" + price +
                    ", rate=" + rate +
                    '}';
        }
    }

}
