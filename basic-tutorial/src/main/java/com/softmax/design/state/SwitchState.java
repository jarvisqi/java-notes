package com.softmax.design.state;

/**
 * ����һ��״̬State�ӿ�.�������������
 *
 * @author Jarvis
 * @date 2019/10/22
 */
public interface SwitchState {
    /**
     * ��
     *
     * @param switcher
     */
    void on(Switcher switcher);

    /**
     * ��
     *
     * @param switcher
     */
    void off(Switcher switcher);
}
