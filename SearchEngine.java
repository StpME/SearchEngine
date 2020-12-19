// Name: Mitchell Stapelman
// Section: DG
// Represents a Search Engine which gives web pages that contains the search query.

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class SearchEngine {
    private HashMap<String, Set<String>> indexMap;

    // Constructs an empty SearchEngine object.
    public SearchEngine() {
        this.indexMap = new HashMap<String, Set<String>>();
    }

    // Splits the given string then assigns the given string to its split contents in a map.
    public void index(String document) {
        Set<String> terms = split(document);
        for (String str : terms) {
            if (!indexMap.keySet().contains(str)) {
                indexMap.put(str, new HashSet<String>());
            }
            indexMap.get(str).add(document);
        }
    }

    // Returns the set that contains all terms from the given query, ignoring terms that weren't indexed.
    // If no terms are indexed or if the given string is empty, returns an empty set.
    public Set<String> search(String query) {
        Set<String> wordList = split(query);
        Set<String> indexedTerms = new HashSet<String>();
        Set<String> removeSet = new HashSet<String>();

        if (query != null) {
            for (String word : wordList) {
                if (indexMap.keySet().contains(word)) {
                    indexedTerms.addAll(indexMap.get(word));
                }
            }
            
            for (String document : indexedTerms) {
                for (String word : wordList) {
                    boolean containsWord = indexMap.keySet().contains(word);
                    if (containsWord && !indexMap.get(word).contains(document)) {
                        removeSet.add(document);
                    }
                }  
            }
        }
        for (String document : removeSet) {
            indexedTerms.remove(document);
        }
        return indexedTerms;
        
    }

    // Return the set of normalized terms split from the given text
    private static Set<String> split(String text) {
        Set<String> result = new HashSet<>();
        for (String term : text.split("\\s+")) {
            term = normalize(term);
            if (!term.isEmpty()) {
                result.add(term);
            }
        }
        return result;
    }

    // Return a standardized lowercase representation of the given string
    private static String normalize(String s) {
        return s.toLowerCase().replaceAll("(^\\p{P}+\\s*)|(\\s*\\p{P}+$)", "");
    }

    public static void main(String[] args) throws IOException {
        SearchEngine engine = new SearchEngine();
        engine.index("apple BALL carrot");
        engine.index("ball !carrot! ,!Dog*&");
        
        /*engine.index("cats are cool");
        engine.index("dogs are cool");
        engine.index("bunnies");
        
    
        System.out.println("cool dogs");
        System.out.println(engine.search("cool dogs") + "search");  */
    }
}
