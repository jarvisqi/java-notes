package com.softmax.design.state;

/**
 * �����״̬
 *
 * @author Jarvis
 * @date 2019/10/22
 */
class DeductState implements ProductState {
    @Override
    public void doAction(ProductContext context) {
        System.out.printf("��Ʒ%s����,������Ʒ�ۼ����", context.getName());
        System.out.println();
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Deduct State";
    }
}

/**
 * ���ӿ��״̬��
 */
class RevertState implements ProductState {
    @Override
    public void doAction(ProductContext context) {
        System.out.printf("����Ʒ%s�����", context.getName());
        System.out.println();
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Reverse State";
    }
}