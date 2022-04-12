package com.softmax.basic.datastructure.sample;

import java.io.IOException;

/**
 * 栈的顺序存储
 *
 * @author Jarvis
 */
public class MyStack {
    private Object data[];
    private int top;
    private static final int maxSize = 10;

    public MyStack() {
        this.data = new Object[maxSize];
        this.top = -1;
    }

    public MyStack(int size) {
        this.data = new Object[size];
        this.top = -1;
    }

    //入栈操作
    public void push(MyStack p, Object value) {
        if (p.top == data.length - 1) {
            System.out.println("堆栈满");
        } else {
            p.data[++p.top] = value;
        }
    }

    //出栈操作
    public Object pop(MyStack p) throws Exception {
        Object value = 0;
        if (p.top == -1) {
            throw new IOException("堆栈空");
        } else {
            value = p.data[p.top--];
        }
        return value;
    }

    public static void main(String[] args) throws Exception {
        MyStack ms = new MyStack();
        ms.push(ms, 10);
        Object ob = ms.pop(ms);
        System.out.println(ob);
    }
}
