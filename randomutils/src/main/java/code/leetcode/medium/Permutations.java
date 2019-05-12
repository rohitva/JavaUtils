package code.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/permutations/description/
 * Given a collection of distinct integers, return all possible permutations.
 * <p>
 * Example:
 * <p>
 * Input: [1,2,3]
 * Output:
 * [
 * [1,2,3],
 * [1,3,2],
 * [2,1,3],
 * [2,3,1],
 * [3,1,2],
 * [3,2,1]
 * ]
 */
public class Permutations {
    public static void main(String[] args) {
        Permutations permutations = new Permutations();
        int[] arr = {1, 1, 3};
        System.out.println(permutations.permute(arr));
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> output = new ArrayList();
        Set<List<Integer>> outputSet = new HashSet<>();
        if (nums == null || nums.length == 0) {
            return output;
        }
        permute(nums, 0, nums.length, output, outputSet);
        return output;
    }

    public void permute(int[] nums, int current, int length, List<List<Integer>> output, Set<List<Integer>> outputSet) {
        if (current == length) {
            //Note: https://howtodoinjava.com/java8/java8-boxed-intstream/
            List<Integer> newList = Arrays.stream(nums)
                    .parallel()
                    .boxed()
                    .collect(Collectors.toList());
            if (!outputSet.contains(newList)) {
                output.add(newList);
                outputSet.add(newList);
            }
            return;
        }

        for (int i = current; i < length; i++) {
            swap(nums, current, i);
            permute(nums, current + 1, nums.length, output, outputSet);
            swap(nums, current, i);
        }
    }

    public void swap(int[] nums, int i, int j) {
        int c = nums[i];
        nums[i] = nums[j];
        nums[j] = c;
    }

    public boolean wordPatternMatch(String pattern, String str) {
        if (pattern == null && str == null) {
            return true;
        }
        if (pattern == null || str == null) {
            return false;
        }
        if (pattern.length() == 0 && str.length() == 0) {
            return true;
        }
        if (pattern.length() == 0 || str.length() == 0 || pattern.length() > str.length()) {
            return false;
        }

        Map<Character, String> characterStringMap = new HashMap<>();

        return findWordPatternMatch(pattern, str, 0, 0, characterStringMap);
    }

    private boolean findWordPatternMatch(String pattern, String str, int patternIndex, int strIndex,
                                         Map<Character, String> charMap) {
        //See the end condition.
        if (patternIndex == pattern.length() && strIndex == str.length()) {
            return true;
        }
        if (patternIndex >= pattern.length() || strIndex >= str.length()) {
            return false;
        }
        char c = pattern.charAt(patternIndex);
        for(int j=strIndex+1;j<=str.length();j++){
            String subString = str.substring(strIndex, j);
            if(!charMap.containsKey(c) && !charMap.containsValue(subString)){
                charMap.put(c, subString);
                if(findWordPatternMatch(pattern, str, patternIndex+1, j, charMap)){
                    return true;
                }
                charMap.remove(c);
            } else {
                if(charMap.containsKey(c) && charMap.get(c).equals(subString)){
                    return findWordPatternMatch(pattern, str, patternIndex+1, j, charMap);
                }
            }
        }
        return false;
    }


}
