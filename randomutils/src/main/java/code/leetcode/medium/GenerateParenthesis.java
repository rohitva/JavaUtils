package code.leetcode.medium;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/generate-parentheses
 *  Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 *
 * For example, given n = 3, a solution set is:
 *
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 */
public class GenerateParenthesis {
    public static void main(String[] args) {
        GenerateParenthesis generateParenthesis = new GenerateParenthesis();
        System.out.println(generateParenthesis.generateParenthesis(3));
    }

    public List<String> generateParenthesis(int n) {
        List<String> output = new ArrayList();
        if (n <= 0) {
            return output;
        }
        char[] str = new char[n * 2];
        generateParenthesis(n, str, 0, 0, 0, output);
        return output;
    }

    public void generateParenthesis(int n, char[] str, int leftCount, int rightCount, int currentIndex, List<String> output) {
        if (leftCount == n && rightCount == n) {
            output.add(new String(str));
            return;
        }
        if(leftCount ==n){
            str[currentIndex] = ')';
            generateParenthesis(n, str, leftCount, rightCount+1, currentIndex + 1, output);
            return;
        }
        if (leftCount == rightCount) {
            str[currentIndex] = '(';
            generateParenthesis(n, str, leftCount + 1, rightCount, currentIndex + 1, output);
        } else {
            str[currentIndex] = '(';
            generateParenthesis(n, str, leftCount + 1, rightCount, currentIndex + 1, output);
            str[currentIndex] = ')';
            generateParenthesis(n, str, leftCount, rightCount + 1, currentIndex + 1, output);
        }
    }
}
