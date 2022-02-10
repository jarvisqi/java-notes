package com.softmax.design.state;

/**
 * ��״̬ʵ����
 *
 * @author Jarvis
 * @date 2019/10/22
 */
public class SwitchOn implements SwitchState {
    @Override
    public void on(Switcher switcher) {
        System.out.println("WARN!!!ͨ��״̬�����ٿ�");
    }

    @Override
    public void off(Switcher switcher) {
        switcher.setSwitchState(new SwitchOff());
        System.out.println("OK...����");
    }
}


/**
 * ��״̬��ʵ����
 *
 * @author Jarvis
 * @date 2019/10/22
 */
class SwitchOff implements SwitchState {
    @Override
    public void on(Switcher switcher) {
        switcher.setSwitchState(new SwitchOn());
        System.out.println("OK...����");
    }

    @Override
    public void off(Switcher switcher) {
        System.out.println("WARN!!!�ϵ�״̬�����ٹ�");
    }
}

