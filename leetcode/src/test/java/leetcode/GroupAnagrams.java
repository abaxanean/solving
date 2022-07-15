/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 * <p>
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.
 */
public class GroupAnagrams {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new String[]{"eat", "tea", "tan", "ate", "nat", "bat",},
                        Arrays.asList(Arrays.asList("bat"), Arrays.asList("nat", "tan"), Arrays.asList("ate", "eat", "tea"))
                }
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String[] strs, List<List<String>> expected) {
        assertEquals(groupAnagrams(strs), expected);
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> byCharMap = new HashMap<>();
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String identity = new String(chars);
            List<String> list = byCharMap.get(identity);
            if (list == null) {
                list = new ArrayList<>();
                result.add(list);
                byCharMap.put(identity, list);
            }
            list.add(s);
        }
        return result;
    }

    static class Identity {
        char[] chars;
        int hashCode;

        Identity(String s) {
            this.chars = s.toCharArray();
            Arrays.sort(this.chars);
            this.hashCode = Arrays.hashCode(chars);
        }

        @Override
        public boolean equals(final Object o) {
            return Arrays.equals(chars, ((Identity)o).chars);
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }
    }

    static class Identity2 {
        int[] chars = new int[26];
        int hashCode;

        Identity2(String s) {
            for (int i = 0; i < s.length(); i++) {
                chars[s.charAt(i) - 'a']++;
            }
            this.hashCode = Arrays.hashCode(chars);
        }

        @Override
        public boolean equals(final Object o) {
            return Arrays.equals(chars, ((Identity2)o).chars);
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }
    }

    private char[] characterSet(final String str) {
        char[] result = str.toCharArray();
        Arrays.sort(result);
        return result;
    }

    private Map<Character, Integer> characterMap(final String str) {
        Map<Character, Integer> result = new HashMap<>(str.length());
        for (int i = 0; i < str.length(); i++) {
            result.merge(str.charAt(i), 1, Integer::sum);
        }
        return result;
    }

}
