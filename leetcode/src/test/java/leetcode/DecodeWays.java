/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

/**
 * A message containing letters from A-Z can be encoded into numbers using the following mapping:
 *
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * To decode an encoded message, all the digits must be grouped then mapped back into letters using the reverse of the mapping above (there may be multiple ways). For example, "11106" can be mapped into:
 *
 * "AAJF" with the grouping (1 1 10 6)
 * "KJF" with the grouping (11 10 6)
 * Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".
 *
 * Given a string s containing only digits, return the number of ways to decode it.
 *
 * The test cases are generated so that the answer fits in a 32-bit integer.
 */
public class DecodeWays {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"2611055971756562", 4},
                {"111111111111111111111111111111111111111111111", 1836311903},
        };
    }
    // 2 6 1 10 5 5 9 7 1 7 5 6 5 6 2
    // 26 1 10 5 5 9 7 1 7 5 6 5 6 2
    // 26 1 10 5 5 9 7 17 5 6 5 6 2
    // 2 6 1 10 5 5 9 7 17 5 6 5 6 2

    @Test(dataProvider = "testCases")
    public void test(String s, int expected) {
        assertEquals(numDecodings(s), expected);
    }


    public int numDecodings(String s) {
        this.dp = new int[s.length()];
        Arrays.fill(this.dp, -1);
        int[] numbers = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            numbers[i] = s.charAt(i) - '0';
        }
        return numDecodingsDp(s.toCharArray());
    }

    int[] dp;
    private int numDecodings(int[] s, int pos) {
        if (pos == s.length) {
            return 1;
        }
        if (this.dp[pos] > -1) {
            return this.dp[pos];
        }
        int number = s[pos];
        if (number == 0) {
            return 0;
        }
        int result = numDecodings(s, pos + 1);
        if (number < 3 && pos < s.length - 1) {
            int nextNumber = s[pos + 1];
            if (number == 1 || nextNumber < 7) {
                result += numDecodings(s, pos + 2);
            }
        }
        this.dp[pos] = result;
        return result;
    }

    private int numDecodingsDp(char[] s) {
        if (s[0] == '0') {
            return 0;
        }
        int len = s.length;
        int oneBack = 1, twoBack = 1;
        for (int i = 1; i < len; i++) {
            char b1 = s[i], b2 = s[i-1];
            int current = 0;
            if (b1 > '0') {
                current = oneBack;
            }
            if (b2 == '1' || (b2 == '2' && b1 < '7')) {
                current += twoBack;
            }
            twoBack = oneBack;
            oneBack = current;
        }
        return oneBack;
    }
}
