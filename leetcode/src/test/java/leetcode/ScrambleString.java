/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * We can scramble a string s to get a string t using the following algorithm:
 * <p>
 * If the length of the string is 1, stop.
 * If the length of the string is > 1, do the following:
 * Split the string into two non-empty substrings at a random index, i.e., if the string is s, divide it to x and y where s = x + y.
 * Randomly decide to swap the two substrings or to keep them in the same order. i.e., after this step, s may become s = x + y or s = y + x.
 * Apply step 1 recursively on each of the two substrings x and y.
 * Given two strings s1 and s2 of the same length, return true if s2 is a scrambled string of s1, otherwise, return false.
 */
public class ScrambleString {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"eebaacbcbcadaaedceaaacadccd", "eadcaacabaddaceacbceaabeccd", false},
                {"xstjzkfpkggnhjzkpfjoguxvkbuopi", "xbouipkvxugojfpkzjhnggkpfkzjts", true},
                {"abc", "bca", true},
                {"ztu", "utz", true},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s1, String s2, boolean expected) {
        assertEquals(isScramble(s1, s2), expected);
    }

    public boolean isScramble(String s1, String s2) {
        this.dp = new Boolean[s1.length()][s2.length()][s1.length() + 1];
        this.s1 = s1.toCharArray();
        this.s2 = s2.toCharArray();
        return isScramble(0, 0, s1.length());
    }

    Boolean[][][] dp;
    char[] s1;
    char[] s2;

    private boolean isScramble(int s1Pos, int s2Pos, int len) {
        if (len == 1)
            return s1[s1Pos] == s2[s2Pos];

        if (this.dp[s1Pos][s2Pos][len] != null)
            return this.dp[s1Pos][s2Pos][len];

        int[] s1Chars = new int[26];
        int[] s2Chars = new int[26];

        for (int i = 0; i < len; i++) {
            s1Chars[s1[i + s1Pos] - 'a']++;
            s2Chars[s2[i + s2Pos] - 'a']++;
        }
        for (int i = 0; i < 26; i++)
            if (s1Chars[i] != s2Chars[i])
                return this.dp[s1Pos][s2Pos][len] = false;

        for (int i = 1; i < len; i++) {
            if (isScramble(s1Pos, s2Pos, i)
                    && isScramble(s1Pos + i, s2Pos + i, len - i)) {
                return this.dp[s1Pos][s2Pos][len] = true;
            }
            if (isScramble(s1Pos, s2Pos + len - i, i)
                    && isScramble(s1Pos + i, s2Pos, len - i)) {
                return this.dp[s1Pos][s2Pos][len] = true;
            }
        }
        return this.dp[s1Pos][s2Pos][len] = false;
    }

}
