/*
 * Copyright 2012 Yaqiang Wang,
 * yaqiang.wang@gmail.com
 */
package org.softmax.ms.gateway.kriging.draw.spatial;

/**
 * PointF class
 *
 * @author Yaqiang Wang
 */
public class PointF {

    public float X;
    public float Y;

    /**
     * Constructor
     */
    public PointF() {

    }

    /**
     * Constructor
     *
     * @param x X
     * @param y Y
     */
    public PointF(float x, float y) {
        X = x;
        Y = y;
    }
}
