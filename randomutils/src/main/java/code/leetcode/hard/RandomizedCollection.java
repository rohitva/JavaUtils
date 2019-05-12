package code.leetcode.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomizedCollection {
    List<Integer> list;
    Map<Integer, List<Integer>> map;
    Random random;

    public static void main(String[] args) {
        RandomizedCollection randomizedCollection = new RandomizedCollection();
        randomizedCollection.insert(9);
        randomizedCollection.insert(9);
        randomizedCollection.insert(1);
        randomizedCollection.insert(1);
        randomizedCollection.insert(2);
        randomizedCollection.insert(1);
        randomizedCollection.print();
        randomizedCollection.remove(2);
        randomizedCollection.print();
        randomizedCollection.remove(1);
        randomizedCollection.print();
        randomizedCollection.remove(1);
        randomizedCollection.print();
        randomizedCollection.insert(9);
        randomizedCollection.print();
        randomizedCollection.remove(1);
        randomizedCollection.print();
    }

    /**
     * Initialize your data structure here.
     */
    public RandomizedCollection() {
        list = new ArrayList<>();
        map = new HashMap<>();
        random = new Random();
    }

    private void print(){
        System.out.println(map);
        System.out.println(list);
    }

    /**
     * Inserts a value to the collection. Returns true if the collection did not already contain the specified element.
     */
    public boolean insert(int val) {
        boolean newKey = map.containsKey(val);
        list.add(val);
        List<Integer> indexList = map.getOrDefault(val, new ArrayList<>());
        indexList.add(list.size() - 1);
        map.put(val, indexList);
        return newKey;
    }

    /**
     * Removes a value from the collection. Returns true if the collection contained the specified element.
     */
    public boolean remove(int val) {
        if (map.containsKey(val) && map.get(val).size() > 0) {
            List<Integer> indexList = map.get(val);
            int index = indexList.get(indexList.size() - 1);
            list.set(index, list.get(list.size() - 1));
            List<Integer> lastElementIndexList = map.get(list.get(list.size() - 1));
            lastElementIndexList.set(lastElementIndexList.size() - 1, index);
            indexList.remove(indexList.size() - 1);
            list.remove(list.size()-1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get a random element from the collection.
     */
    public int getRandom() {
        int index = random.nextInt(list.size());
        return list.get(index);
    }

}
