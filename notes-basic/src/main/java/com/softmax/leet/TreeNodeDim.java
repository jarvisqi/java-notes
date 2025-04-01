package com.softmax.leet;

public class TreeNodeDim {

    int val;
    TreeNodeDim left;
    TreeNodeDim right;

    TreeNodeDim() {
    }

    TreeNodeDim(int val) {
        this.val = val;
    }

    TreeNodeDim(int val, TreeNodeDim left, TreeNodeDim right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    int max = 0;

    public int diameterOfBinaryTree(TreeNodeDim root) {
        if (root == null) {
            return 0;
        }
        dfs(root);
        return max;
    }

    private int dfs(TreeNodeDim root) {
        if (root.left == null && root.right == null) {
            return 0;
        }
        int left = root.left == null ? 0 : dfs(root.left) + 1;
        int right = root.right == null ? 0 : dfs(root.right) + 1;
        max = Math.max(max, left + right);
        return Math.max(left, right);
    }
}
