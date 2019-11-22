package com.softmax.design.state;

/**
 * 商品库存中心有个最基本的需求是减库存和补库存，用状态模式来实现
 *
 * @author Jarvis
 * @date 2019/10/22
 */
public interface ProductState {

    /**
     * 库存增、减接口
     *
     * @param context
     */
    void doAction(ProductContext context);

}
