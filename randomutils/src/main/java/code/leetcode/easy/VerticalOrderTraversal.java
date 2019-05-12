package code.leetcode.easy;

import code.leetcode.type.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VerticalOrderTraversal {
    public static void main(String[] args) {

    }

    public class SavedNode {
        TreeNode node;
        int index;
        public SavedNode(TreeNode node, int index){
            this.node = node;
            this.index = index;
        }
    }
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> output = new LinkedList<>();
        if(root == null){
            return output;
        }
        Map<Integer, List<Integer>> valueMap = new TreeMap<>();

        LinkedList<SavedNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(new SavedNode(root, 0));
        while (!nodeQueue.isEmpty()){
            SavedNode currentNode = nodeQueue.poll();
            List<Integer> currentList = valueMap.getOrDefault(currentNode.index, new ArrayList<>());
            currentList.add(currentNode.node.val);
            valueMap.put(currentNode.index, currentList);
            if(currentNode.node.left !=null){
                nodeQueue.add(new SavedNode(currentNode.node.left, currentNode.index-1));
            }
            if(currentNode.node.right !=null){
                nodeQueue.add(new SavedNode(currentNode.node.right, currentNode.index+1));
            }
        }

        for(Integer key : valueMap.keySet()){
            output.add(valueMap.get(key));
        }
        return output;
    }

}
