package org.softmax.gradle.learn.kriging.algorithm;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Interpolate class - including the functions of interpolation
 *
 * @author Yaqiang Wang
 * @version $Revision: 1.6 $
 */
public class IDW {
    // <editor-fold desc="IDW">
    /**
     * Create grid x/y coordinate arrays with x/y delt
     *
     * @param Xlb x of left-bottom
     * @param Ylb y of left-bottom
     * @param Xrt x of right-top
     * @param Yrt y of right-top
     * @param XDelt x delt
     * @param YDelt y delt
     * @return X/Y coordinate arrays
     */
    public static List<double[]> createGridXY_Delt(double Xlb, double Ylb, double Xrt, double Yrt, double XDelt, double YDelt) {
        int i, Xnum, Ynum;
        Xnum = (int) ((Xrt - Xlb) / XDelt + 1);
        Ynum = (int) ((Yrt - Ylb) / YDelt + 1);
        double[] X = new double[Xnum];
        double[] Y = new double[Ynum];
        for (i = 0; i < Xnum; i++) {
            X[i] = Xlb + i * XDelt;
        }
        for (i = 0; i < Ynum; i++) {
            Y[i] = Ylb + i * YDelt;
        }

        List<double[]> values = new ArrayList<>();
        values.add(X);
        values.add(Y);
        return values;
    }

    /**
     * Create grid X/Y coordinate
     *
     * @param Xlb X left bottom
     * @param Ylb Y left bottom
     * @param Xrt X right top
     * @param Yrt Y right top
     * @param X X coordinate
     * @param Y Y coordinate
     */
    public static void createGridXY_Num(double Xlb, double Ylb, double Xrt, double Yrt,
            double[] X, double[] Y) {
        int i;
        double XDelt, YDelt;
        int Xnum = X.length;
        int Ynum = Y.length;
        XDelt = (Xrt - Xlb) / Xnum;
        YDelt = (Yrt - Ylb) / Ynum;
        for (i = 0; i < Xnum; i++) {
            X[i] = Xlb + i * XDelt;
        }
        for (i = 0; i < Ynum; i++) {
            Y[i] = Ylb + i * YDelt;
        }
        System.out.println("像元XDelt"+XDelt);
        System.out.println("像元YDelt"+YDelt);
    }

    static List<List<Object[]>> searchPointsInRange(double[][] sCoords, 
            double[] center, 
            double longSemiAxisWidth,
            double shortSemiAxisWidth) {
        List<Object[]> allPoints = new ArrayList<>();
        List<Object[]> inRangePoints = new ArrayList<>();
        double distance;
        double ssawSquare = shortSemiAxisWidth * shortSemiAxisWidth;
        double lsawSquare = longSemiAxisWidth * longSemiAxisWidth;

        for(int i = 0, len = sCoords.length; i < len; i++) {
            double px = sCoords[i][0];
            double py = sCoords[i][1];
            distance = Math.pow(center[0]-px, 2) + Math.pow(center[1]-py, 2);
            if(ssawSquare*(px-center[0])*(px-center[0]) + lsawSquare*(py-center[1])*(py-center[1])
                 < ssawSquare*lsawSquare){
                inRangePoints.add(new Object[]{
                    sCoords[i],
                    1/distance
                });
            }
            allPoints.add(new Object[]{
                        sCoords[i],
                        1/distance
                    });

        }

        allPoints.sort(new Comparator<Object[]>(){
			@Override
			public int compare(Object[] o1, Object[] o2) {
                return Double.compare((double)o2[1], (double)o1[1]);
			}
        });
        inRangePoints.sort(new Comparator<Object[]>(){
			@Override
			public int compare(Object[] o1, Object[] o2) {
                return Double.compare((double)o2[1], (double)o1[1]);
			}
        });
        
		return Arrays.asList(inRangePoints, allPoints);
    }


