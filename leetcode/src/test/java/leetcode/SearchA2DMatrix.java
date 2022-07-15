/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Write an efficient algorithm that searches for a value target in an m x n integer matrix matrix. This matrix has the following properties:
 * Integers in each row are sorted from left to right.
 * The first integer of each row is greater than the last integer of the previous row.
 */
public class SearchA2DMatrix {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 3, true},
                {new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 13, false},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[][] matrix, int target, boolean expected) {
        assertEquals(searchMatrix(matrix, target), expected);
    }

    int[][] matrix;
    int target;
    int columnLength;

    public boolean searchMatrix(int[][] matrix, int target) {
        this.matrix = matrix;
        this.target = target;
        this.columnLength = matrix[0].length;
        int size = matrix.length * this.columnLength;
        return binarySearch(0, size - 1);
    }

    private boolean binarySearch(int start, int end) {
        if (start > end) {
            return false;
        }
        int pivot = (start + end) / 2;
        int row = pivot / this.columnLength;
        int column = pivot % this.columnLength;
        if (this.matrix[row][column] == this.target) {
            return true;
        }
        if (this.matrix[row][column] > this.target) {
            return binarySearch(start, pivot - 1);
        }
        return binarySearch(pivot + 1, end);
    }
}
