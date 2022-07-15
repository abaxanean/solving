/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.ListNode;

import static leetcode.shared.ListNode.createList;
import static org.testng.Assert.assertEquals;

/**
 * There is a robot on an m x n grid. The robot is initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.
 * <p>
 * Given the two integers m and n, return the number of possible unique paths that the robot can take to reach the bottom-right corner.
 * <p>
 * The test cases are generated so that the answer will be less than or equal to 2 * 109.
 */
public class UniquePaths {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {3, 7, 28},
                {3, 2, 3},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int m, int n, int expected) {
        assertEquals(uniquePaths(m, n), expected);
    }


    int[][] dp;

    public int uniquePaths(int m, int n) {
        this.dp = new int[m][n];
        return uniquePathsDP(m - 1, n - 1);
    }

    public int uniquePathsDP(int m, int n) {
        if (m == -1 || n == -1) {
            return 0;
        }
        if (m == 0 && n == 0) {
            return 1;
        }
        System.out.printf("m %d n %d %n", m, n);
        if (this.dp[m][n] == 0) {
            this.dp[m][n] = uniquePathsDP(m - 1, n) + uniquePathsDP(m, n - 1);
        }
        return this.dp[m][n];
    }
}
