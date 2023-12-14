package com.softmax.features;

import java.util.Objects;


public class RecordCustomer {

    final int type;
    final String typeName;

    public int type() {
        return type;
    }

    public String name() {
        return typeName;
    }

    public RecordCustomer(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RecordCustomer that = (RecordCustomer) o;
        return type == that.type && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, typeName);
    }
}

/**
 * 新特性 record
 */
record RecordDemo(int type, String typeName) {
    private void test() {
        System.err.println(type + " | " + typeName);
    }

    public static void main(String[] args) {
        // 这里new的时候带的参数其实就是类的属性，等于声明属性+访问构造方法二合一。
        RecordDemo recordDemo = new RecordDemo(100, "葡萄牙");
        recordDemo.test();
    }
}