    /**
     * 
     * @param SCoords
     * @param X
     * @param Y
     * @param maxNumberOfNearestNeighbors 最大相邻要素数
     * @param minNumberOfNearestNeighbors 最小相邻要素数
     * @param longSemiAxisWidth  长半轴长度
     * @param shortSemiAxisWidth 短半轴长度
     * @param sectorNum 扇区数量
     * @param sectorOffsetDegree 扇区偏移角度
     * @return
     */
    public static double[][] interpolation_IDW(double[][] SCoords, 
                double[]X, double[]Y, 
                int maxNumberOfNearestNeighbors,
                int minNumberOfNearestNeighbors,
                double longSemiAxisWidth,
                double shortSemiAxisWidth,
                int sectorNum,
                int sectorOffsetDegree) {
        int rowNum, colNum, pNum;
        colNum = X.length;
        rowNum = Y.length;
        pNum = SCoords.length;
        double[][] GCoords = new double[rowNum][colNum];
        int i, j, p;
        double SV, SW;

        for(i = 0; i < rowNum; i++) {
            for(j = 0; j < colNum; j++) {
                GCoords[i][j] = Double.MIN_VALUE;
                SV = 0;
                SW = 0;
                for(p = 0; p<pNum; p++) {
                    if(X[j] == SCoords[p][0] && Y[i] == SCoords[p][1]) {
                        GCoords[i][j] = SCoords[p][2];
                        break;
                    }
                }
                
                if(GCoords[i][j] == Double.MIN_VALUE) {
                    List<List<Object[]>> pointsList = searchPointsInRange(SCoords, 
                                            new double[]{X[i], Y[j]}, 
                                            longSemiAxisWidth, 
                                            shortSemiAxisWidth);
                    List<Object[]> inRangePoints = pointsList.get(0);
                    List<Object[]> allPoints = pointsList.get(1);
                    List<Object[]> needPoints = null;
                    if(inRangePoints.size()>=maxNumberOfNearestNeighbors) {//大于等于最大相邻要素数
                        needPoints = inRangePoints.subList(0, maxNumberOfNearestNeighbors);
                    }else if(inRangePoints.size()>=minNumberOfNearestNeighbors) {//大于等于最小相邻要素数，小于最大相邻要素数
                        needPoints = inRangePoints;
                    }else { //小于最小相邻要素数
                        if(allPoints.size()>=minNumberOfNearestNeighbors) {
                            needPoints = allPoints.subList(0, minNumberOfNearestNeighbors);
                        } else {
                            needPoints = allPoints;
                        }
                    }
                    
                    int len = needPoints.size();
                    Object[] point;
                    double weight;
                    double[]scoord;
                    for (p = 0; p < len; p++) {
                        point = needPoints.get(p);
                        scoord = (double[])point[0];
                        weight = (double) point[1];
                        SV += weight * scoord[2];
                        SW += weight;
                    }
                    GCoords[i][j] = SV / SW;
                }
            }
        }
        
        return GCoords;
    }

