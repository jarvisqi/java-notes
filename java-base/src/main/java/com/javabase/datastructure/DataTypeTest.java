package com.javabase.datastructure;

/**
 * @author Jarvis
 * @date 2018/7/25
 */
public class DataTypeTest {

    public static void main(String[] args) {

        short s1 = 1;
        //由于1是int类型，因此s1+1运算结果也是int 型，需要强制转换类型才能赋值给short型
        s1 = (short) (s1 + 1);
        //相当于s1 = (short)(s1 + 1);其中有隐含的强制类型转换
        s1 += 1;

        //12
        System.out.println(Math.round(11.5));
        //-11
        System.out.println(Math.round(-11.5));

        //expr类型：byte、short、char、int、enum、String
        int expr = 0;
        switch (expr) {
            case 0:
                System.out.println(expr);
                break;
            default:
                System.out.println("default");
        }
    }


}
