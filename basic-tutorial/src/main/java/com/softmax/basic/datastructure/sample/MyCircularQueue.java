package com.softmax.basic.datastructure.sample;

public class MyCircularQueue {
    private int[] queue;
    private int front;
    private int rear;
    private int len;

    /**
     * Initialize your data structure here. Set the size of the queue to be k.
     */
    public MyCircularQueue(int k) {
        queue = new int[k + 1];
        // 包含
        front = 0;
        // 不包含
        rear = 0;
        len = k + 1;
    }

    /**
     * Insert an element into the circular queue. Return true if the operation is successful.
     */
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        queue[rear] = value;
        rear = (rear + 1) % len;
        return true;
    }

    /**
     * Delete an element from the circular queue. Return true if the operation is successful.
     */
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        queue[front] = -1;
        front = (front + 1) % len;
        return true;
    }

    /**
     * Get the front item from the queue.
     */
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return queue[front];
    }

    /**
     * Get the last item from the queue.
     */
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        if (rear > 0) {
            return queue[rear - 1];
        } else {
            return queue[len - 1];
        }
    }

    /**
     * Checks whether the circular queue is empty or not.
     */
    public boolean isEmpty() {
        return rear == front;
    }

    /**
     * Checks whether the circular queue is full or not.
     */
    public boolean isFull() {
        return (rear + 1) % len == front;
    }
}