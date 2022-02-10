package com.softmax.design.state;

/**
 * 定义一个状态State接口.两个方法开与关
 *
 * @author Jarvis
 * @date 2019/10/22
 */
public interface SwitchState {
    /**
     * 开
     *
     * @param switcher
     */
    void on(Switcher switcher);

    /**
     * 关
     *
     * @param switcher
     */
    void off(Switcher switcher);
}
