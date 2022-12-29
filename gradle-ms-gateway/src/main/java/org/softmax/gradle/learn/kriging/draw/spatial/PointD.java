/*
 * Copyright 2012 Yaqiang Wang,
 * yaqiang.wang@gmail.com
 */
package org.softmax.gradle.learn.kriging.draw.spatial;

/**
 * PointD class
 *
 * @author Yaqiang Wang
 */
public class PointD {

    public double X;
    public double Y;

    /**
     * Constructor
     */
    public PointD() {
        X = 0.0;
        Y = 0.0;
    }

    /**
     * Constructor
     *
     * @param x X
     * @param y Y
     */
    public PointD(double x, double y) {
        X = x;
        Y = y;
    }

    @Override
    public Object clone() {
        return new PointD(X, Y);
    }
}
