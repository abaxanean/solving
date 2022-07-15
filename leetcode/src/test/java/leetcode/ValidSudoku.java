package leetcode;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:
 * <p>
 * Each row must contain the digits 1-9 without repetition.
 * Each column must contain the digits 1-9 without repetition.
 * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 * <p>
 * Note:
 * A Sudoku board (partially filled) could be valid but is not necessarily solvable.
 * Only the filled cells need to be validated according to the mentioned rules.
 */
public class ValidSudoku {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {
                        new char[][]
                                {
                 {'5', '3', '.', '.', '7', '.', '.', '.', '.'}
                 , {'6', '.', '.', '1', '9', '5', '.', '.', '.'}
                 , {'.', '9', '8', '.', '.', '.', '.', '6', '.'}
                 , {'8', '.', '.', '.', '6', '.', '.', '.', '3'}
                 , {'4', '.', '.', '8', '.', '3', '.', '.', '1'}
                 , {'7', '.', '.', '.', '2', '.', '.', '.', '6'}
                 , {'.', '6', '.', '.', '.', '.', '2', '8', '.'}
                 , {'.', '.', '.', '4', '1', '9', '.', '.', '5'}
                 , {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
                                }, true
                },
        };
    }

    @Test(dataProvider = "testCases")
    public void test(char[][] board, boolean expected) {
        assertEquals(isValidSudoku(board), expected);
    }

    public boolean isValidSudoku(char[][] board) {
        boolean[] set = new boolean[9];
        // validate rows
        for (int row = 0; row < 9; row++) {
            Arrays.fill(set, false);
            for (int column = 0; column < 9; column++) {
                char c = board[row][column];
                if(c == '.') {
                    continue;
                }
                int number = c - '1';
                if (set[number]) {
                    return false;
                }
                set[number] = true;
            }
        }
        // validate columns
        for (int column = 0; column < 9; column++) {
            Arrays.fill(set, false);
            for (int row = 0; row < 9; row++) {
                char c = board[row][column];
                if(c == '.') {
                    continue;
                }
                int number = c - '1';
                if (set[number]) {
                    return false;
                }
                set[number] = true;
            }
        }
        // validate sub-boxes
        for (int rowDelta = 0; rowDelta < 7; rowDelta +=3) {
            for (int columnDelta = 0; columnDelta < 7; columnDelta +=3) {
                Arrays.fill(set, false);
                for (int row = rowDelta; row < rowDelta + 3; row++) {
                    for (int column = columnDelta; column < columnDelta + 3; column++) {
                        char c = board[row][column];
                        if(c == '.') {
                            continue;
                        }
                        int number = c - '1';
                        if (set[number]) {
                            return false;
                        }
                        set[number] = true;
                    }
                }
            }
        }
        return true;
    }
}
