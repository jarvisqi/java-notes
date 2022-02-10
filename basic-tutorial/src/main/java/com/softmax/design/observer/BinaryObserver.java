package com.softmax.design.observer;

/**
 * �������۲���
 */
public class BinaryObserver extends Observer {

    /**
     * // �ڹ��췽���н��ж�������
     *
     * @param subject
     */
    public BinaryObserver(Subject subject) {
        this.subject = subject;
        // ͨ���ڹ��췽���н� this ������ȥ�Ĳ���һ��ҪС��
        this.subject.attach(this);
    }

    @Override
    public void update() {
        String result = Integer.toBinaryString(subject.getState());
        System.out.println("���ĵ����ݷ����仯���µ����ݴ���Ϊ������ֵΪ��" + result);
    }
}

class HexaObserver extends Observer {
    public HexaObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        String result = Integer.toHexString(subject.getState()).toUpperCase();
        System.out.println("���ĵ����ݷ����仯���µ����ݴ���Ϊʮ������ֵΪ��" + result);
    }
}