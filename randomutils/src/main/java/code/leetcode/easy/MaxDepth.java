package code.leetcode.easy;

import code.leetcode.type.TreeNode;

/**
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/
 * Figure out the depth of a binary Tree.
 */
public class MaxDepth {
    public int maxDepth(TreeNode root) {
        return maxDepth(root, 0);
    }

    public int maxDepth(TreeNode root, int currentDepth) {
        if (root == null) {
            return currentDepth;
        }
        int left = maxDepth(root.left, currentDepth + 1);
        int right = maxDepth(root.right, currentDepth + 1);
        return Math.max(left, right);
    }

}
