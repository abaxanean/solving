/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
 * Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
 * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.
 */
public class N_Queens {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {4, Arrays.asList(Arrays.asList(".Q..", "...Q", "Q...", "..Q."), Arrays.asList("..Q.", "Q...", "...Q", ".Q.."))},
                {1, Collections.singletonList(Collections.singletonList("Q"))},
                {5, Collections.singletonList(Collections.singletonList("Q"))},
                {6, Collections.singletonList(Collections.singletonList("Q"))}
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int n, List<List<String>> expected) {
        assertEquals(solveNQueens(n), expected);
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        backTrack(n, 0, 0, new Setup(n), result, n);
        return result;
    }

    void backTrack(int numberOfQueensLeft, int row, int column, Setup setup, List<List<String>> result, int size) {
        if (numberOfQueensLeft == 0) {
            result.add(setup.createLayout());
            return;
        }
        if (column == size) {
            if (setup.columns.size() == row) {
                // we were not able to put a queen on this row
                return;
            }
            column = 0;
            row++;
        }
        if (row == size) {
            return;
        }
        if (setup.push(column)) {
            backTrack(numberOfQueensLeft - 1, row + 1, 0, setup, result, size);
            setup.pop();
        }
        backTrack(numberOfQueensLeft, row, column + 1, setup, result, size);
    }

    static class Setup {
        Set<Integer> columns;
        int[] queenPositions;

        Setup(int n) {
            this.columns = new HashSet<>(n);
            this.queenPositions = new int[n];
        }

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

        public List<String> createLayout() {
            List<String> result = new ArrayList<>();
            char[] row = new char[queenPositions.length];
            Arrays.fill(row, '.');
            for (int i = 0; i < queenPositions.length; i++) {
                row[queenPositions[i]] = 'Q';
                result.add(new String(row));
                row[queenPositions[i]] = '.';
            }
            return result;
        }
    }
}
