package com.softmax.leet;

public class TreeNodeDia {

    int val;
    TreeNodeDia left;
    TreeNodeDia right;

    TreeNodeDia() {
    }

    TreeNodeDia(int val) {
        this.val = val;
    }

    TreeNodeDia(int val, TreeNodeDia left, TreeNodeDia right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    int max = 0;

    public int diameterOfBinaryTree(TreeNodeDia root) {
        if (root == null) {
            return 0;
        }
        dfs(root);
        return max;
    }

    private int dfs(TreeNodeDia root) {
        if (root.left == null && root.right == null) {
            return 0;
        }
        int left = root.left == null ? 0 : dfs(root.left) + 1;
        int right = root.right == null ? 0 : dfs(root.right) + 1;
        max = Math.max(max, left + right);
        return Math.max(left, right);
    }
}
