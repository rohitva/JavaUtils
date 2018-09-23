package code.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/subsets/description/
 * Given a set of distinct integers, nums, return all possible subsets (the power set).
 * <p>
 * Note: The solution set must not contain duplicate subsets.
 * <p>
 * Example:
 * <p>
 * Input: nums = [1,2,3]
 * Output:
 * [
 * [3],
 * [1],
 * [2],
 * [1,2,3],
 * [1,3],
 * [2,3],
 * [1,2],
 * []
 * ]
 */
public class Subsets {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> output = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return output;
        }
        iterateSet(0, nums, new ArrayList<>(), output);
        return output;
    }

    public void iterateSet(int currentIndex, int[] nums, List<Integer> currentList, List<List<Integer>> output) {
        if (currentIndex == nums.length) {
            output.add(currentList);
            return;
        }
        iterateSet(currentIndex + 1, nums, currentList, output);
        List<Integer> secondList = new ArrayList<>(currentList);
        secondList.add(nums[currentIndex]);
        iterateSet(currentIndex + 1, nums, secondList, output);
    }

}
