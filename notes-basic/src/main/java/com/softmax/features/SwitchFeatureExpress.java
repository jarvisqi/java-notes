package com.softmax.features;

public class SwitchFeatureExpress {

    /**
     * 单表达式
     */
    private static String fruits(int i) {
        return switch (i) {
            case 1 -> "香蕉";
            case 2 -> "猕猴桃";
            default -> "苹果";
        };
    }

    /**
     * 多表达式
     */
    private static String fruitsNew(int i) {
        return switch (i) {
            case 1, 2 -> {
                System.err.println("----------------------------------");
                System.err.println("来一个香蕉");
                yield "香蕉来咯";
            }
            case 3, 4 -> {
                System.err.println("----------------------------------");
                System.err.println("来一个猕猴桃");
                yield "猕猴桃来咯";
            }
            default -> {
                System.err.println("----------------------------------");
                System.err.println("没的选就来个苹果吧");
                yield "苹果来咯";
            }
        };
    }

    public static void main(String[] args) {

        System.err.println(fruitsNew(2));
        System.err.println(fruitsNew(4));
        System.err.println(fruitsNew(5));
    }
}
