/*
 * Copyright 2012 Yaqiang Wang,
 * yaqiang.wang@gmail.com
 */
package org.softmax.gradle.kriging.draw.spatial;

import java.util.ArrayList;
import java.util.List;

/**
 * BorderLine class
 *
 * @author Yaqiang Wang
 */
public class BorderLine {

    public double area;
    public Extent extent = new Extent();
    public boolean isOutLine;
    public boolean isClockwise;
    public List<PointD> pointList = new ArrayList<>();
    public List<IJPoint> ijPointList = new ArrayList<>();
}
