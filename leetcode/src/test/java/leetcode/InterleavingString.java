/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given strings s1, s2, and s3, find whether s3 is formed by an interleaving of s1 and s2.
 *
 * An interleaving of two strings s and t is a configuration where they are divided into non-empty substrings such that:
 *
 * s = s1 + s2 + ... + sn
 * t = t1 + t2 + ... + tm
 * |n - m| <= 1
 * The interleaving is s1 + t1 + s2 + t2 + s3 + t3 + ... or t1 + s1 + t2 + s2 + t3 + s3 + ...
 * Note: a + b is the concatenation of strings a and b.
 */
public class InterleavingString {


    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"aabcc", "dbbca", "aadbbcbcac", true},
                {"aabcc", "dbbca", "aadbbbaccc", false},
                {"", "", "", true},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String s1, String s2, String s3, boolean expected) {
        assertEquals(isInterleave(s1, s2, s3), expected);
    }

    private Boolean[][] dp;
    private char[] s1;
    private char[] s2;
    private char[] s3;

    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        this.dp = new Boolean[s1.length() + 1][s2.length() + 1];
        this.s1 = s1.toCharArray();
        this.s2 = s2.toCharArray();
        this.s3 = s3.toCharArray();
        return isInterleave(0, 0);
    }

    private boolean isInterleave(int i1, int i2) {
        int i3 = i1 + i2;
        if (i3 == this.s3.length) {
            return true;
        }

        if (this.dp[i1][i2] != null) {
            return this.dp[i1][i2];
        }
        if (i1 < this.s1.length && this.s1[i1] == this.s3[i3] && isInterleave(i1 + 1, i2)) {
            return this.dp[i1][i2] = true;
        }
        if (i2 < this.s2.length && this.s2[i2] == this.s3[i3] && isInterleave(i1, i2 + 1)) {
            return this.dp[i1][i2] = true;
        }
        return this.dp[i1][i2] = false;
    }
}
