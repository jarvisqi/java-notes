package com.javabase.algorithmdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * 排序算法
 *
 * @author : Jarvis
 * @date : 2018/5/23
 */
public class SortPractice {

    /**
     * 直接插入排序，时间复杂度：O（n^2），空间复杂度：O(1)
     * 稳定
     *
     * @param arr
     */
    public int[] insertSort(int[] arr) {

        int i, j;
        int n = arr.length;
        int target;
        //假定第一个元素被放到了正确的位置上
        //这样，仅需遍历1 - n-1
        for (i = 1; i < n; i++) {
            j = i;
            target = arr[i];
            while (j > 0 && target < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = target;
        }
        return arr;
    }

    /**
     * 希尔排序，时间复杂度：O（n^2），空间复杂度：O(1)
     * 希尔排序是非稳定排序算法
     * 不稳定
     *
     * @param arr 数组
     */
    public int[] sheelSort(int[] arr) {
        //单独把数组长度拿出来，提高效率
        int len = arr.length;
        while (len != 0) {
            len = len / 2;
            //分组
            for (int i = 0; i < len; i++) {
                //元素从第二个开始
                for (int j = i + len; j < arr.length; j += len) {
                    //k为有序序列最后一位的位数
                    int k = j - len;
                    //要插入的元素
                    int temp = arr[j];
                    /*for(;k>=0&&temp<arr[k];k-=len){
                        arr[k+len]=arr[k];
                    }*/
                    //从后往前遍历
                    while (k >= 0 && temp < arr[k]) {
                        arr[k + len] = arr[k];
                        //向后移动len位
                        k -= len;
                    }
                    arr[k + len] = temp;
                }
            }
        }

        return arr;
    }

    /**
     * 冒泡排序：时间复杂度：O（n^2），空间复杂度：O(1)
     * 基本上很少使用
     * 将序列中所有元素两两比较，将最大的放在最后面。
     * 将剩余序列中所有元素两两比较，将最大的放在最后面。
     * 稳定
     *
     * @param arr int 数组
     * @return
     */
    public int[] bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            //注意第二重循环的条件
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    //小的放前面
                    arr[j] = arr[j + 1];
                    //交换
                    arr[j + 1] = tmp;
                }
            }
        }
        return arr;
    }

    /**
     * 简单选择排序 ，时间复杂度：O（n^2），空间复杂度：O(1)
     * 遍历整个序列，将最小的数放在最前面。
     * 遍历剩下的序列，将最小的数放在最前面。
     * 不稳定
     *
     * @param arr
     * @return
     */
    public int[] selectionSort(int[] arr) {
        int len = arr.length;
        //循环次数
        for (int i = 0; i < len; i++) {
            int value = arr[i];
            int position = i;
            //找到最小的值和位置
            for (int j = i + 1; j < len; j++) {
                if (arr[j] < value) {
                    value = arr[j];
                    position = j;
                }
            }
            //进行交换
            arr[position] = arr[i];
            arr[i] = value;
        }
        return arr;
    }

    /**
     * 快速排序，时间复杂度：O (nlogn)，空间复杂度：O(nlog2n)
     * 用时最少
     * 选择第一个数为p，小于p的数放在左边，大于p的数放在右边。
     * 递归的将p左边和右边的数都按照第一步进行，直到不能递归。
     * 不稳定
     *
     * @param arr
     * @return
     */
    public int[] quickSort(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        // 轴值，默认选取数组的第一个数字
        while (left < right) {
            while (left < right && arr[left] <= arr[right]) {
                right--;
            }
            if (left < right) {
                int tmp = arr[left];
                arr[left] = arr[right];
                arr[right] = tmp;
            }

            while (left < right && arr[left] <= arr[right]) {
                left++;
            }
            if (left < right) {
                int tmp = arr[left];
                arr[left] = arr[right];
                arr[right] = tmp;
            }
        }
        return arr;
    }

    /**
     * 归并排序,时间复杂度：O(n log n),空间复杂度：O(n)
     * 速度仅次于快速排序，内存少的时候使用，可以进行并行计算的时候使用。
     * 选择相邻两个数组成一个有序序列。
     * 选择相邻的两个有序序列组成一个有序序列。
     * 重复第二步，直到全部组成一个有序序列。
     * 稳定
     *
     * @return
     */
    public int[] mergeSort(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        int mid = (low + high) / 2;

        if (low < high) {
            // 左边
            sort(arr, low, mid);
            // 右边
            sort(arr, mid + 1, high);
            // 左右归并
            merge(arr, low, mid, high);
        }
        return arr;
    }

    /**
     * 基数排序,时间复杂度：O(d(n+r)),空间复杂度：O(n+r)
     * 用于大量数，很长的数进行排序时。
     * 将所有的数的个位数取出，按照个位数进行排序，构成一个序列。
     * 将新构成的所有的数的十位数取出，按照十位数进行排序，构成一个序列。
     * 稳定
     *
     * @param arr
     * @return
     */
    public int[] baseSort(int[] arr) {
        // 找到最大数，确定要排序几趟
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        // 判断位数
        int times = 0;
        while (max > 0) {
            max = max / 10;
            times++;
        }
        // 建立十个队列
        List<ArrayList> queue = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList queue1 = new ArrayList();
            queue.add(queue1);
        }
        // 进行times次分配和收集
        for (int i = 0; i < times; i++) {
            // 分配
            for (int j = 0; j < arr.length; j++) {
                int x = arr[j] % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                ArrayList queue2 = queue.get(x);
                queue2.add(arr[j]);
                queue.set(x, queue2);
            }
            // 收集
            int count = 0;
            for (int j = 0; j < 10; j++) {
                while (queue.get(j).size() > 0) {
                    ArrayList<Integer> queue3 = queue.get(j);
                    arr[count] = queue3.get(0);
                    queue3.remove(0);
                    count++;
                }
            }
        }
        return arr;
    }
    

    private static void sort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(arr, left, mid);
            sort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        // 左指针
        int i = low;
        // 右指针
        int j = mid + 1;
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[i++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = arr[j++];
        }
        // 把新数组中的数覆盖arr数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            arr[k2 + low] = temp[k2];
        }
    }


}
