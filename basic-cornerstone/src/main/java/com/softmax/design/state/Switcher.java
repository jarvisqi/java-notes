package com.softmax.design.state;

/**
 * 开关类
 *
 * @author Jarvis
 * @date 2019/10/22
 */

public class Switcher {

    /**
     * 开关的初始状态设置为“关”
     */
    private SwitchState switchState = new SwitchOff();

    public SwitchState getSwitchState() {
        return switchState;
    }

    public void setSwitchState(SwitchState switchState) {
        this.switchState = switchState;
    }

    /**
     * 用的是当前状态的开方法
     */
    public void switchOn() {
        switchState.on(this);
    }

    /**
     * 用的是当前状态的关方法
     */
    public void switchOff() {
        switchState.off(this);
    }

}
