/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static leetcode.shared.TreeNode.createTreeFromPreOrder;
import static org.testng.Assert.assertEquals;

/**
 * TODO: Document this class.
 */
public class UniqueBinarySearchTrees {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {1, 1},
                {2, 2},
                {3, 5},
                {4, 14},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int n, int expected) {
        assertEquals(numTrees(n), expected);
    }

    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            int sum = 0;
            // select j as root out of i numbers
            for (int j = 1; j <= i; j++) {
                sum += dp[j-1] * dp[i-j];
            }
            dp[i] = sum;
        }
        return dp[n];
    }

    int[][] dp;
    private int numTrees(int start, int end) {
        if (start >= end) return 1;
        if (this.dp[start-1][end-1] > 0) {
            return this.dp[start-1][end-1];
        }
        int sum = 0;
        for (int i = start ; i <= end ; i++) {
            sum += numTrees(start, i - 1) * numTrees(i + 1, end);
        }
        return this.dp[start-1][end-1] = sum;
    }
    
    
}
