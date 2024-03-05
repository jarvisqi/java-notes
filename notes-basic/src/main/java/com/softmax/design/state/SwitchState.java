package com.softmax.design.state;

/**
 * 开关状态接口
 *
 * @author Jarvis
 * @date 2019/10/22
 */
public interface SwitchState {
    /**
     * @param switcher
     */
    void on(Switcher switcher);

    /**
     * @param switcher
     */
    void off(Switcher switcher);
}
