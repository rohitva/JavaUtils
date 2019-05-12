package code.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

/**
 * Beautiful line - frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) +1);
 */
public class TwoSum {
    Map<Integer, Integer> frequencyMap;
    /** Initialize your data structure here. */
    public TwoSum() {
        frequencyMap = new HashMap<>();
    }

    /** Add the number to an internal data structure.. */
    public void add(int number) {
        frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) +1);
    }

    /** Find if there exists any pair of numbers which sum is equal to the value. */
    public boolean find(int value) {
        for(int fistValue : frequencyMap.keySet()){
            int secondValue = value - fistValue;
            if(fistValue != secondValue){
                if(frequencyMap.containsKey(secondValue)){
                    return true;
                }
            } else {
                if(frequencyMap.get(fistValue) > 1){
                    return true;
                }
            }
        }
        return false;
    }

    public int numWays(int n, int k) {
        return (int) Math.pow(k-1, n-1) * k;
    }


    public static void main(String[] args) {

    }
}
