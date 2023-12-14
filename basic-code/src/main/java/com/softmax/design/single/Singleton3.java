package com.softmax.design.single;

/**
 * 嵌套类
 */
class Singleton3 {

    private Singleton3() {
    }

    /**
     * 主要是使用了 嵌套类可以访问外部类的静态属性和静态方法 的特性
     */
    private static class Holder {
        private static final Singleton3 instance = new Singleton3();
    }

    public static Singleton3 getInstance() {
        return Holder.instance;
    }
}
