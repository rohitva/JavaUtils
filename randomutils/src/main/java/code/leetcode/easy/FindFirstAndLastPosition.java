package code.leetcode.easy;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FindFirstAndLastPosition {
    public static void main(String[] args) {
        FindFirstAndLastPosition findFirstAndLastPosition = new FindFirstAndLastPosition();
        int[] arr = {5,7,7,8,8,8,8,8,8,8,8,8,8,8,10};
        System.out.println(Arrays.stream(findFirstAndLastPosition.searchRange(arr, 10))
                .boxed().collect(Collectors.toList()));
    }

    public int[] searchRange(int[] nums, int target) {
        int[] output = new int[2];
        output[0] = searchLeftMost(nums, target);
        output[1] = searchRightMost(nums, target);
        return output;
    }


    public int searchLeftMost(int[] nums, int target){
        int search = search(nums, target, 0, nums.length-1);
        if(search == -1){
            return -1;
        }
        while(search > 0 && nums[search-1] == target){
            search = search(nums, target, 0, search-1);
        }
        return search;
    }

    public int searchRightMost(int[] nums, int target){
        int search = search(nums, target, 0, nums.length-1);
        if(search == -1){
            return -1;
        }
        while(search < nums.length-1 && nums[search+1] == target){
            search = search(nums, target, search+1, nums.length-1);
        }
        return search;
    }

    public int search(int[] nums, int target, int firstIndex, int rightIndex){
        if(nums == null || nums.length ==0){
            return -1;
        }
        int left = firstIndex, right = rightIndex;
        while(left <= right){
            if(left == right){
                if(nums[left] ==target){
                    return left;
                } else {
                    return -1;
                }
            }
            int mid = (left + right) /2;
            if(nums[mid] == target){
                return mid;
            } else if(nums[mid] > target){
                right = mid -1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }


}
