package code.leetcode.medium2;

import code.leetcode.type.ListNode;

import java.util.HashMap;
import java.util.Map;

//1->2->3-4->5->6->7
public class OddEvenList {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
//        head.next.next.next.next.next = new ListNode(6);
        OddEvenList oddEvenList = new OddEvenList();
//        System.out.println(oddEvenList.oddEvenList(head));
//        System.out.println(oddEvenList.longestPalindrome("abccccddABHJGHJFGHJKIBBJAadhgjjiksifhrtilsjFHDJAWEitu"));
        System.out.println(oddEvenList.wordPattern("abba", "dog cat cat dog"));
    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode oddHead = head;
        ListNode eventHead = head.next;
        ListNode oddTemp = oddHead;
        ListNode evenTemp = oddHead.next;
        while (oddTemp != null && evenTemp != null) {
            if (oddTemp.next != null) {
                oddTemp.next = oddTemp.next.next;
            }
            if (evenTemp.next != null) {
                evenTemp.next = evenTemp.next.next;
            }
            oddTemp = oddTemp.next;
            evenTemp = evenTemp.next;
        }
        oddTemp = oddHead;
        while (oddTemp.next != null) {
            oddTemp = oddTemp.next;
        }
        oddTemp.next = eventHead;
        return oddHead;
    }

    public int longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> charMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            charMap.put(s.charAt(i), charMap.getOrDefault(s.charAt(i), 0) + 1);
        }
        int count = 0;
        int oddCount = 0;
        for (Character c : charMap.keySet()) {
            if (charMap.get(c) % 2 == 0) {
                count += charMap.get(c);
            } else {
                count += charMap.get(c) - 1;
                oddCount++;
            }
        }
        if (oddCount > 0) {
            count++;
        }
        return count;
    }


    public boolean wordPattern(String pattern, String str) {
        String[] strings = str.split(" ");
        if (pattern.length() != strings.length) {
            return false;
        }
        Map<Character, String> characterStringMap = new HashMap<>();
        Map<String, Character> stringCharacterHashMap = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (!characterStringMap.containsKey(c) && !stringCharacterHashMap.containsKey(strings[i])) {
                characterStringMap.put(c, strings[i]);
                stringCharacterHashMap.put(strings[i], c);
            } else if (characterStringMap.containsKey(c) && stringCharacterHashMap.containsKey(strings[i])) {
                if (c != stringCharacterHashMap.get(strings[i]) || !characterStringMap.get(c).equals(strings[i])) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }


}
