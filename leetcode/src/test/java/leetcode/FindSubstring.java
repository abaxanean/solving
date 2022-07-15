package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * You are given a string s and an array of strings words of the same length. Return all starting indices of substring(s) in s that is a concatenation of each word in words exactly once, in any order, and without any intervening characters.
 * <p>
 * You can return the answer in any order.
 */
public class FindSubstring {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"barfoothefoobarman", new String[]{"foo", "bar"}, Arrays.asList(0, 9)},
                {"barfoofoobarthefoobarman", new String[]{"bar", "foo", "the"}, Arrays.asList(6, 9, 12)},
                {"wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "word"}, Collections.emptyList()},
                {"wordgoodgoodgoodbestword", new String[]{"word", "good", "best", "good"}, Collections.singletonList(8)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s, String[] words, List<Integer> expected) {
        assertEquals(findSubstring(s, words), expected);
    }

    public List<Integer> findSubstring(String s, String[] words) {
        int wordLength = words[0].length();
        int totalLength = wordLength * words.length;
        if (s.length() < totalLength) {
            return Collections.emptyList();
        }
        int[] stringWindow = new int[26];
        createStringWindow(s, totalLength, stringWindow);
        int[] wordsWindow = createWordsWindow(words);
        List<Integer> result = new ArrayList<>();
        Map<String, Integer> wordMap = createWordMap(words);
        for (int i = 0; i <= s.length() - totalLength; i++) {
            if (i > 0) {
                stringWindow[s.charAt(i - 1) - 'a']--;
                stringWindow[s.charAt(i + totalLength - 1) - 'a']++;
            }
            if (!Arrays.equals(stringWindow, wordsWindow)) {
                continue;
            }
            Map<String, Integer> wordMapCopy = new HashMap<>(wordMap);
            for (int wordCount = 0; wordCount < words.length; wordCount++) {
                int wordStart = wordCount * wordLength + i;
                String word = s.substring(wordStart, wordStart + wordLength);
                if (!wordMapCopy.containsKey(word)) {
                    break;
                }
                Integer count = wordMapCopy.get(word);
                if (count == 1) {
                    wordMapCopy.remove(word);
                } else {
                    wordMapCopy.put(word, count - 1);
                }
            }
            if (wordMapCopy.isEmpty()) {
                result.add(i);
            }
        }
        return result;
    }

    Map<String, Integer> createWordMap(final String[] words) {
        final Map<String, Integer> result = new HashMap<>();
        for (String word : words) {
            result.compute(word, (w, val) -> (val == null) ? 1 : val + 1);
        }
        return result;
    }

    int[] createWordsWindow(String[] words) {
        int[] wordsWindow = new int[26];
        for (String word : words) {
            createStringWindow(word, word.length(), wordsWindow);
        }
        return wordsWindow;
    }

    void createStringWindow(final String s, final int totalLength, final int[] stringWindow) {
        for (int i = 0; i < totalLength; i++) {
            char c = s.charAt(i);
            stringWindow[c - 'a']++;
        }
    }
}
