package code.leetcode.medium2;

import code.leetcode.type.ListNode;
import code.leetcode.type.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RepeatedDNASequences {
    public static void main(String[] args) throws Exception {
        RepeatedDNASequences repeatedDNASequences = new RepeatedDNASequences();
        int[] arr = {-10, -3, 0, 5, 9};
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        listNode.next.next.next = new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);
        System.out.println(repeatedDNASequences.rotateRight(listNode, 2));
    }

    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> output = new ArrayList<>();
        if (n == 0) {
            return output;
        }
        getFactorList(n, output, Collections.emptyList());
        return output;
    }

    public void getFactorList(int n, List<List<Integer>> output, List<Integer> currnetList) {
        if (n == 1) {
            output.add(currnetList);
            return;
        }
        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                List<Integer> newList = new ArrayList<>(currnetList);
                newList.add(i);
                getFactorList(n / i, output, newList);
            }
        }
    }

    public List<String> findRepeatedDnaSequences(String input) {
        List<String> output = new ArrayList<>();
        Set<String> alreadyChecked = new HashSet<>();
        for (int i = 0; i <= input.length() - 10; i++) {
            String current = input.substring(i, i + 10);
            String pending = input.substring(i + 1);
            if (!alreadyChecked.contains(current)) {
                alreadyChecked.add(current);
                if (pending.contains(current)) {
                    output.add(current);
                }
            }
        }
        System.out.println(output.size());
        return output;
    }

    public TreeNode sortedArrayToBST(int[] num) {
        int len = num.length;
        return sortedArrayToBST(num, 0, len - 1);
    }

    public TreeNode sortedArrayToBST(int[] num, int i, int j) {
        if (i < 0 || j < 0 || j < i) {
            return null;
        }
        if (i == j) {
            TreeNode newNode = new TreeNode(num[i]);
            return newNode;
        } else {
            int mid = (i + j) / 2;
            TreeNode newNode = new TreeNode(num[mid]);
            newNode.left = sortedArrayToBST(num, i, mid - 1);
            newNode.right = sortedArrayToBST(num, mid + 1, j);
            return newNode;
        }
    }

    public boolean isPerfectSquare(int x) {
        if (x == 1) {
            return true;
        }
        long left = 1, right = x;
        while (left < right) {
            long mid = (left + right) / 2;
            if (mid * mid == x) {
                return true;
            } else if (mid * mid > x) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return false;
    }


    public List<List<String>> groupStrings(String[] strings) {
        if (strings == null) {
            return Collections.EMPTY_LIST;
        }
        Map<String, List<String>> keyMap = new HashMap<>();
        for (int i = 0; i < strings.length; i++) {
            String key = getKey(strings[i]);
            keyMap.putIfAbsent(key, new ArrayList<>());
            keyMap.get(key).add(strings[i]);
        }
        return keyMap.values().stream().collect(Collectors.toList());
    }

    private String getKey(String input) {
        if (input == null || input.length() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int diff = input.charAt(0) - 'a';
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) - diff < 'a') {
                stringBuilder.append(input.charAt(i) - diff + 26);
            } else {
                stringBuilder.append(input.charAt(i) - diff);
            }
        }
        return stringBuilder.toString();
    }

    public ListNode plusOne(ListNode head) {
        ListNode newList = reverseList(head);
        ListNode temp = newList;
        ListNode previous = null;
        int carry = 1;
        while (temp != null) {
            int newVal = (carry + temp.val) % 10;
            carry = (carry + temp.val) / 10;
            temp.val = newVal;
            previous = temp;
            temp = temp.next;
        }
        if (carry != 0) {
            previous.next = new ListNode(carry);
        }
        return reverseList(newList);
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode previous = null;
        ListNode current = head;
        ListNode next = head;
        while (current.next != null) {
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        current.next = previous;
        return current;
    }

    public int maxProduct(String[] words) {
        if (words == null || words.length <= 1) {
            return 0;
        }
        Map<String, Set<Character>> stringSetMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            Set<Character> wordSet = new HashSet<>();
            for (int j = 0; j < words[i].length(); j++) {
                wordSet.add(words[i].charAt(j));
            }
            stringSetMap.put(word, wordSet);
        }

        int count = 0;
        for (int i = 0; i < words.length - 1; i++) {
            for (int j = i + 1; j < words.length; j++) {
                count = Math.max(count, maxSetUnique(words[i], words[j], stringSetMap));
            }
        }

        return count;
    }

    private int maxSetUnique(String word1, String word2, Map<String, Set<Character>> stringSetMap) {
        Set<Character> firstSet = stringSetMap.get(word1);
        Set<Character> secondSet = stringSetMap.get(word2);

        long nonunique = firstSet.stream().filter(c -> secondSet.contains(c)).count();
        if (nonunique > 0) {
            return 0;
        } else {
            return word1.length() * word2.length();
        }
    }

    public int hammingDistance(int x, int y) {
        int xorVal = x ^ y;
        int sumOfBits = 0;

        while (xorVal > 0) {
            sumOfBits += xorVal % 2;
            xorVal = xorVal / 2;
        }
        return sumOfBits;
    }

    public String reverseVowels(String s) {
        Set<Character> vowels = new HashSet<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');

        if (s == null || s.length() <= 1) {
            return s;
        }
        int left = 0;
        int right = s.length() - 1;
        char[] output = s.toCharArray();
        while (left < right) {
            if (vowels.contains(s.charAt(left)) && vowels.contains(s.charAt(right))) {
                output[left] = s.charAt(right);
                output[right] = s.charAt(left);
                left++;
                right--;
            } else {
                if (!vowels.contains(s.charAt(left))) {
                    left++;
                }
                if (!vowels.contains(s.charAt(right))) {
                    right--;
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(output);
        return stringBuilder.toString();
    }

    public int lengthOfLastWord(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        String trimmedString = s.trim().replace(" +", " ");
        String[] strArray = trimmedString.split(" ");
        if (strArray.length <= 1) {
            return 0;
        }
        return strArray[strArray.length - 1].length();
    }

    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                swap(i, j++, nums);
            }
        }
        return j;
    }

    private void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        int length = getLength(head);
        k = k % length;
        int move = length - k -1;
        ListNode temp = head;
        while (move > 0 && temp != null) {
            temp = temp.next;
            move--;
        }

        if (temp != null) {
            ListNode nextTemp = temp.next;
            ListNode newHead = temp.next;
            temp.next = null;
            while (nextTemp.next != null) {
                nextTemp = nextTemp.next;
            }
            nextTemp.next = head;
            return newHead;
        }

        return head;
    }

    private int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }


}
