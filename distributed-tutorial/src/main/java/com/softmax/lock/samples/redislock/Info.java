package com.softmax.lock.samples.redislock;

import java.io.Serializable;

/**
 * @author Jarvis
 * @date 2018/8/2
 */
public class Info implements Serializable {
    private static final long serialVersionUID = -7898194272883238670L;

    private Integer id;
    private String name;

    public Info(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