    /**
     * Interpolation with IDW neighbor method
     *
     * @param SCoords discrete data array
     * @param X grid X array
     * @param Y grid Y array
     * @param NumberOfNearestNeighbors number of nearest neighbors
     * @return grid data
     */
    public static double[][] interpolation_IDW_Neighbor(double[][] SCoords, double[] X, double[] Y,
            int NumberOfNearestNeighbors) {
        int rowNum, colNum, pNum;
        colNum = X.length;
        rowNum = Y.length;
        pNum = SCoords.length;
        double[][] GCoords = new double[rowNum][colNum];
        int i, j, p, l, aP;
        double w, SV, SW, aMin;
        int points = NumberOfNearestNeighbors;
        Object[][] NW = new Object[2][points];

        //---- Do interpolation
        for (i = 0; i < rowNum; i++) {
            for (j = 0; j < colNum; j++) {
                GCoords[i][j] = Double.MIN_VALUE;
                SV = 0;
                SW = 0;
                for (p = 0; p < points; p++) {//先取样本前points个点计算反距离和记录相应的位置所有p
                    if (X[j] == SCoords[p][0] && Y[i] == SCoords[p][1]) {
                        GCoords[i][j] = SCoords[p][2];
                        break;
                    } else {
                        w = 1 / (Math.pow(X[j] - SCoords[p][0], 2) + Math.pow(Y[i] - SCoords[p][1], 2));
                        NW[0][p] = w;//记录权重
                        NW[1][p] = p;//记录位置索引
                    }
                }
                if (GCoords[i][j] == Double.MIN_VALUE) {
                    for (p = points; p < pNum; p++) {
                        double distance = Math.pow(X[j] - SCoords[p][0], 2) + Math.pow(Y[i] - SCoords[p][1], 2);
                        if (distance == 0) {
                            GCoords[i][j] = SCoords[p][2];
                            break;
                        } else {
                            aMin = Double.parseDouble(NW[0][0].toString());
                            aP = 0;
                            for (l = 1; l < points; l++) {//找到前points个样本点中的最小权重值（距离倒数）
                                if (Double.parseDouble(NW[0][l].toString()) < aMin) {
                                    aMin = Double.parseDouble(NW[0][l].toString());
                                    aP = l;
                                }
                            }
                            w = 1 / distance;
                            if (w > aMin) {//不断用权值大的点替换前points个点，最终实现前point个点取的是最邻近的点
                                NW[0][aP] = w;
                                NW[1][aP] = p;
                            }
                        }
                    }
                    if (GCoords[i][j] == Double.MIN_VALUE) {
                        /**
                         * 根据选取的邻近的样本点计算预测的的值
                         * z = ∑zi*(1/di) / ∑(1/di)
                         */
                        for (p = 0; p < points; p++) {
                            SV += Double.parseDouble(NW[0][p].toString()) * SCoords[Integer.parseInt(NW[1][p].toString())][2];
                            SW += Double.parseDouble(NW[0][p].toString());
                        }
                        GCoords[i][j] = SV / SW;
                    }
                }
            }
        }

        //---- Smooth with 5 points
        double s = 0.5;
        for (i = 1; i < rowNum - 1; i++) {
            for (j = 1; j < colNum - 1; j++) {
                GCoords[i][j] = GCoords[i][j] + s / 4 * (GCoords[i + 1][j] + GCoords[i - 1][j]
                        + GCoords[i][j + 1] + GCoords[i][j - 1] - 4 * GCoords[i][j]);
            }
        }

        return GCoords;
    }

    /**
     * Interpolation with IDW neighbor method
     *
     * @param SCoords discrete data array
     * @param X grid X array
     * @param Y grid Y array
     * @param NumberOfNearestNeighbors number of nearest neighbors
     * @param unDefData undefine data
     * @return interpolated grid data
     */
    public static double[][] interpolation_IDW_Neighbor(double[][] SCoords, double[] X, double[] Y,
            int NumberOfNearestNeighbors, double unDefData) {
        int rowNum, colNum, pNum;
        colNum = X.length;
        rowNum = Y.length;
        pNum = SCoords.length; //sample data length
        double[][] GCoords = new double[rowNum][colNum];
        int i, j, p, l, aP;
        double w, SV, SW, aMin;
        double[] AllWeights = new double[pNum];
        double[][] NW = new double[2][NumberOfNearestNeighbors];//存储K近邻样本权值和权值索引
        int NWIdx;

        //---- Do interpolation with IDW method 
        for (i = 0; i < rowNum; i++) {
            for (j = 0; j < colNum; j++) {
                GCoords[i][j] = unDefData;
                SV = 0;
                SW = 0;
                NWIdx = 0;

                //check
                // if(x[j]-SCoords[pNum-1][1])

                for (p = 0; p < pNum; p++) {//遍历样本数据
                    if (SCoords[p][2] == unDefData) {
                        AllWeights[p] = -1;
                        continue;
                    }
                    if (X[j] == SCoords[p][0] && Y[i] == SCoords[p][1]) {
                        GCoords[i][j] = SCoords[p][2];
                        break;
                    } else {
                        w = 1 / (Math.pow(X[j] - SCoords[p][0], 2) + Math.pow(Y[i] - SCoords[p][1], 2));
                        AllWeights[p] = w;
                        if (NWIdx < NumberOfNearestNeighbors) {//暂时取前NumberOfNearestNeighbors个计算的权重值和索引
                            NW[0][NWIdx] = w;  //权重值
                            NW[1][NWIdx] = p;  //权重值在AllWeights中的索引
                        }
                        NWIdx += 1;
                    }
                }

                if (GCoords[i][j] == unDefData) {
                    for (p = 0; p < pNum; p++) {//将NW中的n个权重替换成AllWeights中n个较大的值
                        w = AllWeights[p];
                        if (w == -1) {
                            continue;
                        }

                        aMin = NW[0][0];
                        aP = 0;
                        for (l = 1; l < NumberOfNearestNeighbors; l++) {//求NW中的最小权重值aMin和索引aP
                            if ((double) NW[0][l] < aMin) {
                                aMin = (double) NW[0][l];
                                aP = l;
                            }
                        }
                        if (w > aMin) {
                            NW[0][aP] = w;
                            NW[1][aP] = p;
                        }
                    }
                    for (p = 0; p < NumberOfNearestNeighbors; p++) {
                        SV += (double) NW[0][p] * SCoords[(int) NW[1][p]][2];//加权值求和
                        SW += (double) NW[0][p]; //权重和
                    }
                    GCoords[i][j] = SV / SW; 
                }
            }
        }

        //---- Smooth with 5 points
        double s = 0.5;
        for (i = 1; i < rowNum - 1; i++) {
            for (j = 1; j < colNum - 1; j++) {
                GCoords[i][j] = GCoords[i][j] + s / 4 * (GCoords[i + 1][j] + GCoords[i - 1][j] + GCoords[i][j + 1]
                        + GCoords[i][j - 1] - 4 * GCoords[i][j]);
            }

        }

        return GCoords;
    }

