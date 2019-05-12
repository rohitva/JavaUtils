package code.leetcode.medium;

import java.util.Arrays;

public class NextPermutation {
    public static void main(String[] args) {
        int[] arr = {3,1,2};
        NextPermutation nextPermutation = new NextPermutation();
        nextPermutation.nextPermutation(arr);
        System.out.println();
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public void nextPermutation(int[] nums) {
        if(nums == null || nums.length <=1){
            return;
        }
        int j = nums.length -2;
        //Start moving from right and first element which is small than it's right
        while(j>=0 && nums[j] >= nums[j+1]) {
            j--;
        }
        //If no such element then it's biggest number so next can be a sorted order
        if(j<0){
            Arrays.sort(nums);
            return;
        }
        //Otherwise find the first element from the right of this pointer which is bigger than this pointer value
        //Swap that value with pointer value and reverse the all elements right to pointer
        int rightBig = nums.length - 1;
        while(nums[rightBig] <=nums[j]){
            rightBig--;
        }
        //swap
        int temp = nums[rightBig];
        nums[rightBig] = nums[j];
        nums[j] = temp;

        rightBig = nums.length - 1;
        j++;
        while(rightBig > j){
            temp = nums[rightBig];
            nums[rightBig] = nums[j];
            nums[j] = temp;
            j++;
            rightBig--;
        }
    }

}
