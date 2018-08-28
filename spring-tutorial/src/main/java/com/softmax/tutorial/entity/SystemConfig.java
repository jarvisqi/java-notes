package com.softmax.tutorial.entity;


import com.softmax.tutorial.entity.enums.FileType;

/**
 * 系统配置
 *
 * @author Jarvis
 * @date 2018/8/24
 */
public class SystemConfig {

    private int id;
    private String key;
    private String value;
    private FileType fileType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }


}
