package com.softmax.features;

import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;

/**
 * Java 17 新接口 RandomGenerator
 */
public class RandomTest {

    public static void main(String[] args) {

        testRandomGenerator(new Random());

        testRandomGenerator(new SplittableRandom());

        testRandomGenerator(ThreadLocalRandom.current());


//        testRandomGeneratorFactory("Random");
//
//        testRandomGeneratorFactory("L128X128MixRandom");
//
//        testRandomGeneratorFactory("Xoshiro256PlusPlus");

    }


    /**
     * RandomGenerators 提供名为 ints、longs、doubles、nextBoolean、nextInt、nextLong、nextDouble 和 nextFloat 的方法
     *
     * @param randomGenerator
     */
    static void testRandomGenerator(RandomGenerator randomGenerator) {
        IntStream ints = randomGenerator.ints(50, 0, 10);
        int[] randoms = ints.toArray();
        for (int i : randoms) {
            System.out.println(i);
        }
        System.out.println("random count = " + randoms.length);
    }


    static void testRandomGeneratorFactory(String randomGeneratorName) {
        RandomGeneratorFactory<RandomGenerator> factory = RandomGeneratorFactory.of(randomGeneratorName);
        // 使用时间戳作为随机数种子
        RandomGenerator randomGenerator = factory.create(System.currentTimeMillis());

        // 生成随机数
        var nextInt = randomGenerator.nextInt(10);
        System.out.println(nextInt);
    }

}
