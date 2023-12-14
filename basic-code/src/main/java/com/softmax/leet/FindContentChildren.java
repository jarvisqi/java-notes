package com.softmax.leet;

import java.util.Arrays;

/**
 * 贪心算法
 *
 * @author Jarvis
 */
public class FindContentChildren {

    /**
     * 孩子满足度，饼干大小
     *
     * @param c
     * @param biscuit
     * @return
     */
    public static int solution(int[] c, int[] biscuit) {
        //贪心法，先排序后从小到大比较
        Arrays.sort(c);
        Arrays.sort(biscuit);
        int i = 0, j = 0;
        int count = 0;
        while (i < c.length && j < biscuit.length) {
            //满足
            if (biscuit[j] >= c[i]) {
                i++;
                j++;
                count++;
            } else {
                //不满足，饼干++
                j++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] g = new int[]{1, 2};
        int[] s = new int[]{1, 2, 3};
        System.out.println(solution(g, s));
    }

}
