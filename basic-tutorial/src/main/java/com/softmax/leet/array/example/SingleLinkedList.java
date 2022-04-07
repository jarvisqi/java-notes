package com.softmax.leet.array.example;

/**
 * 单项链表
 *
 * @author Jarvis
 */
public class SingleLinkedList {

    /**
     * 链表节点的个数
     */
    private int size;
    /**
     * 头节点
     */
    private Node head;


    public class Node {
        /**
         * 每个节点的数据
         */
        private Object data;
        /**
         * 每个节点指向下一个节点的连接
         */
        private Node next;

        public Node(Object data) {
            this.data = data;
        }
    }

    /**
     * 在链表头添加元素
     *
     * @param obj
     * @return
     */
    public Object addHead(Object obj) {
        Node newHead = new Node(obj);
        if (size == 0) {
            head = newHead;
        } else {
            newHead.next = head;
            head = newHead;
        }
        size++;
        return obj;
    }

    /**
     * 在链表头删除元素
     *
     * @return
     */
    public Object deleteHead() {
        Object obj = head.data;
        head = head.next;
        size--;
        return obj;
    }

    /**
     * 查找指定元素，找到了返回节点Node，找不到返回null
     *
     * @param obj
     * @return
     */
    public Node find(Object obj) {
        Node current = head;
        int tempSize = size;
        while (tempSize > 0) {
            if (obj.equals(current.data)) {
                return current;
            } else {
                current = current.next;
            }
            tempSize--;
        }
        return null;
    }

    /**
     * 删除指定的元素，删除成功返回true
     *
     * @param value
     * @return
     */
    public boolean delete(Object value) {
        if (size == 0) {
            return false;
        }
        Node current = head;
        Node previous = head;
        while (current.data != value) {
            if (current.next == null) {
                return false;
            } else {
                previous = current;
                current = current.next;
            }
        }
        //如果删除的节点是第一个节点
        if (current == head) {
            head = current.next;
            size--;
        } else {
            //删除的节点不是第一个节点
            previous.next = current.next;
            size--;
        }
        return true;
    }

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
        SingleLinkedList singleList = new SingleLinkedList();
        singleList.addHead("A");
        singleList.addHead("B");
        singleList.addHead("C");
        singleList.addHead("D");

        singleList.display();

        singleList.delete("C");
        singleList.display();

        System.out.println(singleList.find("B"));
    }
}
