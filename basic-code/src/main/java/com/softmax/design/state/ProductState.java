package com.softmax.design.state;

/**
 *
 *
 * @author Jarvis
 * @date 2019/10/22
 */
public interface ProductState {

    /**
     *
     *
     * @param context
     */
    void doAction(ProductContext context);

}
