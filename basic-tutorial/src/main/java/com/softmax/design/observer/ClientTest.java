package com.softmax.design.observer;

public class ClientTest {

    public static void main(String[] args) {
        // �ȶ���һ������
        Subject subject1 = new Subject();

        //����۲���
        new BinaryObserver(subject1);
        new HexaObserver(subject1);

        // ģ�����ݱ�������ʱ�򣬹۲����ǵ� update �������ᱻ����
        subject1.setState(10);
    }
}