    /**
     * Interpolation with IDW radius method
     *
     * @param SCoords discrete data array
     * @param X grid X array
     * @param Y grid Y array
     * @param NeededPointNum needed at least point number
     * @param radius search radius
     * @param unDefData undefine data
     * @return interpolated grid data
     */
    public static double[][] interpolation_IDW_Radius(double[][] SCoords, double[] X, double[] Y,
            int NeededPointNum, double radius, double unDefData) {
        int rowNum, colNum, pNum;
        colNum = X.length;
        rowNum = Y.length;
        pNum = SCoords.length;
        double[][] GCoords = new double[rowNum][colNum];
        int i, j, p, vNum;
        double w, SV, SW;
        boolean ifPointGrid;

        //---- Do interpolation
        for (i = 0; i < rowNum; i++) {
            for (j = 0; j < colNum; j++) {
                GCoords[i][j] = unDefData;
                ifPointGrid = false;
                SV = 0;
                SW = 0;
                vNum = 0;
                for (p = 0; p < pNum; p++) {
                    if (SCoords[p][2] == unDefData) {
                        continue;
                    }

                    if (SCoords[p][0] < X[j] - radius || SCoords[p][0] > X[j] + radius || SCoords[p][1] < Y[i] - radius
                            || SCoords[p][1] > Y[i] + radius) {
                        continue;
                    }

                    if (X[j] == SCoords[p][0] && Y[i] == SCoords[p][1]) {
                        GCoords[i][j] = SCoords[p][2];
                        ifPointGrid = true;
                        break;
                    } else if (Math.sqrt(Math.pow(X[j] - SCoords[p][0], 2)
                            + Math.pow(Y[i] - SCoords[p][1], 2)) <= radius) {
                        w = 1 / (Math.pow(X[j] - SCoords[p][0], 2) + Math.pow(Y[i] - SCoords[p][1], 2));
                        SW = SW + w;
                        SV = SV + SCoords[p][2] * w;
                        vNum += 1;
                    }
                }

                if (!ifPointGrid) {
                    if (vNum >= NeededPointNum) {
                        GCoords[i][j] = SV / SW;
                    }
                }
            }
        }

        //---- Smooth with 5 points
        double s = 0.5;
        for (i = 1; i < rowNum - 1; i++) {
            for (j = 1; j < colNum - 2; j++) {
                if (GCoords[i][j] == unDefData || GCoords[i + 1][j] == unDefData || GCoords[i - 1][j]
                        == unDefData || GCoords[i][j + 1] == unDefData || GCoords[i][j - 1] == unDefData) {
                    continue;
                }
                GCoords[i][j] = GCoords[i][j] + s / 4 * (GCoords[i + 1][j] + GCoords[i - 1][j] + GCoords[i][j + 1]
                        + GCoords[i][j - 1] - 4 * GCoords[i][j]);
            }
        }

        return GCoords;
    }

