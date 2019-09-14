package com.softmax.basic.instrumen.sample;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


/**
 * 转发类
 *
 * @author : Jarvis
 * @date : 2018/5/29
 */
public class ForwardingSet<T> implements Set<T> {

    private final Set<T> sets;

    public ForwardingSet(Set<T> sets) {
        this.sets = sets;
    }

    @Override
    public int size() {
        return sets.size();
    }

    @Override
    public boolean isEmpty() {
        return sets.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return sets.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return sets.iterator();
    }

    @Override
    public Object[] toArray() {
        return sets.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return sets.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return sets.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return sets.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return sets.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return sets.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return sets.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return sets.removeAll(c);
    }

    @Override
    public void clear() {

    }
}

