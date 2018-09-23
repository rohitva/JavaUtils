package code.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/maximum-product-of-word-lengths/description/
 * Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where the two words do not share common letters. You may assume that each word will contain only lower case letters. If no such two words exist, return 0.
 * <p>
 * Example 1:
 * <p>
 * Input: ["abcw","baz","foo","bar","xtfn","abcdef"]
 * Output: 16
 * Explanation: The two words can be "abcw", "xtfn".
 * <p>
 * Example 2:
 * <p>
 * Input: ["a","ab","abc","d","cd","bcd","abcd"]
 * Output: 4
 * Explanation: The two words can be "ab", "cd".
 * <p>
 * Example 3:
 * <p>
 * Input: ["a","aa","aaa","aaaa"]
 * Output: 0
 * Explanation: No such pair of words.
 */
public class MaximumProductOfWordLengths {
    public int maxProduct(String[] words) {
        if (words == null || words.length <= 1) {
            return 0;
        }
        List<boolean[]> keysList = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            boolean[] arr = new boolean[26];
            char[] chars = words[i].toCharArray();
            for (int j = 0; j < words[i].length(); j++) {
                //Note: This is important
                arr[chars[j] - 'a'] = true;
            }
            keysList.add(arr);
        }

        int max = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                boolean[] firstArray = keysList.get(i);
                boolean[] secondArray = keysList.get(j);
                boolean unique = true;
                for (int k = 0; k < 26; k++) {
                    if (firstArray[k] && secondArray[k]) {
                        unique = false;
                        break;
                    }
                }
                if (unique) {
                    max = Math.max(max, words[i].length() * words[j].length());
                }
            }
        }

        return max;
    }
}
