/*
 * Approach:
 * Use trie to store the words in the dictionary and at each node keep track of top 3 words starting at that node using priorityqueue
 * When we hit # we record the sentence and if it doesnt exist in the trie, we add it
 * TC: insert is O(l) -> l is the length of the word and for sorting its log3 which is constant
 * SC: O(n * l)  -> unique words with avg l length for each word
 */
class AutocompleteSystem {
    HashMap<String, Integer> map;
    String search;
    TrieNode root;

    //create trie class
    class TrieNode {
        TrieNode[] children;
        List<String> top3Results;
        
        //initialize children array and top3results list
        public TrieNode() {
            this.children = new TrieNode[256];
            this.top3Results = new ArrayList<>();
        }
    }

    //for inserting the word in the trie
    private void insert(String word) {
        TrieNode curr = root;
        //create new trienode if it doesnt exists
        for (char c : word.toCharArray()) {
            if (curr.children[c - ' '] == null) {
                curr.children[c - ' '] = new TrieNode();
            }
            curr = curr.children[c - ' '];

            //if trienode is present, get top 3 results and if the top three results doesnt contain the word, add the word to the list
            List<String> list = curr.top3Results;
            if (!list.contains(word)) {
                list.add(word);
            }

            //sort the map to get the highest freq words on top
            Collections.sort(list, (a, b) -> {
                if (map.get(a).equals(map.get(b))) return a.compareTo(b);
                return map.get(b) - map.get(a);
            });
            
            //if the list is greater than 3, remove additional words from the list
            if (list.size() > 3) list.remove(list.size() - 1);
        }
    }

    //if the prefix exists, return the top 3 results if not, create a new array list
    private List<String> searchPrefix(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            if (curr.children[c - ' '] == null) return new ArrayList<>();
            curr = curr.children[c - ' '];
        }
        return curr.top3Results;
    }

    public AutocompleteSystem(String[] sentences, int[] times) {
        this.search = "";
        this.map = new HashMap<>();
        this.root = new TrieNode();
        
        for (int i = 0; i < sentences.length; i++) {
            map.put(sentences[i], map.getOrDefault(sentences[i], 0) + times[i]);
            insert(sentences[i]);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            map.put(search, map.getOrDefault(search, 0) + 1);
            insert(search);
            search = "";
            return new ArrayList<>();
        }
        search += c;
        return searchPrefix(search);
    }
}
