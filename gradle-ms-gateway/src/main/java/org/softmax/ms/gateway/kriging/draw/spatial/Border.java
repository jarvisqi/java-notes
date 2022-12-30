/*
 * Copyright 2012 Yaqiang Wang,
 * yaqiang.wang@gmail.com
 */
package org.softmax.ms.gateway.kriging.draw.spatial;

import java.util.ArrayList;
import java.util.List;

/**
 * Border class - contour line border
 *
 * @author Yaqiang Wang
 * @version $Revision: 1.6 $
 */
public class Border {
    public List<BorderLine> LineList = new ArrayList<>();
    
    /**
     * Constructor
     */
    public Border()
    {
        
    }
    
    /**
     * Get line number
     * @return Line number
     */
    public int getLineNum(){
        return LineList.size();
    }
            
}
