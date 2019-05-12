package code.leetcode.medium;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/group-anagrams/description/
 * Given an array of strings, group anagrams together.
 * <p>
 * Example:
 * <p>
 * Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
 * Output:
 * [
 * ["ate","eat","tea"],
 * ["nat","tan"],
 * ["bat"]
 * ]
 * <p>
 * Note:
 * <p>
 * All inputs will be in lowercase.
 * The order of your output does not matter.
 */
public class GroupAnagrams {
    public static void main(String[] args) {
        GroupAnagrams groupAnagrams = new GroupAnagrams();
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> output = new ArrayList<>();
        if (strs == null || strs.length == 0) {
            return output;
        }
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            String key = getKey(strs[i]);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(strs[i]);
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            output.add(entry.getValue());
        }
        return output;
    }

    //The default solution is sort.
    private String getKey(String str) {
        if (StringUtils.isBlank(str)) {
            return StringUtils.EMPTY;
        }
        int[] arr = new int[26];
        char[] chars = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            //Note: This is important
            arr[chars[i] - 'a']++;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            //Note: Adding + to make a difference between abb and 12 times a
            builder.append(arr[i] + "+");
        }
        return builder.toString();
    }

}
