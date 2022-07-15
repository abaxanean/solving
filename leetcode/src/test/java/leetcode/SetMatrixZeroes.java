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
 * Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0's.
 * You must do it in place.
 */
public class SetMatrixZeroes {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}}, new int[][]{{1, 0, 1}, {0, 0, 0}, {1, 0, 1}}},
                {new int[][]{{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}}, new int[][]{{0, 0, 0, 0}, {0, 4, 5, 0}, {0, 3, 1, 0}}},
                {new int[][]{{1, 2, 3, 4}, {5, 0, 7, 8}, {0, 10, 11, 12}, {13, 14, 15, 0}}, new int[][]{{0, 0, 3, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[][] matrix, int[][] expected) {
        setZeroes(matrix);
        for (int i = 0; i < matrix.length; i++) {
            assertEquals(matrix[i], expected[i]);
        }
    }

    public void setZeroes(int[][] matrix) {
        boolean firstColumnHasZero = false;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                firstColumnHasZero = true;
            }
            for (int j = 1; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (matrix[0][0] == 0) {
            Arrays.fill(matrix[0], 0);
        }
        if (firstColumnHasZero) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
