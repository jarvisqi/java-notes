package com.softmax.features;


/**
 * 通过 sealed 关键字标识，通过 permits 允许继承的子类。
 */
public class SealedTest {


    public sealed class Demo
            permits Child, NoSealedChild {
    }

    /**
     * 密封类的子类，默认同样是密封类，必须带 final 关键字，表示不能再被继承
     */
    public final class Child extends Demo {
    }

    /**
     * 密封类的子类，通过non-sealed设定为非密封类，可以再被继承
     */
    public non-sealed class NoSealedChild extends Demo {
    }

    /**
     * 密封类子类，并且是非密封类，就可以再被下面的类继承
     */
    public class Grandchild extends NoSealedChild {

    }
}

