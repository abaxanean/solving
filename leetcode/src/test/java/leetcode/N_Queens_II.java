/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
 * Given an integer n, return the number of distinct solutions to the n-queens puzzle.
 */
public class N_Queens_II {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {4, 2},
                {1, 1}
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int n, int expected) {
        assertEquals(totalNQueens(n), expected);
    }

    public int totalNQueens(int n) {
        this.columns = new HashSet<>(n);
        this.queenPositions = new int[n];
        return backTrack(n, 0, 0, n);
    }

    int backTrack(int numberOfQueensLeft, int row, int column, int size) {
        if (numberOfQueensLeft == 0) {
            return 1;
        }
        if (column == size) {
            if (this.columns.size() == row) {
                // we were not able to put a queen on this row
                return 0;
            }
            column = 0;
            row++;
        }
        if (row == size) {
            return 0;
        }
        int result = 0;
        if (this.push(column)) {
            result += backTrack(numberOfQueensLeft - 1, row + 1, 0, size);
            this.pop();
        }
        return result + backTrack(numberOfQueensLeft, row, column + 1, size);
    }

    Set<Integer> columns;
    int[] queenPositions;

    boolean push(int column) {
        if (this.columns.contains(column)) {
            return false;
        }
        for (int i = 0; i < columns.size(); i++) {
            // diagonal
            if (Math.abs(columns.size() - i) == Math.abs(column - queenPositions[i])) {
                return false;
            }
        }
        // the queen can be added in this position
        queenPositions[columns.size()] = column;
        this.columns.add(column);
        return true;
    }

    void pop() {
        int column = this.queenPositions[this.columns.size() - 1];
        this.columns.remove(column);
    }
}
