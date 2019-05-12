package code.leetcode.hard;


import lombok.ToString;

/**
 * See the node structure and constructor.
 * [1] Root is single start point and end of empty or null string. It's not 26
 *          root
 *          ////\\\\\ [26 child]
 * [2] By implementing searchNode method we can reuse it in search-word and prefix search methods.
 * [3] We are not thinking about delete. Trie they used mostly for dictionary and delete is not something happen often.
 */
public class Trie {
    TrieNode root;

    public class TrieNode {
        boolean isEndPoint = false;
        TrieNode[] nextChild = new TrieNode[26];
    }

    /**
     * Initialize your data structure here.
     */
    public Trie() {
        root = new TrieNode();
        root.isEndPoint = true; //root is end of Empty string or null
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (currentNode.nextChild[index] == null) {
                currentNode.nextChild[index] = new TrieNode();
            }
            currentNode = currentNode.nextChild[index];
        }
        currentNode.isEndPoint = true;
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
       TrieNode searchNode = searchNode(word);
       return searchNode !=null && searchNode.isEndPoint;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        TrieNode searchNode = searchNode(prefix);
        return searchNode !=null;
    }


    public TrieNode searchNode(String word) {
        if(word == null || word.length() ==0){
            return root;
        }
        TrieNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (currentNode.nextChild[index] == null) {
                return null;
            }
            currentNode = currentNode.nextChild[index];
        }
        return currentNode;
    }
}
