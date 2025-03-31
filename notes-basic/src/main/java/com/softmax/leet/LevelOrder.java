package com.softmax.leet;

import java.util.*;

/**
 * 二叉树的层次遍历
 */
public class LevelOrder {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {

        //-1代表null
        int[] arr = {3, 9, 20, -1, -1, 15, 7};

        TreeNode root = buildTree(arr, 0);

        List<List<Integer>> ans = solution(root);
        Iterator<List<Integer>> iterator = ans.iterator();
        while (iterator.hasNext()) {
            Iterator<Integer> ite = iterator.next().iterator();
            while (ite.hasNext()) {
                System.out.print(ite.next() + " ");
            }
            System.out.println();
        }
    }

    /**
     * 根据数组建立二叉树的函数
     *
     * @param arr
     * @param index
     * @return
     */
    private static TreeNode buildTree(int[] arr, int index) {
        // 根据数组建立二叉树
        if (index >= arr.length) {
            return null;
        }
        if (arr[index] == -1) {
            return null;
        }
        TreeNode ans = new TreeNode(arr[index]);
        // 递归建立左子树
        ans.left = buildTree(arr, index * 2 + 1);
        // 递归建立右子树
        ans.right = buildTree(arr, index * 2 + 2);
        return ans;
    }

    private static List<List<Integer>> solution(TreeNode root) {
        // 存储每层节点的列表
        List<List<Integer>> ans = new ArrayList<>();
        // 使用队列进行层次遍历
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        // 当队列不为空时，进行循环
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> temp = new ArrayList<>();
            while (size > 0) {
                TreeNode tempNode = queue.poll();
                temp.add(tempNode.val);
                if (tempNode.left != null) {
                    queue.offer(tempNode.left);
                }
                if (tempNode.right != null) {
                    queue.offer(tempNode.right);
                }
                size--;
            }
            ans.add(temp);
        }
        return ans;
    }
}
