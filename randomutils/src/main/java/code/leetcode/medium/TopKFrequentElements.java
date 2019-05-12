package code.leetcode.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentElements {
    public static void main(String[] args) {
        TopKFrequentElements topKFrequentElements = new TopKFrequentElements();
        int[] arr = {1,1,1,2,2,3,4,5,5,5,5,7};
        System.out.println(topKFrequentElements.topKFrequent(arr, 2));
        LinkedList<Integer> deque = new LinkedList<>();
        deque.addFirst(20);
        deque.addLast(30);
        deque.peekFirst();
        deque.peekLast();
        deque.pollFirst();
        deque.pollLast();
    }

    public static class ItemFrequency{
        int number, frequency;
        public ItemFrequency(int number, int frequency){
            this.number = number;
            this.frequency = frequency;
        }
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> output = new ArrayList<>();
        if(nums ==null || nums.length ==0 || k<=0){
            return output;
        }
        Map<Integer,Integer> frequencyMap = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            Integer currentCount =  frequencyMap.getOrDefault(nums[i], 0);
            frequencyMap.put(nums[i], currentCount+1);
        }
        PriorityQueue<ItemFrequency> pQueue = new PriorityQueue<>((i1, i2) -> i1.frequency - i2.frequency);
        for(int key : frequencyMap.keySet()){
            if(pQueue.size() <k){
                pQueue.add(new ItemFrequency(key, frequencyMap.get(key)));
            } else {
                if(pQueue.peek().frequency < frequencyMap.get(key)){
                    pQueue.poll();
                    pQueue.add(new ItemFrequency(key, frequencyMap.get(key)));
                }
            }
        }
        while(pQueue.size()>0){
            output.add(pQueue.poll().number);
        }
        return output;
    }

}
