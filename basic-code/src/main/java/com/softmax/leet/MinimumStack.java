package com.softmax.leet;

/**
 * @author Jarvis
 */
public class MinimumStack {

    public Elem top;

    public MinimumStack() {

    }

    public void push(int x) {
        if (top == null) {
            top = new Elem(x, x);
        } else {
            Elem e = new Elem(x, Math.min(x, top.min));
            e.next = top;
            top = e;
        }
    }

    public void pop() {
        if (top == null) {
            return;
        }
        Elem temp = top.next;
        top.next = null;
        top = temp;
    }

    public int top() {
        if (top == null) {
            return -1;
        }
        return top.value;

    }

    public int getMin() {
        if (top == null) {
            return -1;
        }
        return top.min;

    }

    public static void main(String[] args) {
        MinimumStack stack = new MinimumStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("最小堆：" + stack.getMin());
        stack.pop();
        stack.pop();
        stack.pop();
        System.out.println(stack.top);
        System.out.println("最小堆：" + stack.getMin());

    }
}

