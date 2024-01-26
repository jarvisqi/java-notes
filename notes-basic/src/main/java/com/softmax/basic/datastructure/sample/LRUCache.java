package com.softmax.basic.datastructure.sample;

import java.util.HashMap;

/**
 * LRU的实现：HashMap+双向链表。
 *
 * @author Jarvis
 */
public class LRUCache {

    private HashMap<String, LRUNode> caches;
    private int capacity;
    /**
     * 头节点
     */
    private LRUNode head;
    /**
     * 尾节点
     */
    private LRUNode tail;

    /**
     * 初始化存储容器的map
     *
     * @param capacity
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        /**
         * 这里的map保存的不是单纯的Object对象，而是双向链表对象，另外记录head和tail节点，
         * 在缓存对象满的时候，要移除最后一个对象，使用tail.key可以获取到最近不使用的key，然后map.remove（key）。
         */
        this.caches = new HashMap<String, LRUNode>();
    }

    public void put(String key, Object value) {
        LRUNode node = caches.get(key);

        //新节点
        if (node == null) {
            //如果超过元素容纳量，移除最后一个节点
            if (caches.size() >= capacity) {
                caches.remove(tail.getKey());
                //移除最后一个节点:
                removeTail();
            }
            //构建一个节点,节点的prev,next下面操作
            node = new LRUNode(key, (String) value);
        }
        //已经存在的元素覆盖旧值,新创建的节点,就是一样了。
        node.setValue(value);
        //把元素移动到首部
        moveToHead(node);
        caches.put(key, node);
    }

    /**
     * 通过key获取元素
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        LRUNode node = caches.get(key);
        if (node == null) {
            return null;
        }
        //把访问的节点移动到首部。
        moveToHead(node);
        return node.getValue();
    }

    /**
     * 根据key移除节点
     *
     * @param key
     * @return
     */
    public Object remove(String key) {
        LRUNode node = caches.get(key);
        if (node != null) {
            //当前节点存在上一个节点的指向，说明不是head
            if (node.getPre() != null) {
                //由于此节点被移走了，那么上一个节点的next原本指向当前节点的，
                //移走之后，需要指向当前节点的next.
                node.getPre().setNext(node.getNext());
            }
            //当前节点存在下一个节点的指向，说明不是tail
            if (node.getNext() != null) {
                //由于此节点被移走了，那么下个节点的pre原本指向是当前节点的，
                //移走之后，需要指向当前节点的pre.
                node.getNext().setPre(node.getPre());
            }
            //当前删除的就是头部节点.那么头部节点需要指向当前节点的next.
            if (node == head) {
                head = node.getNext();
            }
            //当前删除的就是尾部节点，那么尾部节点就需要指向当前节点的上一个节点。
            if (node == tail) {
                tail = node.getPre();
            }
        }
        return caches.remove(key);
    }

    /**
     * 把当前节点移动到首部:
     */
    private void moveToHead(LRUNode node) {
        /*
         * 如果当前节点的引用，就是头部节点，无需处理，直接返回。
         */
        if (head == node) {
            //当前节点就在头位置，直接return
            return;
        }

        /*
         * 第一个节点的时候，head和tail都是不存在的，直接设置即可。
         * 另外对于节点的node的pre和next也都不存在，处理直接结束.
         */
        if (head == null || tail == null) {
            head = tail = node;
            return;
        }

        //当前节点的next存在的话,很明显是老节点
        if (node.getNext() != null) {
            //此节点要从当前位置直接移动到head,需要对于它的next进行处理。
            //移走之后，next的pre就需要指向，node的pre
            node.getNext().setPre(node.getPre());
        }
        //当node的pre存在的时候，不是第一个节点.
        if (node.getPre() != null) {
            //此节点要从当前位置直接移动到head,需要对于它的pre进行处理。
            //移走之后，pre的next就需要指向 ，node的next
            node.getPre().setNext(node.getNext());
        }
        if (node == tail) {
            tail = tail.getPre();
        }
        //head变为node的next节点。
        node.setNext(head);
        //头部移动到第二个节点，那么head.pre 就是当前节点了。
        head.setPre(node);
        // 头部节点，直接指向新的节点。
        head = node;
        //由于是head 那么pre设置为null, next就是上一次head。
        head.setPre(null);
    }

    /**
     * 移除最后一个节点
     * （1）直接把tail节点的上一个节点(pre) ，指向当前的tail即可：tail = tail.pre。
     * （2）如果新的tail==null的话，说明已经没有节点了，需要设置：head=null。
     * （3）如果新的tail存在，那么这个新的下个节点需要指向null：tail.next = null。
     */
    private void removeTail() {
        if (tail != null) {
            //将最后一个节点的上一个节点设置为tail
            tail = tail.getPre();
            if (tail == null) {
                //都移除没了
                head = null;
            } else {
                //原本指向的是当前这个的节点的，移除掉了，那么此节点的next也没有了。
                tail.setNext(null);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        LRUNode node = head;
        sb.append("[");
        while (node != null) {
            sb.append(String.format("%s:%s ", node.getKey(), node.getValue()));
            node = node.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        LRUCache lru = new LRUCache(5);
        //设置数据
        lru.put("key1", "AAA");
        lru.put("key2", "BBB");
        lru.put("key3", "CCC");
        lru.put("key4", "DDD");
        lru.put("key5", "EEE");
        System.out.println("原始链表：" + lru.toString());

        System.out.println("获取key为4的元素之后的链表:" + lru.toString());

        lru.put("key6", "FFF");
        System.out.println("新添加一个key为6之后的链表:" + lru.toString());

        lru.remove("key3");
        System.out.println("移除key=3的之后的链表:" + lru.toString());
    }
}
