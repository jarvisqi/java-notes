package com.softmax.design.state;

/**
 * 减库存状态
 *
 * @author Jarvis
 * @date 2019/10/22
 */
class DeductState implements ProductState {
    @Override
    public void doAction(ProductContext context) {
        System.out.printf("商品%s卖出,给此商品扣减库存", context.getName());
        System.out.println();
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Deduct State";
    }
}

/**
 * 增加库存状态：
 */
class RevertState implements ProductState {
    @Override
    public void doAction(ProductContext context) {
        System.out.printf("给商品%s补库存", context.getName());
        System.out.println();
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Reverse State";
    }
}