package com.softmax.design.intermediary;

public class People {
    /**
     * //用名字来区别人。
     */
    private String name;
    /**
     * //持有对方的引用。
     */
    private People other;

    public String getName() {
        return this.name;
    }

    /**
     * //初始化必须起名。
     *
     * @param name
     */
    public People(String name) {
        this.name = name;
    }

    /**
     * 连接方法中注入对方引用。
     *
     * @param other
     */
    public void connect(People other) {
        this.other = other;
    }

    /**
     * 我方说话时，对方聆听。
     *
     * @param msg
     */
    public void talk(String msg) {
        other.listen(msg);
    }

    public void listen(String msg) {
        //聆听来自对方的声音
        System.out.println(other.getName() + " 对 " + this.name + " 说：" + msg);
    }
}