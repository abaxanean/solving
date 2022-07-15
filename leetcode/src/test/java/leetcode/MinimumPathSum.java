/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.
 * <p>
 * Note: You can only move either down or right at any point in time.
 */
public class MinimumPathSum {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}, 7},
                {new int[][]{{1, 2, 3}, {4, 5, 6}}, 12},
                {new int[][]{{1, 0, 3}, {4, 5, 6}}, 10},
                {new int[][]{{0, 2, 3}, {4, 5, 6}}, 11},
                {new int[][]{{1, 2, 3}, {4, 0, 6}}, 9},
                {new int[][]{{1, 0, 3}, {5, 1, 6}}, 8},
                {new int[][]{{0, 0, 3}, {5, 1, 6}}, 7},
                {new int[][]{{0, 2, 2, 6, 4, 1, 6, 2, 9, 9, 5, 8, 4, 4}, {0, 3, 6, 4, 5, 5, 9, 7, 8, 3, 9, 9, 5, 4}, {6, 9, 0, 7, 2, 2, 5, 6, 3, 1, 0, 4, 2, 5}, {3, 8, 2, 3, 2, 8, 8, 7, 5, 9, 6, 3, 4, 5}, {4, 0, 1, 3, 9, 2, 0, 1, 6, 7, 9, 2, 8, 9}, {6, 2, 7, 9, 0, 9, 5, 2, 7, 5, 1, 4, 4, 7}, {9, 8, 3, 3, 0, 6, 8, 0, 8, 8, 3, 5, 7, 3}, {7, 7, 4, 5, 9, 1, 5, 0, 2, 2, 2, 1, 7, 4}, {5, 1, 3, 4, 1, 6, 0, 4, 3, 8, 4, 3, 9, 9}, {0, 6, 4, 9, 4, 1, 5, 5, 4, 2, 5, 7, 4, 0}, {0, 1, 6, 6, 4, 9, 2, 7, 8, 2, 1, 3, 3, 7}, {8, 4, 9, 9, 2, 3, 8, 6, 6, 5, 4, 1, 7, 9}}, 63}
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[][] grid, int expected) {
        assertEquals(minPathSum(grid), expected);
    }

    // reusing the grid for DP storage
    public int minPathSum(int[][] grid) {
        int rows = grid.length;
        if (rows == 0 || grid[0].length == 0) {
            return 0;
        }
        int columns = grid[0].length;

        // initialize first row
        for (int i = 1; i < columns; i++) {
            grid[0][i] += grid[0][i - 1];
        }

        // initialize first column
        for (int i = 1; i < rows; i++) {
            grid[i][0] += grid[i - 1][0];
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                grid[i][j] += Math.min(grid[i][j - 1], grid[i - 1][j]);
            }
        }
        return grid[rows - 1][columns - 1];
    }
}
