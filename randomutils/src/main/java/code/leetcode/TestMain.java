package code.leetcode;

import code.leetcode.hard.Trie;

public class TestMain {
    public static void main(String[] args){
        Trie trie = new Trie();
        trie.insert("abc");
//        System.out.println(trie);
        System.out.println(trie.search("abc"));
        System.out.println(trie.search("abb"));
        System.out.println(trie.startsWith("abc"));
        System.out.println(trie.startsWith("ab"));
        System.out.println(trie.startsWith("ac"));
        System.out.println(trie.startsWith("a"));
        System.out.println(trie.startsWith("b"));
    }
}
