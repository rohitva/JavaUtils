package code.leetcode.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Map;

/**
 * It's a good to read problem.
 * We can do it O(NlogK) using a priority Queue.
 * This textbook algorthm has O(N)\mathcal{O}(N)O(N) average time complexity. Like quicksort,
 * it was developed by Tony Hoare, and is also known as Hoare's selection algorithm. Kth largest is N-K th smallest
 * [1] To implement partition one moves along an array, compares each element with a pivot,
 * and moves all elements smaller than pivot to the left of the pivot.
 * [2] Next step is to search only a part of the remaining array
 * Complexity is O(N) =~ N+N/2+N/4+N/8 ...1 which is 2N-1
 */
public class FindKthLargest {
    public static void main(String[] args) {
        int[] arr = {3};
        FindKthLargest findKthLargest = new FindKthLargest();
        System.out.println(findKthLargest.findKthLargest(arr, 1));
        List<Integer> list = new ArrayList<>();
        list.add(0,10);

    }

    public int findKthLargest(int[] nums, int k) {
        return findKthLargest(nums, nums.length - k + 1, 0, nums.length - 1);

    }

    public int findKthLargest(int[] nums, int k, int start, int end) {
        int pivot = nums[end];
        int i = start, j = start;
        while (j < end) {
            if (nums[j] <= pivot) {
                swap(i++, j, nums);
            }
            j++;
        }
        swap(i, j, nums);
        int countLeft = i - start + 1; //(including i)
        if (countLeft == k) {
            return nums[i];
        } else if (countLeft > k) {
            return findKthLargest(nums, k, start, i - 1);
        } else {
            return findKthLargest(nums, k - countLeft, i + 1, end);
        }
    }

    private void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    //Priority Queue based NLogK solution
//    public int findKthLargest(int[] nums, int k) {
//        PriorityQueue<Integer> employeePriorityQueue = new PriorityQueue<>((x, y) -> x-y);
//        for(int i=0; i<nums.length;i++){
//            if(i<k){
//                employeePriorityQueue.add(nums[i]);
//            } else {
//                if(employeePriorityQueue.peek() < nums[i]){
//                    employeePriorityQueue.poll();
//                    employeePriorityQueue.add(nums[i]);
//                }
//            }
//        }
//        return employeePriorityQueue.peek();
//    }

}
