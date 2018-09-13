package code.leetcode.easy;

/**
 * https://leetcode.com/problems/single-number/description/
 * Given a non-empty array of integers, every element appears twice except for one. Find that single one.
 */
public class SingleNumber {
    public int singleNumber(int[] nums) {
        int val = nums[0];
        for (int i = 1; i < nums.length; i++) {
            val = val ^ nums[i];
        }
        return val;
    }
}
