package com.softmax.design.state;

/**
 * 开关类
 *
 * @author Jarvis
 * @date 2019/10/22
 */

public class Switcher {

    /**
     * 开关状态
     */
    private SwitchState switchState = new SwitchOff();

    public SwitchState getSwitchState() {
        return switchState;
    }

    public void setSwitchState(SwitchState switchState) {
        this.switchState = switchState;
    }

    /**
     * 打开
     */
    public void switchOn() {
        switchState.on(this);
    }

    /**
     * 关闭
     */
    public void switchOff() {
        switchState.off(this);
    }

}
