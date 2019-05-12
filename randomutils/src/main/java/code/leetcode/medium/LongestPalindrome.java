package code.leetcode.medium;

/**
 * Treat each point as mid point of the string and try to get biggest Palindrome from that point.
 * Also don't forget to consider the even and odd both length.
 * A side note - Either use StringBuilder and if can't even str.substring() is better than adding character by character.
 */
public class LongestPalindrome {
    public static void main(String[] args) {

    }


    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }
        String longestPalindrome = s.substring(0, 1);
        for (int i = 0; i < s.length(); i++) {
            String middleOdd = getLongestSubStringMiddlePoint(s, i, i);
            if (middleOdd.length() > longestPalindrome.length()) {
                longestPalindrome = middleOdd;
            }
            String middleEven = getLongestSubStringMiddlePoint(s, i, i + 1);
            if (middleEven.length() > longestPalindrome.length()) {
                longestPalindrome = middleOdd;
            }
        }
        return longestPalindrome;
    }

    private String getLongestSubStringMiddlePoint(String s, int middlePoint, int anotherMiddle) {
        while (middlePoint >= 0 && anotherMiddle < s.length() && s.charAt(middlePoint) == s.charAt(anotherMiddle)) {
            middlePoint--;
            anotherMiddle++;
        }
        return s.substring(middlePoint + 1, anotherMiddle);
    }
}
