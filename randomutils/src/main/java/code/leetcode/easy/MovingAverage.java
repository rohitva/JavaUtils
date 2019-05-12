package code.leetcode.easy;

import java.util.LinkedList;

public class MovingAverage {
    LinkedList<Integer> list;
    int size;
    long currentSum =0;
    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        this.list = new LinkedList<Integer>();
        this.size = size;
    }

    public double next(int val) {
        currentSum += val;
        list.addLast(val);
        if(list.size() > size){
            currentSum -= list.pollFirst();
        }
        return 1.0 * currentSum / list.size();
    }


    public static void main(String[] args) {

    }
}
