package com.basecode.jvmdemo;


class Grandpa {
    static {
        System.out.println("爷爷在静态代码块");
    }
}

class Father extends Grandpa {
    static {
        System.out.println("爸爸在静态代码块");
    }

    /**
     * Java 中的变量有「类变量」和「类成员变量」两种类型，「类变量」指的是被 static 修饰的变量，而其他所有类型的变量都属于「类成员变量」
     * 「类变量」指的是被 static 修饰的变量,在准备阶段，JVM 只会为「类变量」分配内存，而不会为「类成员变量」分配内存
     * 「类成员变量」的内存分配需要等到初始化阶段才开始。
     * <p>
     * 初始化的类型。在准备阶段，JVM 会为类变量分配内存，并为其初始化。
     * 但是这里的初始化指的是为变量赋予 Java 语言中该数据类型的零值，而不是用户代码里初始化的值。
     * 但如果一个变量是常量（被 static final 修饰）的话，那么在准备阶段，属性便会被赋予用户希望的值
     */
    public static int factor = 25;

    public Father() {
        System.out.println("我是爸爸~");
    }
}

class Son extends Father {
    static {
        System.out.println("儿子在静态代码块");
    }

    public Son() {
        System.out.println("我是儿子~");
    }
}

/**
 * @author : Jarvis
 * @date : 2018/6/14
 */
public class JvmDemo0 {
    /**
     * 当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要先触发其父类的初始化。
     * 当虚拟机启动时，用户需要指定一个要执行的主类（包含main()方法的那个类），虚拟机会先初始化这个主类。
     * 类初始化方法。编译器会按照其出现顺序，收集类变量的赋值语句、静态代码块，最终组成类初始化方法。
     * 类初始化方法一般在类初始化的时候执行。
     * 分析：
     * 首先程序到 main 方法这里，使用标准化输出 Son 类中的 factor 类成员变量，但是 Son 类中并没有定义这个类成员变量。
     * 于是往父类去找，我们在 Father 类中找到了对应的类成员变量，于是触发了 Father 的初始化。
     * 但根据我们上面说到的初始化的 5 种情况中的第 3 种（当初始化一个类的时候，如果发现其父类还没有进行过初始化，
     * 则需要先触发其父类的初始化）。我们需要先初始化 Father 类的父类，也就是先初始化 Grandpa 类再初始化 Father 类。
     * 于是我们先初始化 Grandpa 类输出：「爷爷在静态代码块」，再初始化 Father 类输出：「爸爸在静态代码块」。
     * 最后，所有父类都初始化完成之后，Son 类才能调用父类的静态变量，从而输出：「爸爸的岁数：25」。
     *
     * @param args
     */
    public static void main(String[] args) {
        //入口
        System.out.println("爸爸的岁数:" + Son.factor);
    }
}

