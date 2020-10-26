package com.softmax.design.decorator;

/**
 * �������ĵ�ζ��
 */
public class Lemon extends Condiment {

    private final Beverage bevarage;

    /**
     * ��Ҫ�����������ϣ�����Ҫ����û�б�װ�εĺ����̲裬
     * Ҳ���Դ����Ѿ�װ�κõ�â���̲裬����������â�������̲�
     *
     * @param bevarage
     */
    public Lemon(Beverage bevarage) {
        this.bevarage = bevarage;
    }

    /**
     * װ��
     *
     * @return
     */
    @Override
    public String getDescription() {
        return bevarage.getDescription() + ",�ӡ����ʡ�";
    }

    /**
     * װ��
     *
     * @return
     */
    @Override
    public double cost() {
        // ��������Ҫ 2 Ԫ
        return bevarage.cost() + 2;
    }
}


class Mango extends Condiment {

    private final Beverage beverage;

    public Mango(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",�ӡ�â����";
    }


    @Override
    public double cost() {
        //��â����Ҫ��1.5Ԫ
        return beverage.cost() + 1.5;
    }
}