package code.leetcode.medium;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ContainsDuplicateThird {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length == 0 || k < 0 || t < 0) {
            return false;
        }
        TreeSet<Long> valueSet = new TreeSet<Long>();
        int i = 0;
        //Let's create the size K window
        while (i <= k && i < nums.length) {
            long currentNumber = nums[i];
            if (isValidEntry(valueSet, t, currentNumber)) {
                return true;
            }
            valueSet.add(currentNumber);
            i++;
        }
        //After the window we need to remove one while adding one
        int j = 0; //Start removing from start.
        while (i < nums.length) {
            valueSet.remove((long) nums[j]);
            long currentNumber = nums[i];
            if (isValidEntry(valueSet, t, currentNumber)) {
                return true;
            }
            valueSet.add(currentNumber);
            i++;
            j++;
        }


        return false;
    }

    private boolean isValidEntry(TreeSet<Long> valueSet, int t, long currentNumber) {
        if (valueSet.subSet(currentNumber - t, currentNumber + t + 1).size() > 0) {
            return true;
        }
        return false;
    }

    public int lengthOfLongestSubstring(String input) {
        if(input ==null || input.length() == 0){
            return 0;
        }
        int maxSize =0;
        Set<Character> charSet = new HashSet<>();
        int j=0;
        for(int i=0;i<input.length();i++){
            char c = input.charAt(i);
            while (charSet.contains(c)){
                charSet.remove(input.charAt(j));
                j++;
            }
            charSet.add(input.charAt(i));
            if(charSet.size() > maxSize){
                maxSize = charSet.size();
            }
        }

        StringBuilder sb = new StringBuilder();
        return maxSize;
    }


}
