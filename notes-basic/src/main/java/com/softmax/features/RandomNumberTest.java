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
public class RandomNumberTest {

    public static void main(String[] args) {

        // Random 缺陷，高并发情况下，可能使用同一个种子数字去生成下一个随机数
        // 可能会导致生成的数字相同
        for (int i = 0; i < 10; i++) {
            Random random1 = new Random(15);
            System.out.println(random1.nextInt(100));
        }

        testRandomGenerator(new Random());
        testRandomGenerator(new SplittableRandom());
        testRandomGenerator(ThreadLocalRandom.current());


        testRandomGeneratorFactory("Random");
        testRandomGeneratorFactory("L128X128MixRandom");
        testRandomGeneratorFactory("Xoshiro256PlusPlus");

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

    /**
     * 上述方法还支持如下LXM 系列 PRNG 算法：
     * L32X64MixRandom
     * L32X64StarStarRandom
     * L64X128MixRandom
     * L64X128StarStarRandom
     * L64X256MixRandom
     * L64X1024MixRandom
     * L128X128MixRandom
     * L128X256MixRandom
     * L128X1024MixRandom
     * 以及广泛使用的 PRNG 算法：Xoshiro256PlusPlus Xoroshiro128PlusPlus
     *
     * @param randomGeneratorName
     */

    static void testRandomGeneratorFactory(String randomGeneratorName) {
        RandomGeneratorFactory<RandomGenerator> factory = RandomGeneratorFactory.of(randomGeneratorName);
        // 使用时间戳作为随机数种子
        RandomGenerator randomGenerator = factory.create(System.currentTimeMillis());

        // 生成随机数
        var nextInt = randomGenerator.nextInt(10);
        System.out.println(nextInt);
    }

}
