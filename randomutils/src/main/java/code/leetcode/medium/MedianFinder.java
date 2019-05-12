package code.leetcode.medium;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class MedianFinder {
    PriorityQueue<Integer> maxPriorityQueue;
    PriorityQueue<Integer> minPriorityQueue;

    public MedianFinder() {
        maxPriorityQueue = new PriorityQueue<>((x,y) -> y-x);
        minPriorityQueue = new PriorityQueue<>((x,y) -> x-y);

    }

    public void addNum(int num) {
        maxPriorityQueue.add(num);
        minPriorityQueue.add(maxPriorityQueue.poll());
        if(maxPriorityQueue.size() < minPriorityQueue.size()){
            maxPriorityQueue.add(minPriorityQueue.poll());
        }
    }

    public double findMedian() {
        if(maxPriorityQueue.size() == 0){
            return 0.0;
        }
        if(maxPriorityQueue.size()  == minPriorityQueue.size()){
            double value = maxPriorityQueue.peek() + maxPriorityQueue.peek() ;
            return value / 2.0;
        } else {
            return maxPriorityQueue.peek();
        }
    }

}
