package com.softmax.basic.thread.sample;

import org.apache.commons.beanutils.converters.IntegerConverter;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class ApiCompletableFuture {

    public static void serialTask() throws InterruptedException {
        Instant start = Instant.now();
        ApiTaskService service = new ApiTaskService();
        ApiTaskService.Good tb = service.getTaobao();
        ApiTaskService.Good jd = service.getJdong();
        ApiTaskService.Good pin = service.getPin();
        Stream.of(tb, jd, pin).min(Comparator.comparingInt(ApiTaskService.Good::getPrice)).get();
        Instant finish = Instant.now();
        long total = Duration.between(start, finish).toMillis();

        System.out.println("Serial execution time: " + total);
    }


    public static void futureTask() throws InterruptedException {
        Instant start = Instant.now();
        ApiTaskService service = new ApiTaskService();
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        Future<ApiTaskService.Good> tb = executorService.submit(service::getTaobao);
        Future<ApiTaskService.Good> jd = executorService.submit(service::getJdong);
        Future<ApiTaskService.Good> pin = executorService.submit(service::getPin);

        Stream.of(tb, jd, pin).map(priceResult -> {
            try {
                return priceResult.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException();
            }
        }).filter(Objects::nonNull).min(Comparator.comparingInt(ApiTaskService.Good::getPrice)).get();

        Instant finish = Instant.now();
        long total = Duration.between(start, finish).toMillis();
        executorService.shutdownNow();
        System.out.println("ThreadPool execution time: " + total);
    }

    public static void main(String[] args) throws InterruptedException {

//        serialTask();

        futureTask();
    }

}
