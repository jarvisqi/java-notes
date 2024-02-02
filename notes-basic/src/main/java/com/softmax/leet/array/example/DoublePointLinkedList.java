package com.softmax.leet.array.example;

/**
 * 双端链表
 *
 * @author Jarvis
 */
public class DoublePointLinkedList {
    /**
     * 头节点
     */
    private Node head;
    /**
     * 尾节点
     */
    private Node tail;
    /**
     * 节点的个数
     */
    private int size;

    private class Node {
        private Object data;
        private Node next;

        public Node(Object data) {
            this.data = data;
        }
    }

    public DoublePointLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * 链表头新增节点
     *
     * @param data
     */
    public void addHead(Object data) {
        Node node = new Node(data);
        //如果链表为空，那么头节点和尾节点都是该新增节点
        if (size == 0) {
            head = node;
            tail = node;
            size++;
        } else {
            node.next = head;
            head = node;
            size++;
        }
    }

    /**
     * 链表尾新增节点
     *
     * @param data
     */
    public void addTail(Object data) {
        Node node = new Node(data);
        //如果链表为空，那么头节点和尾节点都是该新增节点
        if (size == 0) {
            head = node;
            tail = node;
            size++;
        } else {
            tail.next = node;
            tail = node;
            size++;
        }
    }

    /**
     * 删除头部节点，成功返回true，失败返回false
     *
     * @return
     */
    public boolean deleteHead() {
        //当前链表节点数为0
        if (size == 0) {
            return false;
        }
        //当前链表节点数为1
        if (head.next == null) {
            head = null;
            tail = null;
        } else {
            head = head.next;
        }
        size--;
        return true;
    }

    /**
     * 判断是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * 获得链表的节点个数
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    //显示节点信息
    public void display() {
        if (size > 0) {
            Node node = head;
            int tempSize = size;
            //当前链表只有一个节点
            if (tempSize == 1) {
                System.out.println("[" + node.data + "]");
                return;
            }
            while (tempSize > 0) {
                if (node.equals(head)) {
                    System.out.print("[" + node.data + "->");
                } else if (node.next == null) {
                    System.out.print(node.data + "]");
                } else {
                    System.out.print(node.data + "->");
                }
                node = node.next;
                tempSize--;
            }
            System.out.println();
        } else {
            //如果链表一个节点都没有，直接打印[]
            System.out.println("[]");
        }
    }

}