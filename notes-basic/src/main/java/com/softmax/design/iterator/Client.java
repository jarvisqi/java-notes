package com.softmax.design.iterator;

/**
 * 迭代器模式 以首尾相接的环形结构解决空间有限的问题
 * 对于任何的集合类，既要保证内部数据表示不暴露给外部以防搞乱内部机制，还要提供给用户遍历并访问到每个数据的权限，
 * 迭代器模式则成就了鱼与熊掌兼得的可能，它提供了所有集合对外开放的统一标准接口，内政容不得干涉，但是经济依旧要开放。
 */
public class Client {
    public static void main(String[] args) {
        DrivingRecorder dr = new DrivingRecorder();
        for (int i = 0; i < 12; i++) {
            dr.append("视频_" + i);
        }
        dr.display();

        dr.displayInOrder();
    }
}
