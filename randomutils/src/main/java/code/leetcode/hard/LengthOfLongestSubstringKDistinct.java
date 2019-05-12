package code.leetcode.hard;

import java.util.HashMap;
import java.util.Map;

public class LengthOfLongestSubstringKDistinct {
    public static void main(String[] args) {
        String input = "aabbbbbbcdddddefgtadrfsdf";
        LengthOfLongestSubstringKDistinct longestSubstringKDistinct = new LengthOfLongestSubstringKDistinct();
        System.out.println(longestSubstringKDistinct.lengthOfLongestSubstringKDistinct(input, 1));
    }

    public int lengthOfLongestSubstringTwoDistinct(String s) {
        return lengthOfLongestSubstringKDistinct(s,2);
    }

    public int lengthOfLongestSubstringKDistinct(String input, int k) {
        if(input == null || input.length() ==0 || k<=0){
            return 0;
        }
        if(input.length() <=k){
            return input.length();
        }
        Map<Character, Integer> frequencyMap = new HashMap<>();
        int j=0;
        int maxCount =0;
        for(int i=0;i<input.length();i++){
            Integer currentCount = frequencyMap.getOrDefault(input.charAt(i), 0);
            frequencyMap.put(input.charAt(i), currentCount+1);
            while (frequencyMap.size() >k && j<=i){
                 currentCount = frequencyMap.getOrDefault(input.charAt(j), 0);
                 if(currentCount == 1){
                     frequencyMap.remove(input.charAt(j));
                 } else {
                     frequencyMap.put(input.charAt(j), currentCount-1);
                 }
                 j++;
            }
            maxCount = Math.max(maxCount, i-j+1);
        }
        return maxCount;
    }

}