    /**
     * Interpolation with IDW radius method - using KDTree for fast search
     *
     * @param stData discrete data array
     * @param xGrid grid X array
     * @param yGrid grid Y array
     * @param neededPointNum needed at least point number
     * @param radius search radius
     * @param fillValue Fill value
     * @return interpolated grid data
     */
    public static double[][] idw_Radius_kdTree(double[][] stData, double[] xGrid, double[] yGrid, 
            int neededPointNum, double radius, double fillValue) {
        KDTree.Euclidean<double[]> kdtree = new KDTree.Euclidean<>(2);
        int l = stData.length;

        for (int i = 0; i < l; i++) {
            //Avoid key repeat
            double nodex = stData[i][0] + (Math.random() * 10e-5);
            double nodey = stData[i][1] + (Math.random() * 10e-5);
            kdtree.addPoint(new double[]{nodex, nodey}, new double[]{stData[i][0], stData[i][1], stData[i][2]});
        }

        int w = xGrid.length;
        int h = yGrid.length;

        double[][] grid = new double[h][w];
        for (int i = 0; i < h; i++) {
            double yValue = yGrid[i];
            for (int j = 0; j < w; j++) {
                double xValue = xGrid[j];
                List<double[]> nearPntList = kdtree.ballSearch(new double[]{xValue, yValue}, radius);
                int nearSize = nearPntList.size();
                if (nearSize < neededPointNum) {
                    grid[i][j] = fillValue;
                    continue;
                }

                double z_sum = 0.0;
                double weight_sum = 0.0;
                for (int k = 0; k < nearSize; k++) {
                    double[] xyz = nearPntList.get(k);
                    double distance = Point2D.Double.distance(xValue, yValue, xyz[0], xyz[1]);
                    if (distance <= radius && distance > 0) {
                        weight_sum += radius / distance;
                        z_sum += radius / distance * xyz[2];
                    } else if (Math.abs(distance) < 0.0001) {	//Using point value when the grid is point
                        z_sum = xyz[2];
                        weight_sum = 1.0f;
                        break;
                    }
                }
                if (Math.abs(weight_sum) < 0.0001) {
                    grid[i][j] = fillValue;
                } else {
                    grid[i][j] = z_sum / weight_sum;
                }
            }
        }
        return grid;
    }
        
