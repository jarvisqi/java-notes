package com.softmax.basic.datastructure.sample;

public class LRUNode {
    private String key;
    private Object value;
    /**
     * 指向上一个节点
     */
    private LRUNode pre;
    /**
     * 指向下个节点
     */
    private LRUNode next;

    public LRUNode(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public LRUNode getPre() {
        return pre;
    }

    public void setPre(LRUNode pre) {
        this.pre = pre;
    }

    public LRUNode getNext() {
        return next;
    }

    public void setNext(LRUNode next) {
        this.next = next;
    }
}
