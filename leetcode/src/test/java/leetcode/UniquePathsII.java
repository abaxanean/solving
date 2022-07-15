/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * You are given an m x n integer array grid. There is a robot initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m-1][n-1]). The robot can only move either down or right at any point in time.
 * <p>
 * An obstacle and space are marked as 1 or 0 respectively in grid. A path that the robot takes cannot include any square that is an obstacle.
 * <p>
 * Return the number of possible unique paths that the robot can take to reach the bottom-right corner.
 * <p>
 * The testcases are generated so that the answer will be less than or equal to 2 * 109.
 */
public class UniquePathsII {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}, 2},
                {new int[][]{{0, 1}, {0, 0}}, 1},
                {new int[][]{{1}}, 0},
                {new int[][]{{0},{1},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{1},{0},{0},{0},{0},{1},{0},{0},{0},{0},{0},{0},{0},{0},{0},{0},{1},{1},{0},{1},{0},{0},{1},{0},{0},{0},{0},{1}}, 0}
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[][] obstacleGrid, int expected) {
        assertEquals(uniquePathsWithObstacles(obstacleGrid), expected);
    }


    int[][] grid;

    public int uniquePathsWithObstacles(int[][] grid) {
        int rows = grid.length;
        if (rows == 0 || grid[0].length == 0) {
            return 0;
        }
        int columns = grid[0].length;
        if (grid[0][0] == 1) {
            return 0;
        }
        grid[0][0] = 1;
        // initialize first column
        for (int i = 1; i < rows; i++) {
            if (grid[i][0] == 1) {
                grid[i][0] = 0;
                continue;
            }
            grid[i][0] = grid[i-1][0];
        }

        // initialize first row
        for (int i = 1; i < columns; i++) {
            if (grid[0][i] == 1) {
                grid[0][i] = 0;
                continue;
            }
            grid[0][i] = grid[0][i-1];
        }

        // go cell by cell
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                if (grid[i][j] == 1) {
                    grid[i][j] = 0;
                    continue;
                }
                grid[i][j] = grid[i - 1][j] + grid[i][j - 1];
            }
        }
        return grid[rows - 1][columns - 1];
    }

    public int uniquePathsDP(int m, int n) {
        if (m == -1 || n == -1) {
            return 0;
        }
        if (this.grid[m][n] == 1 || this.grid[m][n] == -1) {
            this.grid[m][n] = -1;
            return 0;
        }
        if (m == 0 && n == 0) {
            return 1;
        }
        if (this.grid[m][n] == 0) {
            this.grid[m][n] = uniquePathsDP(m - 1, n) + uniquePathsDP(m, n - 1);
        }
        return this.grid[m][n];
    }
}
