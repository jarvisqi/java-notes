package com.softmax.mq.courier.kafka.beans;

import org.springframework.stereotype.Component;

/**
 * 业务操作日志
 */
@Component
public class BizOperationLog {

    /**
     * 系统id
     */
    private String appId;
    /**
     * 系统名称
     */
    private String appName;
    /**
     * 操作时间
     */
    private String optTime;
    /**
     * 修改前数据
     */
    private String beforeModifyData;
    /**
     * 修改后数据
     */
    private String afterModifyData;
    /**
     * 操作人
     */
    private String optUser;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getBeforeModifyData() {
        return beforeModifyData;
    }

    public void setBeforeModifyData(String beforeModifyData) {
        this.beforeModifyData = beforeModifyData;
    }

    public String getAfterModifyData() {
        return afterModifyData;
    }

    public void setAfterModifyData(String afterModifyData) {
        this.afterModifyData = afterModifyData;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }
}
