/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an m x n matrix, return all elements of the matrix in spiral order.
 */
public class SpiralMatrix {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[][]{
                        {7},
                        {9},
                        {6},
                }, Arrays.asList(7,9,6)},
                {new int[][]{
                        {1,2},
                        {3,4,},
                }, Arrays.asList(1,2,4,3)},
                {new int[][]{
                        {1,2,3},
                        {4,5,6},
                }, Arrays.asList(1,2,3,6,5,4)},
                {new int[][]{
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                }, Arrays.asList(1,2,3,6,9,8,7,4,5)},
                {new int[][]{
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12},
                }, Arrays.asList(1,2,3,6,9,12,11,10,7,4,5,8)},
                {new int[][]{
                        {1,2,3,4},
                        {5,6,7,8},
                        {9,10,11,12},
                }, Arrays.asList(1,2,3,4,8,12,11,10,9,5,6,7)},
                {new int[][]{
                        {1,2,3,4},
                        {5,6,7,8},
                        {9,10,11,12},
                        {13,14,15,16},
                }, Arrays.asList(1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[][] matrix, List<Integer> expected) {
        assertEquals(spiralOrder(matrix), expected);
    }

public List<Integer> spiralOrder(int[][] matrix) {
    int ceiling = 0;
    int floor = matrix.length - 1;
    int left = 0;
    int right = matrix[0].length - 1;
    List<Integer> result = new ArrayList<>(floor * right);
    while (true) {
        // go right
        for (int i = left; i <= right; i++) {
            result.add(matrix[ceiling][i]);
        }
        if (++ceiling > floor) {
            return result;
        }
        //go down
        for (int i = ceiling; i <= floor; i++) {
            result.add(matrix[i][right]);
        }
        if (left > --right) {
            return result;
        }
        //go left
        for (int i = right; i >= left; i--) {
            result.add(matrix[floor][i]);
        }
        if (ceiling > --floor) {
            return result;
        }
        // go up
        for (int i = floor; i >= ceiling; i--) {
            result.add(matrix[i][left]);
        }
        if (++left > right) {
            return result;
        }
    }
}
}
