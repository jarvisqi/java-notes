package com.softmax.design.iterator;

public interface Iterator<E> {
    /**
     * //返回下一个元素
     */
    E next();

    /**
     * //是否还有下一个元素
     */
    boolean hasNext();
}