    /**
     * Interpolate from grid data
     *
     * @param GridData input grid data
     * @param X input x coordinates
     * @param Y input y coordinates
     * @param unDefData undefine data
     * @param nX output x coordinate
     * @param nY output y coordinate
     * @return output grid data
     */
    public static double[][] interpolation_Grid(double[][] GridData, double[] X, double[] Y, double unDefData,
            double[] nX, double[] nY) {
        int nxNum = X.length * 2 - 1;
        int nyNum = Y.length * 2 - 1;
        nX = new double[nxNum];
        nY = new double[nyNum];
        double[][] nGridData = new double[nyNum][nxNum];
        int i, j;
        double a, b, c, d;
        List<Double> dList;
        for (i = 0; i < nxNum; i++) {
            if (i % 2 == 0) {
                nX[i] = X[i / 2];
            } else {
                nX[i] = (X[(i - 1) / 2] + X[(i - 1) / 2 + 1]) / 2;
            }
        }
        for (i = 0; i < nyNum; i++) {
            if (i % 2 == 0) {
                nY[i] = Y[i / 2];
            } else {
                nY[i] = (Y[(i - 1) / 2] + Y[(i - 1) / 2 + 1]) / 2;
            }
            for (j = 0; j < nxNum; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    nGridData[i][j] = GridData[i / 2][j / 2];
                } else if (i % 2 == 0 && j % 2 != 0) {
                    a = GridData[i / 2][(j - 1) / 2];
                    b = GridData[i / 2][(j - 1) / 2 + 1];
                    dList = new ArrayList<>();
                    if (a != unDefData) {
                        dList.add(a);
                    }
                    if (b != unDefData) {
                        dList.add(b);
                    }

                    if (dList.isEmpty()) {
                        nGridData[i][j] = unDefData;
                    } else if (dList.size() == 1) {
                        nGridData[i][j] = dList.get(0);
                    } else {
                        nGridData[i][j] = (a + b) / 2;
                    }
                } else if (i % 2 != 0 && j % 2 == 0) {
                    a = GridData[(i - 1) / 2][j / 2];
                    b = GridData[(i - 1) / 2 + 1][j / 2];
                    dList = new ArrayList<>();
                    if (a != unDefData) {
                        dList.add(a);
                    }
                    if (b != unDefData) {
                        dList.add(b);
                    }

                    if (dList.isEmpty()) {
                        nGridData[i][j] = unDefData;
                    } else if (dList.size() == 1) {
                        nGridData[i][j] = dList.get(0);
                    } else {
                        nGridData[i][j] = (a + b) / 2;
                    }
                } else {
                    a = GridData[(i - 1) / 2][(j - 1) / 2];
                    b = GridData[(i - 1) / 2][(j - 1) / 2 + 1];
                    c = GridData[(i - 1) / 2 + 1][(j - 1) / 2 + 1];
                    d = GridData[(i - 1) / 2 + 1][(j - 1) / 2];
                    dList = new ArrayList<>();
                    if (a != unDefData) {
                        dList.add(a);
                    }
                    if (b != unDefData) {
                        dList.add(b);
                    }
                    if (c != unDefData) {
                        dList.add(c);
                    }
                    if (d != unDefData) {
                        dList.add(d);
                    }

                    if (dList.isEmpty()) {
                        nGridData[i][j] = unDefData;
                    } else if (dList.size() == 1) {
                        nGridData[i][j] = dList.get(0);
                    } else {
                        double aSum = 0;
                        for (double dd : dList) {
                            aSum += dd;
                        }
                        nGridData[i][j] = aSum / dList.size();
                    }
                }
            }
        }

        return nGridData;
    }

    // </editor-fold>
    // <editor-fold desc="Others">
    /**
     * Assign point value to grid value
     *
     * @param SCoords point value array
     * @param X x coordinate
     * @param Y y coordinate
     * @param unDefData undefine value
     * @return grid data
     */
    public static double[][] assignPointToGrid(double[][] SCoords, double[] X, double[] Y,
            double unDefData) {
        int rowNum, colNum, pNum;
        colNum = X.length;
        rowNum = Y.length;
        pNum = SCoords.length;
        double[][] GCoords = new double[rowNum][colNum];
        double dX = X[1] - X[0];
        double dY = Y[1] - Y[0];
        int[][] pNums = new int[rowNum][colNum];

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                pNums[i][j] = 0;
                GCoords[i][j] = 0.0;
            }
        }

        for (int p = 0; p < pNum; p++) {
            if (doubleEquals(SCoords[p][2], unDefData)) {
                continue;
            }

            double x = SCoords[p][0];
            double y = SCoords[p][1];
            if (x < X[0] || x > X[colNum - 1]) {
                continue;
            }
            if (y < Y[0] || y > Y[rowNum - 1]) {
                continue;
            }

            int j = (int) ((x - X[0]) / dX);
            int i = (int) ((y - Y[0]) / dY);
            pNums[i][j] += 1;
            GCoords[i][j] += SCoords[p][2];
        }

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                if (pNums[i][j] == 0) {
                    GCoords[i][j] = unDefData;
                } else {
                    GCoords[i][j] = GCoords[i][j] / pNums[i][j];
                }
            }
        }

        return GCoords;
    }

    private static boolean doubleEquals(double a, double b) {
        //if (Math.Abs(a - b) < 0.000001)
        if (Math.abs(a / b - 1) < 0.00000000001) {
            return true;
        } else {
            return false;
        }
    }
    // </editor-fold>
}
