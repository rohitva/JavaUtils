package code.leetcode.medium;

import code.leetcode.type.ListNode;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddTwoNumbers {
    ListNode head = null;
    ListNode tail = null;

    public static void main(String[] args){
        ListNode listNode = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        listNode.next = new ListNode(6);
        listNode.next.next = new ListNode(8);
        listNode2.next = new ListNode(5);
        listNode2.next.next = new ListNode(8);
        listNode2.next.next.next = new ListNode(9);

        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
//        System.out.println(addTwoNumbers.reverseWords("Ram is a Good    Boy  "));

        int[] arr = {1,0,1,1};
        System.out.println(addTwoNumbers.containsNearbyAlmostDuplicate(arr, 1, 2));
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;
        int current = 0;
        while(l1 !=null || l2 !=null){
            current = carry;
            if(l1 !=null){
                current += l1.val;
                l1 = l1.next;
            }
            if(l2 !=null){
                current += l2.val;
                l2 = l2.next;
            }
            carry = current / 10;
            current = current % 10;
            addNode(current);
        }
        if(carry > 0){
            addNode(carry);
        }
        return head;
    }

    //https://leetcode.com/problems/merge-two-sorted-lists/
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        while(l1 !=null || l2 !=null){
           if(l1 !=null && l2 !=null){
               if(l1.val <= l2.val){
                   addNode(l1.val);
                   l1 = l1.next;
               } else {
                   addNode(l2.val);
                   l2 = l2.next;
               }
           } else if(l1 !=null){
               addNode(l1.val);
               l1 = l1.next;
           } else {
               addNode(l2.val);
               l2 = l2.next;
           }
        }
        return head;
    }


    private void addNode(int val) {
        ListNode newNode = new ListNode(val);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = tail.next;
        }
    }

    public String reverseWords(String s) {
        //trim remove all space from start and end.
        //.replaceAll(" +", " "); replace 2 or more space with one.
        String after = s.trim().replaceAll(" +", " ");
        String reverseString = reverseString(after);
        String[] words = reverseString.split(" ");
        StringBuilder output = new StringBuilder();
        for(String word : words){
            output.append(reverseString(word)).append(" ");
        }
        return output.toString().trim();
    }

    private String reverseString(String str){
        StringBuilder stringBuilder = new StringBuilder(str);
        return stringBuilder.reverse().toString();
    }

    public boolean containsDuplicate(int[] nums) {
        if(nums ==null || nums.length ==0){
            return false;
        }
        Set<Integer> inSet = new HashSet<>();
        for(int i=0; i<nums.length;i++){
            if(inSet.contains(nums[i])){
                return true;
            } else {
                inSet.add(nums[i]);
            }
        }
        return false;
    }

    //1,2,3,1 ... K =3
    //1, 0
    //2, 1
    //3, 2
//    public boolean containsNearbyDuplicate(int[] nums, int k) {
//        if(nums ==null || nums.length ==0){
//            return false;
//        }
//        Map<Integer,List<Integer>> numberMap = new HashMap<>();
//        for(int i=0; i<nums.length;i++){
//            List<Integer> previousIndex = numberMap.getOrDefault(nums[i], new ArrayList<>());
//            int currentIndex = previousIndex.size() -1;
//            while(currentIndex >=0){
//                if(i - previousIndex.get(currentIndex) <= k){
//                    return true;
//                } else if(i - previousIndex.get(currentIndex) > k){
//                    break;
//                }
//                currentIndex--;
//            }
//            previousIndex.add(i);
//            numberMap.put(nums[i], previousIndex);
//        }
//        return false;
//    }

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if(nums ==null || nums.length ==0){
            return false;
        }
        Map<Integer,List<Integer>> numberMap = new HashMap<>();
        for(int i=0; i<nums.length;i++){
            int small = nums[i] - t;
            int big = nums[i] + t;
            if(checkFor(numberMap, small, i, k) || checkFor(numberMap, big, i, k)){
                return true;
            }
            List<Integer> previousIndex = numberMap.getOrDefault(nums[i], new ArrayList<>());
            previousIndex.add(i);
            numberMap.put(nums[i], previousIndex);
        }
        return false;
    }

    private boolean checkFor(Map<Integer,List<Integer>> numberMap, int numberChecking, int i, int k){
        List<Integer> previousIndex = numberMap.getOrDefault(numberChecking, new ArrayList<>());
        int currentIndex = previousIndex.size() -1;
        while(currentIndex >=0){
            if(i - previousIndex.get(currentIndex) <= k){
                return true;
            } else if(i - previousIndex.get(currentIndex) > k){
                break;
            }
            currentIndex--;
        }
        return false;
    }



}
