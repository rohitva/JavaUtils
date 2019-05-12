package code.leetcode.medium2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

public class WordDistance {
    Map<String, Integer> lastPrintedStamp = new HashMap<>();
    Map<String, List<Integer>> indexMap = new HashMap<>();

    public static void main(String[] args) {
        String[] input = {"a", "a", "b", "b"};
        WordDistance wordDistance = new WordDistance(input);
        System.out.println(wordDistance.shortest("b", "a"));
    }

    public WordDistance(String[] words) {
        if (words == null || words.length == 0) {
            return;
        }
        for (int i = 0; i < words.length; i++) {
            List<Integer> currentList = indexMap.getOrDefault(words[i], new ArrayList<>());
            currentList.add(i);
            indexMap.put(words[i], currentList);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> firstList = indexMap.get(word1);
        List<Integer> secondList = indexMap.get(word2);
        int minDiff = Integer.MAX_VALUE;
        int firstIndex = 0, secondIndex = 0;
        while (firstIndex < firstList.size() && secondIndex < secondList.size()) {
            minDiff = Math.min(minDiff, Math.abs(secondList.get(secondIndex) - firstList.get(firstIndex)));
            if (firstList.get(firstIndex) < secondList.get(secondIndex)) {
                firstIndex++;
            } else {
                secondIndex++;
            }
        }

        return minDiff;
    }


    public int evalRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return 0;
        }
        Stack<Integer> valueStack = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            switch (tokens[i]) {
                case "*":
                    //Add
                    int first = valueStack.pop();
                    int second = valueStack.pop();
                    valueStack.push(second * first);
                    break;
                case "/":
                    // Divide
                    first = valueStack.pop();
                    second = valueStack.pop();
                    valueStack.push(second / first);
                    break;
                case "+":
                    // Divide
                    first = valueStack.pop();
                    second = valueStack.pop();
                    valueStack.push(second + first);
                    break;
                case "-":
                    // Divide
                    first = valueStack.pop();
                    second = valueStack.pop();
                    valueStack.push(second - first);
                    break;
                default:
                    Integer value = Integer.parseInt(tokens[i]);
                    valueStack.push(value);
            }
        }
        return valueStack.pop();
    }

    public boolean isIsomorphic(String s, String t) {
        if(s ==null && t==null){
            return true;
        }
        if(s==null || t==null || s.length() !=t.length()){
            return false;
        }
        Map<Character, Character> characterMap = new HashMap<>();
        Map<Character, Character> characterMap2 = new HashMap<>();

        for(int i=0;i<s.length();i++){
            char firstChar = s.charAt(i);
            char secondChar = t.charAt(i);
            if(characterMap.containsKey(firstChar) || characterMap2.containsKey(firstChar)
                    ||characterMap.containsKey(secondChar) || characterMap2.containsKey(secondChar)){

            } else {
                characterMap.put(firstChar, secondChar);
                characterMap2.put(secondChar,firstChar);
            }
        }
        return true;
    }


}
