/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that every character in t (including duplicates) is included in the window. If there is no such substring, return the empty string "".
 * <p>
 * The testcases will be generated such that the answer is unique.
 * <p>
 * A substring is a contiguous sequence of characters within the string.
 */
public class MinimumWindowSubstring {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"ADOBECODEBANC", "ABC", "BANC"},
                {"a", "a", "a"},
                {"a", "aa", ""},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s, String t, String expected) {
        assertEquals(minWindow(s, t), expected);
    }

    public String minWindow(String s, String t) {
        int[] chars = new int[(int)'z' + 1];
        t.chars().forEach(c -> chars[c]++);
        int charsMissing = t.length();
        int start = 0;
        int min = Integer.MAX_VALUE;
        int minStart = 0;
        for (int i = 0; i < s.length(); i++) {
            char sChar = s.charAt(i);
            if (chars[sChar] > 0) {
                charsMissing--;
            }
            chars[sChar]--;
            if (charsMissing > 0) {
                continue;
            }
            char startC = s.charAt(start);
            while (chars[startC] < 0) {
                chars[startC]++;
                start++;
                startC = s.charAt(start);
            }
            if ((i - start) < min) {
                min = i - start;
                minStart = start;
            }
        }
        if (charsMissing > 0) {
            return "";
        }
        return s.substring(minStart, minStart + min + 1);
    }

    Map<Character, Integer> charMap(String s) {
        Map<Character, Integer> result = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            result.merge(c, 1, Integer::sum);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println((int)'A');
        System.out.println((int)'Z');
        System.out.println((int)'a');
        System.out.println((int)'z');
        for (int i = 65; i < 123; i++) {
            System.out.println((char)i);
        }
    }


}
