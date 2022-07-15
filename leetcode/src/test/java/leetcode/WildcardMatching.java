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
 * Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
 * <p>
 * '?' Matches any single character.
 * '*' Matches any sequence of characters (including the empty sequence).
 * The matching should cover the entire input string (not partial).
 */
public class WildcardMatching {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"a", "aa", false},
                {"aa", "a", false},
                {"aa", "*", true},
                {"aaa", "**", true},
                {"cb", "?a", false},
                {"abcdac", "a*c", true},
                {"abcda", "a*a", true},
                {"aa", "a*a", true},
                {"aa", "a*ab", false},
                {"12345", "1?*5", true},
                {"123456", "1?*5", false},
                {"23457", "*45?", true},
                {"123456789", "*9", true},
                {"", "******", true},
                {"aaabbbaabaaaaababaabaaabbabbbbbbbbaabababbabbbaaaaba", "a*******b", false},
                {"babbbbaabababaabbababaababaabbaabababbaaababbababaaaaaabbabaaaabababbabbababbbaaaababbbabbbbbbbbbbaabbb", "b**bb**a**bba*b**a*bbb**aba***babbb*aa****aabb*bbb***a", false},
                {"abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb", "**aa*****ba*a*bb**aa*ab****a*aaaaaa***a*aaaa**bbabb*b*b**aaaaaaaaa*a********ba*bbb***a*ba*bb*bb**a*b*bb", false},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s, String p, boolean expected) {
        assertEquals(isMatch(s, p), expected);
    }

//    public boolean isMatch(String s, String p) {
//        this.cache = new byte[s.length() + 1][p.length() + 1];
//        return match(s, p, 0, 0);
//    }

    byte[][] cache;

    boolean match(String s, String p, int indexS, int indexP) {
        byte cached = cache[indexS][indexP];
        if (cached != 0) {
            return cached == 1;
        }
//        System.out.printf("%d %d %n", indexS, indexP);
        if (indexS == s.length() && indexP == p.length()) {
            cache[indexS][indexP] = 1;
            return true;
        }
        if (indexP == p.length()) {
            cache[indexS][indexP] = -1;
            return false;
        }
        char pChar = p.charAt(indexP);
        if (indexS == s.length()) {
            return pChar == '*' && match(s, p, indexS, indexP + 1);
        }
        if (pChar == '?' || pChar == s.charAt(indexS)) {
            return match(s, p, indexS + 1, indexP + 1);
        }
        if (pChar == '*') {
            if (indexP < p.length() - 1 && p.charAt(indexP + 1) == '*') {
                return match(s, p, indexS, indexP + 1);
            }
            return match(s, p, indexS, indexP + 1)
                    || match(s, p, indexS + 1, indexP);
        }
        return false;
    }

    public boolean isMatch2(String s, String p) {
        // multiple '*' are identical to a single '*' in the pattern
        p = p.replaceAll("\\*+", "*");
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        // dp[i][j] shows whether the first i characters of the string can be matched by the first j characters of the pattern
        dp[0][0] = true;
        if (p.length() > 0 && p.charAt(0) == '*') {
            dp[0][1] = true;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                char pChar = p.charAt(j - 1);
                if (pChar == s.charAt(i - 1) || pChar == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pChar == '*') {
                    // we either eat one (more) char with the '*' or none
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    public boolean isMatch(String str, String pattern) {
        int s = 0, p = 0, match = 0, starIdx = -1;
        while (s < str.length()) {
            // advancing both pointers
            if (p < pattern.length() && (pattern.charAt(p) == '?' || str.charAt(s) == pattern.charAt(p))) {
                s++;
                p++;
            }
            // * found, only advancing pattern pointer
            else if (p < pattern.length() && pattern.charAt(p) == '*') {
                starIdx = p;
                match = s;
                p++;
            }
            // last pattern pointer was *, advancing string pointer
            else if (starIdx != -1) {
                p = starIdx + 1;
                match++;
                s = match;
            }
            //current pattern pointer is not star, last patter pointer was not *
            //characters do not match
            else return false;
        }

        //check for remaining characters in pattern
        while (p < pattern.length() && pattern.charAt(p) == '*')
            p++;

        return p == pattern.length();
    }

}
