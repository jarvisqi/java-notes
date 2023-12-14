package com.softmax.features;

import java.util.Objects;

/**
 * 新特性 record
 */
public class RecordCustom {

    final int type;
    final String typeName;

    public int type() {
        return type;
    }

    public String name() {
        return typeName;
    }

    public RecordCustom(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RecordCustom that = (RecordCustom) o;
        return type == that.type && Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, typeName);
    }
}
