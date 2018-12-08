/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


public class RetrieveMostFrequentlyUsedWords {

    public static void main(String[] args) {
        final List<String> result = new RetrieveMostFrequentlyUsedWords().retrieveMostFrequentlyUsedWords("and's he-s the to is",
                Arrays.asList("and", "he", "the", "to", "is"));
        System.out.println(result);
    }

    List<String> retrieveMostFrequentlyUsedWords(String literatureText, List<String> wordsToExclude) {
        System.out.println(literatureText + "# #" + wordsToExclude);
        Map<String, Integer> words = buildFrequencyMap(literatureText, new HashSet<>(wordsToExclude));
        List<String> result = new ArrayList<>();
        int max = 0;
        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            if (count > max) {
                // found a word with a bigger frequency
                result.clear();
                result.add(word);
                max = count;
            } else if (count == max) {
                result.add(word);
            }
        }
        return result;
    }

    private Map<String, Integer> buildFrequencyMap(final String literatureText, final Set<String> excludeSet) {
        Map<String, Integer> words = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (char c : literatureText.toCharArray()) {
            // if the character is not punctuation or whitespace
            if (Pattern.matches("[\\w'-]", Character.toString(c))) {
                sb.append(c);
            } else if (sb.length() > 0) {
                final String word = sb.toString();
                if (!excludeSet.contains(word)) {
                    words.compute(word, (w, count) -> count == null ? 1 : count + 1);
                }
                sb.setLength(0);
            }
        }
        return words;
    }
}
