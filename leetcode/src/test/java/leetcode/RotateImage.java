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

import static org.testng.Assert.assertEquals;

/**
 * You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).
 * You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.
 */
public class RotateImage {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[][]{
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9},},
                        new int[][]{
                                {7, 4, 1},
                                {8, 5, 2},
                                {9, 6, 3},}
                },
                {new int[][]{
                        {5, 1, 9, 11},
                        {2, 4, 8, 10},
                        {13, 3, 6, 7},
                        {15, 14, 12, 16}},
                        new int[][]{
                                {15, 13, 2, 5},
                                {14, 3, 4, 1},
                                {12, 6, 8, 9},
                                {16, 7, 10, 11}}
                },
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[][] matrix, int[][] expected) {
        rotate(matrix);
        for (int i = 0; i < matrix.length; i++) {
            assertEquals(matrix[i], expected[i]);
        }
    }

    public void rotate(int[][] matrix) {
        for (int row = 0; row < matrix.length / 2; row++) {
            int rowOpposite = matrix.length - 1 - row;
            for (int column = row; column < rowOpposite; column++) {
                int columnOpposite = matrix.length - 1 - column;
                // move top to right
                int right = matrix[column][rowOpposite];
                matrix[column][rowOpposite] = matrix[row][column];
                // move right to bottom
                int bottom = matrix[rowOpposite][columnOpposite];
                matrix[rowOpposite][columnOpposite] = right;
                // move bottom to left
                int left = matrix[columnOpposite][row];
                matrix[columnOpposite][row] = bottom;
                // move left to top
                matrix[row][column] = left;
            }
        }
    }
}
