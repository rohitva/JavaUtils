package code.leetcode.hard;

import java.util.ArrayList;
import java.util.List;

public class Codec {
    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        if(strs == null || strs.isEmpty()){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(String str : strs){
            stringBuilder.append(str.length()).append("#").append(str);
        }
        return stringBuilder.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> outPut = new ArrayList<>();
        if(s == null || s.length() ==0){
            return outPut;
        }
        int start =0;
        int end =0;
        while(end < s.length()){
            if(s.charAt(end) == '#'){
                Integer length = Integer.parseInt(s.substring(start, end));
                String str = s.substring(end+1, end+length+1);
                outPut.add(str);
                start = end+length+1;
                end = start;
            } else {
                end++;
            }
        }

        return outPut;
    }
}
