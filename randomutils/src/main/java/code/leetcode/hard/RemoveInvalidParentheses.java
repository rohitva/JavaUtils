package code.leetcode.hard;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RemoveInvalidParentheses {

    public static void main(String[] args) {
        RemoveInvalidParentheses removeInvalidParentheses = new RemoveInvalidParentheses();

        System.out.println(removeInvalidParentheses.removeInvalidParentheses(")"));
    }

    public class LeftRightCount {
        int leftCount;
        int rightCount;
        public LeftRightCount(int leftCount, int rightCount){
            this.leftCount = leftCount;
            this.rightCount = rightCount;
        }
    }
    /**
     * If we see a left there are chances we will see corresponding right later.
     * Anytime we see extra right that need to be gone.
     * In the end if there are extra left those also need to be gone.
     */
    public LeftRightCount countExtraLeftOrRight(String str) {
        int extraLeft = 0;
        int extraRight = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                extraLeft++;
            } else if (str.charAt(i) == ')') {
                extraLeft--;
                if (extraLeft < 0) {
                    extraRight++;
                    extraLeft = 0;
                }
            }
        }
        LeftRightCount pair = new LeftRightCount(extraLeft, extraRight);
        return pair;
    }


    public boolean isValid(String str) {
        int extraLeft = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                extraLeft++;
            } else if (str.charAt(i) == ')') {
                extraLeft--;
                if (extraLeft < 0) {
                    return false;
                }
            }
        }
        return extraLeft == 0 ? true : false;
    }

    public List<String> removeInvalidParentheses(String s) {
        Set<String> outPut = new HashSet<>();
        List<String> listOutPut = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return listOutPut;
        }
        LeftRightCount pair = countExtraLeftOrRight(s);
        StringBuilder stringBuilder = new StringBuilder();
        removeAndAddValid(pair.leftCount, pair.rightCount, stringBuilder, s, 0, outPut);
        listOutPut.addAll(outPut);
        return listOutPut;
    }

    private void removeAndAddValid(int extraLeft, int extraRight,
                                   StringBuilder stringBuilder, String inputString,
                                   int currentIndex, Set<String> outPut) {
        if (currentIndex == inputString.length()) {
            if (extraLeft == 0 && extraRight == 0) {
                String currentString = stringBuilder.toString();
                if (isValid(currentString)) {
                    outPut.add(currentString);
                }
            }
            return;
        }
        stringBuilder.append(inputString.charAt(currentIndex));
        removeAndAddValid(extraLeft, extraRight, stringBuilder, inputString, currentIndex + 1, outPut);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        if (inputString.charAt(currentIndex) == '(') {
            if (extraLeft > 0) {
                removeAndAddValid(extraLeft - 1, extraRight, stringBuilder, inputString, currentIndex + 1, outPut);
            }
        } else if (inputString.charAt(currentIndex) == ')') {
            if (extraRight > 0) {
                removeAndAddValid(extraLeft, extraRight - 1, stringBuilder, inputString, currentIndex + 1, outPut);
            }
        }
    }


}
