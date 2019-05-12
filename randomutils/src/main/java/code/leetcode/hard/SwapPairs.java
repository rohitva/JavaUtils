package code.leetcode.hard;

import code.leetcode.type.ListNode;
import code.leetcode.type.TreeNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class SwapPairs {
    public static void main(String[] args){
        SwapPairs swapPairs = new SwapPairs();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        System.out.println(head);
        head = swapPairs.swapPairs(head);
        System.out.println(head);
    }

    public ListNode swapPairs(ListNode head) {
        if(head ==null || head.next ==null){
            return head;
        }
        ListNode temp = head.next;
        head.next = head.next.next;
        temp.next = head;
        head = temp;
        temp = temp.next.next;
        ListNode previous = head.next;
        while(temp !=null && temp.next != null){
            previous.next = temp.next;
            ListNode nextTemp = temp.next.next;
            temp.next.next = temp;
            temp.next = nextTemp;
            previous = temp;
            temp = nextTemp;
        }

        return head;
    }


    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> outPut = new ArrayList<>();
        Map<Integer, List<Integer>> heightMap = new TreeMap<>(Comparator.reverseOrder());
        findLeaves(root, 0, heightMap);
        for(int key : heightMap.keySet()){
            outPut.add(heightMap.get(key));
        }
        return outPut;
    }

    public void findLeaves(TreeNode root, int height, Map<Integer, List<Integer>> heightMap) {
        if(root == null){
            return;
        }
        List<Integer> list = heightMap.getOrDefault(height, new ArrayList<>());
        list.add(root.val);
        heightMap.put(height, list);
        findLeaves(root.left, height+1, heightMap);
        findLeaves(root.right, height+1, heightMap);
    }

}
