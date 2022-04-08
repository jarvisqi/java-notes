package com.softmax.leet.array.example;

/**
 * 双向链表
 *
 * @author Jarvis
 */
public class TwoWayLinkedList {

    /**
     * 表示链表头
     */
    private Node head;
    /**
     * 表示链表尾
     */
    private Node tail;
    /**
     * 表示链表的节点个数
     */
    private int size;

    private class Node {
        private Object data;
        private Node next;
        private Node prev;

        public Node(Object data) {
            this.data = data;
        }
    }

    public TwoWayLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * 在链表头增加节点
     *
     * @param value
     */
    public void addHead(Object value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
        }
    }

    /**
     * 在链表尾增加节点
     *
     * @param value
     */
    public void addTail(Object value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    /**
     * 删除链表头
     *
     * @return
     */
    public Node deleteHead() {
        Node temp = head;
        if (size != 0) {
            head = head.next;
            head.prev = null;
            size--;
        }
        return temp;
    }

    /**
     * 删除链表尾
     *
     * @return
     */
    public Node deleteTail() {
        Node temp = tail;
        if (size != 0) {
            tail = tail.prev;
            tail.next = null;
            size--;
        }
        return temp;
    }

    /**
     * 获得链表的节点个数
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 判断链表是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * 显示节点信息
     */
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

    public static void main(String[] args) {
        TwoWayLinkedList linkedList = new TwoWayLinkedList();
        linkedList.addHead("AAA");
        linkedList.addTail("BBB");
        linkedList.addHead("CCC");
        linkedList.addTail("DDD");
        linkedList.addHead("EEE");
        linkedList.addTail("FFF");

        linkedList.display();
    }
}
