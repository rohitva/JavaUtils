package code.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        int[] arr = {1, 2, 3};
        System.out.println(permutations.permute(arr));
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> output = new ArrayList();
        if (nums == null || nums.length == 0) {
            return output;
        }
        permute(nums, 0, nums.length, output);
        return output;
    }

    public void permute(int[] nums, int current, int length, List<List<Integer>> output) {
        if (current == length) {
            //Note: https://howtodoinjava.com/java8/java8-boxed-intstream/
            output.add(Arrays.stream(nums)
                    .parallel()
                    .boxed()
                    .collect(Collectors.toList()));
            return;
        }

        for (int i = current; i < length; i++) {
            swap(nums, current, i);
            permute(nums, current + 1, nums.length, output);
            swap(nums, current, i);
        }
    }

    public void swap(int[] nums, int i, int j) {
        int c = nums[i];
        nums[i] = nums[j];
        nums[j] = c;
    }
}
