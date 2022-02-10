package com.softmax.design.decorator;

public class ClientTest {

    public static void main(String[] args) {
        // step1��������Ҫһ���������ϣ���衢�̲�򿧷�
        Beverage beverage = new GreenTea();

        //step2����ʼװ�Σ����ӵ�ζ��

        //������
        beverage = new Lemon(beverage);
        //��â��
        beverage = new Mango(beverage);

        //step3�������ӡ
        System.out.println("���ϣ�" + beverage.getDescription() + "��\n �۸� ��" + beverage.cost());

        //˫â��-˫������-��裺
        beverage = new Mango(new Mango(new Lemon(new Lemon(new BlackTea()))));
        System.out.println("���ϣ�" + beverage.getDescription() + "��\n �۸� ��" + beverage.cost());

    }
}
