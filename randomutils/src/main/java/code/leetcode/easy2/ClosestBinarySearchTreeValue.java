package code.leetcode.easy2;

import code.leetcode.type.TreeNode;

public class ClosestBinarySearchTreeValue {
    public class NodeIndex {
        double diff = Double.MAX_VALUE;
        int nodeValue;
    }

    public int closestValue(TreeNode root, double target) {
        NodeIndex nodeIndex = new NodeIndex();
        closestValue(root, target, nodeIndex);
        return nodeIndex.nodeValue;
    }

    public void closestValue(TreeNode root, double target, NodeIndex nodeIndex) {
        if(root == null){
            return;
        }
        double diff = Math.abs(target - root.val);
        if(diff < nodeIndex.diff){
            nodeIndex.diff = diff;
            nodeIndex.nodeValue = root.val;
        }
        closestValue(root.left, target, nodeIndex);
        closestValue(root.right, target, nodeIndex);
    }
}
