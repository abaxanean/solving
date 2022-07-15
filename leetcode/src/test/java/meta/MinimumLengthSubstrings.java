/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Map.Entry;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Minimum Length Substrings
 * You are given two strings s and t. You can select any substring of string s and rearrange the characters of the selected substring. Determine the minimum length of the substring of s such that string t is a substring of the selected substring.
 * Signature
 * int minLengthSubstring(String s, String t)
 * Input
 * s and t are non-empty strings that contain less than 1,000,000 characters each
 * Output
 * Return the minimum length of the substring of s. If it is not possible, return -1
 * Example
 * s = "dcbefebce"
 * t = "fd"
 * output = 5
 * Explanation:
 * Substring "dcbef" can be rearranged to "cfdeb", "cefdb", and so on. String t is a substring of "cfdeb". Thus, the minimum length required is 5.
 */
public class MinimumLengthSubstrings {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"dcbefebce", "fd", 5},
                {"bfbeadbcbcbfeaaeefcddcccbbbfaaafdbebedddf", "cbccfafebccdccebdd", -1},
                {"this is a test string", "tist", 6},
        };
    }


    @Test(dataProvider = "testCases")
    public void test(String s, String t, int expected) {
        assertEquals(minLengthSubstring(s, t), expected);
    }

    int minLengthSubstring(String s, String t) {
        Map<Character, Integer> tCharsCount = mapChars(t);
        // count of chars matched
        int length = 0;
        int start = 0, end = 0;
        int result = Integer.MAX_VALUE;
        for (end = 0; end < s.length(); end++) {
            char sChar = s.charAt(end);
            Integer count = tCharsCount.get(sChar);
            if (count == null) {
                continue;
            }
            tCharsCount.put(sChar, count - 1);
            if (count > 0 && length < t.length()) {
                length++;
            }
            if (length < t.length()) {
                continue;
            }
            // we have all the chars, try to minimize the string
            do {
                char startChar = s.charAt(start);
                Integer startCharCount = tCharsCount.get(startChar);
                if (startCharCount == null) {
                    start++;
                    continue;
                }
                if (startCharCount < 0) {
                    start++;
                    tCharsCount.put(startChar, startCharCount + 1);
                }
                if (startCharCount == 0) {
                    break;
                }
            }
            while (true);
            result = Math.min(result, end - start + 1);
        }

        return length < t.length() ? -1 : result;
    }


    Map<Character, Integer> mapChars(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.merge(s.charAt(i), 1, Integer::sum);
        }
        return map;
    }
}
