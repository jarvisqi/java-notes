package com.softmax.design.state;

/**
 * ��Ʒ��������и�������������Ǽ����Ͳ���棬��״̬ģʽ��ʵ��
 *
 * @author Jarvis
 * @date 2019/10/22
 */
public interface ProductState {

    /**
     * ����������ӿ�
     *
     * @param context
     */
    void doAction(ProductContext context);

}
