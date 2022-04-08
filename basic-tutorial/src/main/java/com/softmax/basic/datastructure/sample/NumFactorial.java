package com.softmax.basic.datastructure.sample;

/**
 * ,
 * n! = n*(n-1)*(n-2)*......1
 *
 * @author Jarvis
 */
public class NumFactorial {
    /**
     * 0！=1  1！=1
     * 负数没有阶乘,如果输入负数返回-1  for循环方式
     *
     * @param n
     * @return
     */
    public static int getFactorialFor(int n) {
        int temp = 1;
        if (n >= 0) {
            for (int i = 1; i <= n; i++) {
                temp = temp * i;
            }
        } else {
            return -1;
        }
        return temp;
    }

    /**
     * 0！=1  1！=1
     * 负数没有阶乘,如果输入负数返回-1 递归形式
     *
     * @param n
     * @return
     */
    public static int getFactorial(int n) {
        if (n >= 0) {
            if (n == 0) {
                System.out.println(n + "!=1");
                return 1;
            } else {
                System.out.println(n);
                int temp = n * getFactorial(n - 1);
                System.out.println(n + "!=" + temp);
                return temp;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int factorialFor = getFactorialFor(4);
        System.out.println(factorialFor);

        int factorial = getFactorial(4);

    }
}
