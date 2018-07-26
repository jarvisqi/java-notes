package com.basecode.datastructure;

import java.util.LinkedList;

/**
 * 反转二叉树
 * 4
 * /   \
 * 2     7
 * / \   / \
 * 1   3 6   9
 * 反转后的二叉树：
 * 4
 * /   \
 * 7     2
 * / \   / \
 * 9   6 3   1
 *
 * @author : Jarvis
 * @date : 2018/6/27
 */
public class ReverseBinaryTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);

        TreeNode.printTreeNode(root);
        System.out.println("--------反转---------");

        TreeNode.invertNode(root);
        TreeNode.printTreeNode(root);
    }
}

/**
 * 树
 */
class TreeNode {
    public TreeNode left;
    public TreeNode right;
    public int value;

    public TreeNode(int value) {
        this.value = value;
    }

    /**
     * 反转二叉树
     *
     * @param root
     * @return
     */
    public static TreeNode invertNode(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode temp = root.left;
        root.left = invertNode(root.right);
        root.right = invertNode(temp);

        return root;
    }

    /**
     * 打印二叉树
     */
    public static void printTreeNode(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        TreeNode currentLineRightestNode = new TreeNode(root.value);
        TreeNode nextLineRightestNode = null;

        while (!queue.isEmpty()) {
            //获取并移除队列头部元素，如果队列为空，返回null；
            TreeNode currentNode = queue.poll();

            if (currentNode.left != null) {
                queue.add(currentNode.left);
                nextLineRightestNode = currentNode.left;
            }
            if (currentNode.right != null) {
                queue.add(currentNode.right);
                nextLineRightestNode = currentNode.right;
            }

            System.out.print(currentNode.value);

            if (currentNode.value == currentLineRightestNode.value) {
                System.out.println();
                currentLineRightestNode.value = nextLineRightestNode.value;
            }
        }
    }
}
