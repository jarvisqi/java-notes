package com.softmax.design.iterator;

/**
 * @author Jarvis
 */
public class DrivingRecorder {

    /**
     * 当前记录位置
     */
    private int index = -1;
    /**
     * 假设只能记录10条视频
     */
    private String[] records = new String[10];

    public void append(String record) {
        if (index == 9) {
            index = 0;
        } else {
            index++;
        }
        records[index] = record;
    }

    /**
     * 循环数组并显示所有10条记录
     */
    public void display() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + records[i]);
        }
    }

    /**
     * 按顺序从新到旧显示10条记录
     */
    public void displayInOrder() {
        for (int i = index, loopCount = 0; loopCount < 10; i = i == 0 ? i = 9 : i - 1, loopCount++) {
            System.out.println(records[i]);
        }
    }

    public Iterator<String> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<String> {
        // 迭代器游标，不染指原始游标
        int cursor = index;
        int loopCount = 0;

        public boolean hasNext() {
            return loopCount < 10;
        }

        public String next() {
            // 记录即将返回的游标位置
            int i = cursor;
            if (cursor == 0) {
                cursor = 9;
            } else {
                cursor--;
            }
            loopCount++;
            return records[i];
        }

    }
}
