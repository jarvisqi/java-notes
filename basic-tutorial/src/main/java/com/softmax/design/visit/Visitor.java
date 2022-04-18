package com.softmax.design.visit;

public interface Visitor {

    public void visit(Candy candy);

    public void visit(Wine wine);

    public void visit(Fruit fruit);
}
