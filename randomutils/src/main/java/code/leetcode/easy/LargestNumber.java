package code.leetcode.easy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LargestNumber {
    public static void main(String[] args) {
        int[] arr = {3,30,34,5,9};
        LargestNumber largestNumber = new LargestNumber();
        System.out.println(largestNumber.largestNumber(arr));
    }

    //Input: [3,30,34,5,9]
    //Output: "9534330"
    public String largestNumber(int[] nums) {
        List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
        Collections.sort(list, (x, y) -> {
            Long rightLeft = Long.parseLong(String.valueOf(y) + (String.valueOf(x)));
            Long leftRight = Long.parseLong(String.valueOf(x) + (String.valueOf(y)));
            if(leftRight > rightLeft){
                return -1;
            } else {
                return 1;
            }

        });
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

}
