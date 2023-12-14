package com.softmax.features;

public class SwitchFeature {


    /**
     * 旧写法
     *
     * @param i
     */
    private static void foods(int i) {
        switch (i) {
            case 1:
                System.err.println("青椒肉丝");
                break;
            case 2:
                System.err.println("番茄炒蛋");
                break;
            default:
                System.err.println("米饭");
        }
    }

    /**
     * 新写法
     *
     * @param i
     */
    private static void fruits(int i) {
        switch (i) {
            case 1 -> System.err.println("香蕉");
            case 2 -> System.err.println("猕猴桃");
            default -> System.err.println("苹果");
        }
    }




}
