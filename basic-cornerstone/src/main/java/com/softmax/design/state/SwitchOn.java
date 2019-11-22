package com.softmax.design.state;

/**
 * 开状态实现类
 *
 * @author Jarvis
 * @date 2019/10/22
 */
public class SwitchOn implements SwitchState {
    @Override
    public void on(Switcher switcher) {
        System.out.println("WARN!!!通电状态无需再开");
    }

    @Override
    public void off(Switcher switcher) {
        switcher.setSwitchState(new SwitchOff());
        System.out.println("OK...灯灭");
    }
}


/**
 * 关状态的实现类
 *
 * @author Jarvis
 * @date 2019/10/22
 */
class SwitchOff implements SwitchState {
    @Override
    public void on(Switcher switcher) {
        switcher.setSwitchState(new SwitchOn());
        System.out.println("OK...灯亮");
    }

    @Override
    public void off(Switcher switcher) {
        System.out.println("WARN!!!断电状态无需再关");
    }
}

