package com.softmax.basic.datastructure.sample;

public class StackInfo {
    public Node stackTop;
    public Node stackBottom;

    public StackInfo(Node stackTop, Node stackBottom) {
        this.stackTop = stackTop;
        this.stackBottom = stackBottom;
    }

    public StackInfo() {
    }

    /**
     * 进栈
     *
     * @param stackInfo 栈
     * @param value 要进栈的元素
     */
    public static void pushStack(StackInfo stackInfo, int value) {
        // 封装数据成节点
        Node newNode = new Node(value);
        // 栈顶本来指向的节点交由新节点来指向
        newNode.next = stackInfo.stackTop;
        // 栈顶指针指向新节点
        stackInfo.stackTop = newNode;
    }

    /**
     * 出栈(将栈顶的指针指向下一个节点)
     *
     * @param stackInfo
     */
    public static void popStack(StackInfo stackInfo) {
        // 栈不为空才能出栈
        if (!isEmpty(stackInfo)) {
            //栈顶元素
            Node top = stackInfo.stackTop;
            // 栈顶指针指向下一个节点
            stackInfo.stackTop = top.next;
            System.out.println("出栈的元素是：" + top.data);
        }
    }

    /**
     * 判断该栈是否为空
     *
     * @param stackInfo
     */
    public static Boolean isEmpty(StackInfo stackInfo) {
        if (stackInfo.stackTop == stackInfo.stackBottom) {
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
     * @param stackInfo
     */
    public static void traverse(StackInfo stackInfo) {
        Node stackTop = stackInfo.stackTop;
        while (stackTop != stackInfo.stackBottom) {
            System.out.println("AAAAA ——" + stackTop.data);
            stackTop = stackTop.next;
        }
    }

    /**
     * 清空栈
     * @param stackInfo
     */
    public static void clearStack(StackInfo stackInfo) {

        stackInfo.stackTop = null;
        stackInfo.stackBottom = stackInfo.stackTop;
    }

    public static void main(String[] args) {
        //初始化栈(无元素)
        StackInfo stackInfo = new StackInfo(new Node(), new Node());
        //栈顶和栈尾是同一指向
        stackInfo.stackBottom = stackInfo.stackTop;
        //指向null
        stackInfo.stackTop.next = null;
        //进栈
        pushStack(stackInfo, 3);
        pushStack(stackInfo, 4);
        pushStack(stackInfo, 5);

        //先进后出
        traverse(stackInfo);

        popStack(stackInfo);
        popStack(stackInfo);
        popStack(stackInfo);
    }
}
