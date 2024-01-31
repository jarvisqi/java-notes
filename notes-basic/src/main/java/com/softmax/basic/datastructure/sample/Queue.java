package com.softmax.basic.datastructure.sample;

public class Queue {

    /**
     * 数组
     */
    public int[] arrays;

    /**
     * 指向第一个有效的元素
     */
    public int front;

    /**
     * 指向有效数据的下一个元素(即指向无效的数据)
     * rear并不指向最后一个有效的元素，在循环队列中这样设计是非常方便的！
     * 因为这样设计可以让我们分得清队头和队尾(不然循环队列不断入队或出队，位置是变化很快的)
     */
    public int rear;

    /**
     * 判断队列是否满了，front和rear指针紧挨着，就是满了
     *
     * @param queue
     * @return
     */
    public static boolean isFull(Queue queue) {
        if ((queue.rear + 1) % queue.arrays.length == queue.front) {

            System.out.println("此时队列满了！");
            return true;
        } else {
            System.out.println("此时队列没满了！");
            return false;
        }
    }

    /**
     * 入队
     *
     * @param queue
     */
    public static void enQueue(Queue queue, int value) {
        // 不是满的队列才能入队
        if (!isFull(queue)) {
            // 将新的元素插入到队尾中
            queue.arrays[queue.rear] = value;
            // rear节点移动到新的无效元素位置上
            queue.rear = (queue.rear + 1) % queue.arrays.length;
        }
    }

    /**
     * 遍历队列
     *
     * @param queue
     */
    public static void traverseQueue(Queue queue) {
        // front的位置
        int i = queue.front;
        while (i != queue.rear) {
            System.out.println(queue.arrays[i]);
            //移动front
            i = (i + 1) % queue.arrays.length;
        }
    }

    /**
     * 判断队列是否空，front和rear指针相等，就是空了
     *
     * @param queue
     * @return
     */
    public static boolean isEmpty(Queue queue) {
        if (queue.rear == queue.front) {
            System.out.println("此时队列空的！");
            return true;
        } else {
            System.out.println("此时队列非空！");
            return false;
        }
    }

    /**
     * 出队
     *
     * @param queue
     */
    public static void outQueue(Queue queue) {
        //判断该队列是否为null
        if (!isEmpty(queue)) {
            //不为空才出队
            int value = queue.arrays[queue.front];
            System.out.println("出队的元素是：" + value);
            // front指针往后面移
            queue.front = (queue.front + 1) % queue.arrays.length;
        }
    }

    public static void main(String[] args) {
        //初始化队列
        Queue queue = new Queue();
        queue.front = 0;
        queue.rear = 0;
        queue.arrays = new int[6];

        Queue.enQueue(queue, 1);
        Queue.enQueue(queue, 2);
        Queue.enQueue(queue, 3);

        Queue.traverseQueue(queue);

        Queue.outQueue(queue);
        Queue.outQueue(queue);
        Queue.outQueue(queue);


    }
}
