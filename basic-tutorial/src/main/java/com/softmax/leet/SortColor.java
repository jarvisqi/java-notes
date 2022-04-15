package com.softmax.leet;

/**
 * 荷兰国旗问题
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
