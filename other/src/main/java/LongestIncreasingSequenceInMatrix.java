/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.util.Arrays;

public class LongestIncreasingSequenceInMatrix {

    int[][] sequenceCache = new int[4][3];
    boolean[][] visited = new boolean[4][3];

    public static void main(String[] args) {
        int[][] array0 = new int[][]{
                {5,    5,    7},
                {-1,   0,    2},
                {10 ,  5  ,  4},
                {6  ,  7 ,   9},
        };
        int[][] array1 = new int[][]{
                {10, 11, 12},
                {9, 8, 7},
                {4, 5, 6},
                {3, 2, 1},
        };
        int[][] array2 = new int[][]{
                {1, 2, 3},
                {6, 5, 4},
                {7, 8, 9},
                {12, 11, 10},
        };
        System.out.println(new LongestIncreasingSequenceInMatrix().llis(array0));
        System.out.println(new LongestIncreasingSequenceInMatrix().llis(array1));
        System.out.println(new LongestIncreasingSequenceInMatrix().llis(array2));
    }

    public int llis(int[][] A) {
        int result = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                int max = sequenceCache[i][j] != 0 ? sequenceCache[i][j] : computeMax(A, i, j, 0, Integer.MIN_VALUE);
                if (max > result) {
                    result = max;
                }
            }
        }
        return result;
    }

    int computeMax(int[][] A, int x, int y, int sequence, int previousNumber) {
        // crossed array boundaries
        if (x < 0 || x >= A.length || y < 0 || y >= A[x].length) {
            return sequence;
        }

        if (visited[x][y]) {
            return sequence + sequenceCache[x][y];
        }
        // current number if less than equal to the previous
        if (A[x][y] < previousNumber) {
            return sequence;
        }

        if (sequenceCache[x][y] != 0) {
            return sequenceCache[x][y] + sequence;
        }
        visited[x][y] = true;
        int result = max(
                // left
                computeMax(A, x, y - 1, sequence + 1, A[x][y]),
                // right
                computeMax(A, x, y + 1, sequence + 1, A[x][y]),
                // bottom
                computeMax(A, x + 1, y, sequence + 1, A[x][y]),
                // top
                computeMax(A, x - 1, y, sequence + 1, A[x][y])
        );
        sequenceCache[x][y] = result - sequence;

        return result;
    }

    int max(int... ints) {
        return Arrays.stream(ints).max().getAsInt();
    }
}
