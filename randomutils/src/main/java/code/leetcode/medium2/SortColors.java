package code.leetcode.medium2;

public class SortColors {
    public void sortColors(int[] nums) {
        if(nums == null || nums.length <=1){
            return;
        }
        int endIndex = sortColors(nums, nums.length-1, 1);
        if(endIndex >0){
            sortColors(nums, endIndex, 0);
        }
    }

    public int sortColors(int[] nums, int end, int pivot) {
        int start = 0;
        int swapped = 0;
        while (start <= end){
            if(nums[start]<=pivot){
                swap(nums, start, swapped++);
            }
        }
        return swapped-1;
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
