package com.softmax.design.state;

/**
 * ¿â´æ×´Ì¬
 *
 * @author Jarvis
 * @date 2019/10/22
 */
public class ProductContext {

    private ProductState state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public ProductContext(String name) {
        this.name = name;
    }

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

}
