package com.softmax.design.builder;

import cn.hutool.json.JSONObject;

/**
 * @author Jarvis
 */
public class MyConfig {

    private JSONObject data;

    private String appName;

    private Long logTime;

    private MyConfig(Builder builder) {
        this.data = builder.data;
        this.appName = builder.appName;
        this.logTime = builder.logTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return this.appName + this.data;
    }

    public static class Builder {

        private JSONObject data = new JSONObject();
        private String appName;

        private Long logTime;

        public Builder appName(String appName) {
            this.appName = appName;
            return this;
        }

        public Builder logTime(Long logTime) {
            this.logTime = logTime;
            return this;
        }

        public SemiData key(String key) {
            return new SemiData(this, key);
        }

        public MyConfig build() {
            return new MyConfig(this);
        }

        public class SemiData {
            private String key;
            private Builder parent;

            private SemiData(Builder myConfig, String key) {
                this.parent = myConfig;
                this.key = key;
            }

            public Builder value(Object value) {
                parent.data.put(key, value);
                return parent;
            }
        }

    }
}


class ConfigBuild {
    public static void main(String[] args) {
        //建造者模式 构造一个user
        MyConfig myConfig = MyConfig.builder()
                .appName("Config")
                .key("name").value("刘备")
                .key("sex").value("男")
                .key("job").value("汉中王")
                .build();
        var info = myConfig.toString();
        System.out.println(info);
    }
}


