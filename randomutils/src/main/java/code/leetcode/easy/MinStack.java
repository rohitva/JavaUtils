package code.leetcode.easy;

import java.util.ArrayList;
import java.util.List;

public class MinStack {
    public class ValueNode {
        public int value;
        public int minSoFar;
        public ValueNode(int value, int minSoFar){
            this.value = value;
            this.minSoFar = minSoFar;
        }
    }
    List<ValueNode> list;
    /** initialize your data structure here. */
    public MinStack() {
        list = new ArrayList<>();
    }

    public void push(int x) {
        if(list.isEmpty()){
            list.add(new ValueNode(x, x));
        } else {
            int min = Math.min(list.get(list.size()-1).minSoFar, x);
            list.add(new ValueNode(x, min));
        }
    }

    public void pop() {
        if(!list.isEmpty()){
            list.remove(list.size()-1);
        }
    }

    public int top() {
        if(!list.isEmpty()){
            return list.get(list.size()-1).value;
        }
        return -1;
    }

    public int getMin() {
        if(!list.isEmpty()){
            return list.get(list.size()-1).minSoFar;
        }
        return -1;
    }
}
