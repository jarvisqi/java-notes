package com.softmax.basic.datastructure.sample;

public class Stack {
    public Node stackTop;
    public Node stackBottom;

    public Stack(Node stackTop, Node stackBottom) {
        this.stackTop = stackTop;
        this.stackBottom = stackBottom;
    }

    public Stack() {
    }

    /**
     * 进栈
     *
     * @param stack 栈
     * @param value 要进栈的元素
     */
    public static void pushStack(Stack stack, int value) {
        // 封装数据成节点
        Node newNode = new Node(value);
        // 栈顶本来指向的节点交由新节点来指向
        newNode.next = stack.stackTop;
        // 栈顶指针指向新节点
        stack.stackTop = newNode;
    }

    /**
     * 出栈(将栈顶的指针指向下一个节点)
     *
     * @param stack
     */
    public static void popStack(Stack stack) {
        // 栈不为空才能出栈
        if (!isEmpty(stack)) {
            //栈顶元素
            Node top = stack.stackTop;
            // 栈顶指针指向下一个节点
            stack.stackTop = top.next;
            System.out.println("出栈的元素是：" + top.data);
        }
    }

    /**
     * 判断该栈是否为空
     *
     * @param stack
     */
    public static Boolean isEmpty(Stack stack) {
        if (stack.stackTop == stack.stackBottom) {
            System.out.println("该栈为空");
            return true;
        } else {
            System.out.println("该栈不为空");
            return false;
        }
    }

    /**
     * 遍历栈(只要栈顶指针不指向栈底指针，就一直输出)
     *
     * @param stack
     */
    public static void traverse(Stack stack) {
        Node stackTop = stack.stackTop;
        while (stackTop != stack.stackBottom) {
            System.out.println("AAAAA ——" + stackTop.data);
            stackTop = stackTop.next;
        }
    }

    /**
     * 清空栈
     * @param stack
     */
    public static void clearStack(Stack stack) {

        stack.stackTop = null;
        stack.stackBottom = stack.stackTop;
    }

    public static void main(String[] args) {
        //初始化栈(无元素)
        Stack stack = new Stack(new Node(), new Node());
        //栈顶和栈尾是同一指向
        stack.stackBottom = stack.stackTop;
        //指向null
        stack.stackTop.next = null;
        //进栈
        pushStack(stack, 3);
        pushStack(stack, 4);
        pushStack(stack, 5);

        //先进后出
        traverse(stack);

        popStack(stack);
        popStack(stack);
        popStack(stack);
    }
}
