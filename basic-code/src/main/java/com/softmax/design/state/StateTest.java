package com.softmax.design.state;

/**
 * @author Jarvis
 * @date 2019/10/22
 */
public class StateTest {
    public static void main(String[] args) {

        ProductContext context = new ProductContext("����ս��");

        //
        DeductState deductState = new DeductState();
        deductState.doAction(context);

        //�������
        RevertState revertState = new RevertState();
        revertState.doAction(context);

        //��ȡ��ǰ״̬
        String state = context.getState().toString();
        System.out.println(state);

        System.out.println("==================================================");

        Switcher switcher = new Switcher();

        switcher.setSwitchState(new SwitchOff());
        System.out.println("��ʱ�Ƶ�״̬ΪOff");
        //�ص�
        switcher.switchOff();
        //����
        switcher.switchOn();
        //�ص�
        switcher.switchOff();

        switcher.setSwitchState(new SwitchOn());
        System.out.println("��ʱ�Ƶ�״̬On");
        //����
        switcher.switchOn();
        //�ص�
        switcher.switchOff();
        //����
        switcher.switchOn();
    }
}
