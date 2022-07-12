package com.softmax.leet;

/**
 * 荷兰国旗问题
 * 荷兰国旗是由红白蓝3种颜色的条纹拼接而成,假设这样的条纹有多条，且各种颜色的数量不一，并且随机组成了一个新的图形
 * 把这些条纹按照颜色排好，红色的在上半部分，白色的在中间部分，蓝色的在下半部分，我们把这类问题称作荷兰国旗问题。
 */
public class SortColor {
    private static void sort(int[] arr) {
        int less = -1;
        int more = arr.length;
        int index = 0;
        while (index < more) {
            if (arr[index] == 0) {
                swap(arr, ++less, index++);
            } else if (arr[index] == 2) {
                swap(arr, --more, index);
            } else {
                index++;
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 2, 1, 0, 0};
        sort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

}
