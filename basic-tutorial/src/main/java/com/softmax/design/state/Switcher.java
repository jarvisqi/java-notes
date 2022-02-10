package com.softmax.design.state;

/**
 * ������
 *
 * @author Jarvis
 * @date 2019/10/22
 */

public class Switcher {

    /**
     * ���صĳ�ʼ״̬����Ϊ���ء�
     */
    private SwitchState switchState = new SwitchOff();

    public SwitchState getSwitchState() {
        return switchState;
    }

    public void setSwitchState(SwitchState switchState) {
        this.switchState = switchState;
    }

    /**
     * �õ��ǵ�ǰ״̬�Ŀ�����
     */
    public void switchOn() {
        switchState.on(this);
    }

    /**
     * �õ��ǵ�ǰ״̬�Ĺط���
     */
    public void switchOff() {
        switchState.off(this);
    }

}
