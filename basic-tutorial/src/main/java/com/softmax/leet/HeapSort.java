package com.softmax.leet;

/**
 * 建立大根堆、小根堆有两种方式，第一种叫heapInsert，称为上浮，
 * 时间复杂度为o(nlogn)， * 第二种为heapify，即下沉，时间复杂度为O(n)
 */
public class HeapSort {

    private static void buildMaxHeap(int[] arr) {
        // int len = arr.length;
        // for(int i = 0; i < len; i++){//heapInsert：向上调整
        //     while(arr[i] > arr[(i - 1) / 2]){
        //         swap(arr, i, (i - 1) / 2);
        //         i = (i - 1) / 2;
        //     }
        // }
        for (int i = arr.length / 2; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

    }

    private static void heapify(int[] arr, int i, int heapSize) {
        // int l = i * 2 + 1;
        // while(l < heapSize){//非递归实现
        //     int large = l + 1 <= heapSize && arr[l] > arr[l + 1] ? l : l + 1;
        //     large = arr[large] > arr[i] ? large : i;
        //     if(large == i) break;//自己最大，退出
        //     swap(arr, large, i);
        //     i = large; //新node
        //     l = i * 2 + 1; //新左儿子
        // }
        //l:左孩子，r：右孩子，i:父节点
        int l = i * 2 + 1, r = i * 2 + 2, largest = i;
        if (l < heapSize && arr[l] > arr[largest]) {
            //先与左孩子比较
            largest = l;
        }
        if (r < heapSize && arr[r] > arr[largest]) {
            //再与右孩子比较
            largest = r;
        }
        //如果孩子节点更大，交换
        if (largest != i) {
            swap(arr, i, largest);
            //递归调整下一个子树
            heapify(arr, largest, heapSize);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 5, 3, 9, 1};
        buildMaxHeap(arr);
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, i, 0);
            //注意这里的heapSize
            heapify(arr, 0, i);
        }
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
