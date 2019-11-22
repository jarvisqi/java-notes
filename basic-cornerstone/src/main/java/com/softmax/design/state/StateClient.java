package com.softmax.design.state;

/**
 * @author Jarvis
 * @date 2019/10/22
 */
public class StateClient {
    public static void main(String[] args) {

        ProductContext context = new ProductContext("神舟战神");

        //减库存操作
        DeductState deductState = new DeductState();
        deductState.doAction(context);

        //增存操作
        RevertState revertState = new RevertState();
        revertState.doAction(context);

        //获取当前状态
        String state = context.getState().toString();
        System.out.println(state);

        System.out.println("==================================================");

        Switcher switcher = new Switcher();

        switcher.setSwitchState(new SwitchOff());
        System.out.println("此时灯的状态为Off");
        //关灯
        switcher.switchOff();
        //开灯
        switcher.switchOn();
        //关灯
        switcher.switchOff();

        switcher.setSwitchState(new SwitchOn());
        System.out.println("此时灯的状态On");
        //开灯
        switcher.switchOn();
        //关灯
        switcher.switchOff();
        //开灯
        switcher.switchOn();
    }
}
