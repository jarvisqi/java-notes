package com.softmax.design.state;

/**
 * @author Jarvis
 * @date 2019/10/22
 */
public class SwitchOn implements SwitchState {
    @Override
    public void on(Switcher switcher) {
        System.out.println("WARN!!!ͨ开个已打开");
    }

    @Override
    public void off(Switcher switcher) {
        switcher.setSwitchState(new SwitchOff());
        System.out.println("OK...开个已关闭");
    }
}


/**
 * @author Jarvis
 * @date 2019/10/22
 */
class SwitchOff implements SwitchState {
    @Override
    public void on(Switcher switcher) {
        switcher.setSwitchState(new SwitchOn());
        System.out.println("OK...开关打开");
    }

    @Override
    public void off(Switcher switcher) {
        System.out.println("WARN!!!开关已关闭");
    }
}

