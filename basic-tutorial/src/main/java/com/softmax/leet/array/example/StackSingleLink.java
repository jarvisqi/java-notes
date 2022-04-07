package com.softmax.leet.array.example;

/**
 * 用单向链表实现栈
 *
 * @author Jarvis
 */
public class StackSingleLink {
    private SingleLinkedList link;

    public StackSingleLink() {
        link = new SingleLinkedList();
    }

    /**
     * 添加元素
     *
     * @param obj
     */
    public void push(Object obj) {
        link.addHead(obj);
    }

    /**
     * 移除栈顶元素
     *
     * @return
     */
    public Object pop() {
        Object obj = link.deleteHead();
        return obj;
    }

    /**
     * 判断是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return link.isEmpty();
    }

    /**
     * 打印栈内元素信息
     */
    public void display() {
        link.display();
    }

    public static void main(String[] args) {
        StackSingleLink stackSingleLink = new StackSingleLink();
        stackSingleLink.push("AAA");
        stackSingleLink.push("BBB");
        stackSingleLink.push("CCC");

        stackSingleLink.display();

    }
}
