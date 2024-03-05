package com.softmax.design.state;

/**
 * 定义减库存的状态
 *
 * @author Jarvis
 * @date 2019/10/22
 */
class DeductState implements ProductState {
    @Override
    public void doAction(ProductContext context) {
        System.out.printf("商品卖出，准备减库存", context.getName());
        System.out.println();
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Deduct State";
    }
}

/**
 *
 */
class RevertState implements ProductState {
    @Override
    public void doAction(ProductContext context) {
        System.out.printf("给此商品补库存", context.getName());
        System.out.println();
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Reverse State";
    }
}