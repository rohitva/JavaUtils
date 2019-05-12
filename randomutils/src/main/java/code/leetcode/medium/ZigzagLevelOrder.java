package code.leetcode.medium;

import code.leetcode.type.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZigzagLevelOrder {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> output = new ArrayList<>();
        Map<Integer, List<Integer>> heightMap = new HashMap<>();
        zigzagLevelOrder(root, heightMap, 0);
        int i=0;
        System.out.println(heightMap);
        while (heightMap.containsKey(i)){
            List<Integer> values = heightMap.get(i);
            if(i%2 ==1){
                Collections.reverse(values);
            }
            output.add(values);
            i++;
        }
        return output;
    }

    private void zigzagLevelOrder(TreeNode root, Map<Integer, List<Integer>> heightMap, int currentHeight) {
        if(root ==null){
            return;
        }
        List<Integer> values = heightMap.getOrDefault(currentHeight, new ArrayList<>());
        values.add(root.val);
        heightMap.put(currentHeight, values);
        zigzagLevelOrder(root.left, heightMap, currentHeight +1);
        zigzagLevelOrder(root.right, heightMap, currentHeight +1);
    }

}
