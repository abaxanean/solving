/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.Objects;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
 * <p>
 * The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.
 */
public class WordSearch {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCCED", true},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(char[][] board, String word, boolean expected) {
        assertEquals(exist(board, word), expected);
    }

    public boolean exist(char[][] board, String word) {
        // just an optimization, can be omitted
        if (!checkChars(board, word)) {
            return false;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (exist(board, word, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean exist(char[][] board, String word, int index, int x, int y) {
        if (index == word.length()) {
            return true;
        }
        if (x < 0 || y < 0 || x == board.length || y == board[x].length) {
            return false;
        }
        if (board[x][y] != word.charAt(index)) {
            return false;
        }
        board[x][y] = '_';
        boolean result = exist(board, word, index + 1, x + 1, y)
                || exist(board, word, index + 1, x - 1, y)
                || exist(board, word, index + 1, x, y + 1)
                || exist(board, word, index + 1, x, y - 1);
        board[x][y] = word.charAt(index);
        return result;
    }

    // this method is just an optimization
// checks whether we have all chars in the grid
// if we don't have the chars, the result is certainly false
    private boolean checkChars(final char[][] board, final String word) {
        // 'z' char has code 122
        int[] charCount = new int[123];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                charCount[board[i][j]]++;
            }
        }
        for (int i = 0; i < word.length(); i++) {
            if (charCount[word.charAt(i)] == 0) {
                // not all chars for the word are in the grid
                return false;
            }
            charCount[word.charAt(i)]--;
        }
        return true;
    }

    static class Pair {
        int x, y, hash;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
            this.hash = Objects.hash(x, y);
        }

        @Override
        public boolean equals(final Object o) {
            final Pair pair = (Pair)o;
            return x == pair.x && y == pair.y;
        }

        @Override
        public int hashCode() {
            return this.hash;
        }
    }

    int[][] adj = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

//    public boolean exist(char[][] board, String word) {
//        int m = board.length, n = board[0].length;
//
//        char[] w = word.toCharArray();
//
//        if (!checkWordExists(board, w))
//            return false;
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                if (board[i][j] == w[0] && checkPattern(board, w, i, j, 0)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    boolean checkWordExists(char[][] board, char[] word) {
        int[] charCount = new int[58];
        boolean[][] neighbours = new boolean[58][58];
        int i, j, charCurrentIndex, m = board.length, n = board[0].length;
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                charCurrentIndex = board[i][j] - 'A';
                charCount[charCurrentIndex]++;
                if (i > 1) {
                    neighbours[charCurrentIndex][board[i - 1][j] - 'A'] = true;
                    neighbours[board[i - 1][j] - 'A'][charCurrentIndex] = true;
                }
                if (i + 1 < m) {
                    neighbours[charCurrentIndex][board[i + 1][j] - 'A'] = true;
                    neighbours[board[i + 1][j] - 'A'][charCurrentIndex] = true;
                }
                if (j > 1) {
                    neighbours[charCurrentIndex][board[i][j - 1] - 'A'] = true;
                    neighbours[board[i][j - 1] - 'A'][charCurrentIndex] = true;
                }
                if (j + 1 < n) {
                    neighbours[charCurrentIndex][board[i][j + 1] - 'A'] = true;
                    neighbours[board[i][j + 1] - 'A'][charCurrentIndex] = true;
                }
            }
        }
        for (i = 0; i < word.length; i++) {
            charCurrentIndex = word[i] - 'A';
            if (charCount[charCurrentIndex] == 0)
                return false;
            charCount[charCurrentIndex]--;
            if (i < word.length - 1 && (!neighbours[charCurrentIndex][word[i + 1] - 'A'] || !neighbours[word[i + 1] - 'A'][charCurrentIndex]))
                return false;
        }
        return true;
    }

    boolean checkPattern(char[][] board, char[] word, int i, int j, int wordInd) {
        if (wordInd == word.length)
            return true;

        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != word[wordInd])
            return false;
        board[i][j] = '*';
        for (int a = 0; a < 4; a++) {
            int x = i + adj[a][0];
            int y = j + adj[a][1];
            if (checkPattern(board, word, x, y, wordInd + 1))
                return true;
        }
        board[i][j] = word[wordInd];
        return false;
    }

    public static void main(String[] args) {
//        System.out.println((int)'a');
//        System.out.println((int)'A');
//        System.out.println((int)'z');
//        System.out.println((int)'Z');
//        System.out.println((char)94);
        for (int i = 65; i < 123; i++) {
            System.out.printf("%d %s %n", i, (char)i);
        }
    }
}
