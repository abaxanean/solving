/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given two strings word1 and word2, return the minimum number of operations required to convert word1 to word2.
 * <p>
 * You have the following three operations permitted on a word:
 * <p>
 * Insert a character
 * Delete a character
 * Replace a character
 */
public class EditDistance {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"horse", "ros", 3},
                {"intention", "execution", 5},
                {"dinitrophenylhydrazine", "acetylphenylhydrazine", 6},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String word1, String word2, int expected) {
        assertEquals(minDistance(word1, word2), expected);
    }

    String word1;
    String word2;
    int[][] dp;

    public int minDistance(String word1, String word2) {
        this.word1 = word1;
        this.word2 = word2;
        this.dp = new int[word1.length()][word2.length()];
        for (int i = 0; i < word1.length(); i++) {
            Arrays.fill(dp[i], -1);
        }
        return minDistance(0, 0);
    }

    int minDistance(int index1, int index2) {
        if (index1 == this.word1.length() && index2 == this.word2.length()) {
            return 0;
        }
        if (index1 == this.word1.length()) {
            // append a character to w1
            return this.word2.length() - index2;
        }
        if (index2 == this.word2.length()) {
            // delete a character from w1
            return this.word1.length() - index1;
        }
        if (this.dp[index1][index2] != -1) {
            return this.dp[index1][index2];
        }
        // the chars match, check next
        char w1Char = this.word1.charAt(index1);
        char w2Char = this.word2.charAt(index2);
        if (w1Char == w2Char) {
            return this.dp[index1][index2] = minDistance(index1 + 1, index2 + 1);
        }
        // at this point we have two chars that are different
        int delete = minDistance(index1 + 1, index2);
        int insert = minDistance(index1, index2 + 1);
        int replace = minDistance(index1 + 1, index2 + 1);
        int result = 1 + Math.min(delete, Math.min(insert, replace));
        this.dp[index1][index2] = result;
        return result;
    }
}
