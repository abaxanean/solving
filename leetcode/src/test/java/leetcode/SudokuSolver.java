package leetcode;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 * <p>
 * A sudoku solution must satisfy all of the following rules:
 * <p>
 * Each of the digits 1-9 must occur exactly once in each row.
 * Each of the digits 1-9 must occur exactly once in each column.
 * Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
 * The '.' character indicates empty cells.
 */
public class SudokuSolver {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
//                {
//                        new char[][]
//                                {
//                                        {'5', '3', '.', '.', '7', '.', '.', '.', '.'}
//                                        , {'6', '.', '.', '1', '9', '5', '.', '.', '.'}
//                                        , {'.', '9', '8', '.', '.', '.', '.', '6', '.'}
//                                        , {'8', '.', '.', '.', '6', '.', '.', '.', '3'}
//                                        , {'4', '.', '.', '8', '.', '3', '.', '.', '1'}
//                                        , {'7', '.', '.', '.', '2', '.', '.', '.', '6'}
//                                        , {'.', '6', '.', '.', '.', '.', '2', '8', '.'}
//                                        , {'.', '.', '.', '4', '1', '9', '.', '.', '5'}
//                                        , {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
//                                },
//                        new char[][]{{'5', '3', '4', '6', '7', '8', '9', '1', '2'}
//                                , {'6', '7', '2', '1', '9', '5', '3', '4', '8'}
//                                , {'1', '9', '8', '3', '4', '2', '5', '6', '7'}
//                                , {'8', '5', '9', '7', '6', '1', '4', '2', '3'}
//                                , {'4', '2', '6', '8', '5', '3', '7', '9', '1'}
//                                , {'7', '1', '3', '9', '2', '4', '8', '5', '6'}
//                                , {'9', '6', '1', '5', '3', '7', '2', '8', '4'}
//                                , {'2', '8', '7', '4', '1', '9', '6', '3', '5'}
//                                , {'3', '4', '5', '2', '8', '6', '1', '7', '9'}}
//                },
                {
                        new char[][]{
                                {'.', '.', '9', '7', '4', '8', '.', '.', '.'},
                                {'7', '.', '.', '.', '.', '.', '.', '.', '.'},
                                {'.', '2', '.', '1', '.', '9', '.', '.', '.'},
                                {'.', '.', '7', '.', '.', '.', '2', '4', '.'},
                                {'.', '6', '4', '.', '1', '.', '5', '9', '.'},
                                {'.', '9', '8', '.', '.', '.', '3', '.', '.'},
                                {'.', '.', '.', '8', '.', '3', '.', '2', '.'},
                                {'.', '.', '.', '.', '.', '.', '.', '.', '6'},
                                {'.', '.', '.', '2', '7', '5', '9', '.', '.'}},
                        new char[][]{
                                {'5', '1', '9', '7', '4', '8', '6', '3', '2'},
                                {'7', '8', '3', '6', '5', '2', '4', '1', '9'},
                                {'4', '2', '6', '1', '3', '9', '8', '7', '5'},
                                {'3', '5', '7', '9', '8', '6', '2', '4', '1'},
                                {'2', '6', '4', '3', '1', '7', '5', '9', '8'},
                                {'1', '9', '8', '5', '2', '4', '3', '6', '7'},
                                {'9', '7', '5', '8', '6', '3', '1', '2', '4'},
                                {'8', '3', '2', '4', '9', '1', '7', '5', '6'},
                                {'6', '4', '1', '2', '7', '5', '9', '8', '3'}
                        }
                }
        };
    }

    @Test(dataProvider = "testCases")
    public void test(char[][] board, char[][] expected) {
        rows = new boolean[9][9];
        columns = new boolean[9][9];
        boxes = new boolean[9][9];
        solveSudoku(board);
        for (int i = 0; i < 9; i++) {
            assertEquals(board[i], expected[i]);
        }

        //            String line = CharBuffer.wrap(board[i])
//                    .chars().mapToObj(Character::getNumericValue).map(s -> "'" + s + "'")
//                    .collect(Collectors.joining(", "));
//            System.out.println(line);
    }

boolean[][] rows = new boolean[9][9];
boolean[][] columns = new boolean[9][9];
boolean[][] boxes = new boolean[9][9];

public void solveSudoku(char[][] board) {
    // scan sudoku for existing numbers
    for (int row = 0; row < 9; row++) {
        for (int column = 0; column < 9; column++) {
            char c = board[row][column];
            if (c == '.') {
                continue;
            }
            rows[row][c - '1'] = true;
            columns[column][c - '1'] = true;
            boxes[row - row % 3 + column / 3][c - '1'] = true;
        }
    }
    // we can invoke solveRecursive here, the bellow do-while block is just an optimization

    // fill sudoku while we can find cells for which only 1 option is possible
    boolean modified;
    do {
        modified = false;
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                char c = board[row][column];
                if (c != '.') {
                    continue;
                }
                boolean[] rowSet = rows[row];
                boolean[] columnSet = columns[column];
                boolean[] boxSet = boxes[row - row % 3 + column / 3];
                int optionCount = 0;
                int option = -1;
                for (int k = 0; k < 9; k++) {
                    if (rowSet[k] || columnSet[k] || boxSet[k]) {
                        continue;
                    }
                    optionCount++;
                    if (optionCount == 2) {
                        break;
                    }
                    option = k;
                }
                if (optionCount == 1) {
                    modified = true;
                    board[row][column] = (char)('1' + option);
                    rowSet[option] = columnSet[option] = boxSet[option] = true;
                }
            }
        }
    } while (modified);

    // all remaining cells have multiple options possible, use back-tracking
    solveRecursive(board, 0, -1);
}

boolean solveRecursive(char[][] board, int row, int column) {
    if (row == 8 && column == 8) {
        return true;
    }
    if (column == 8) {
        column = 0;
        row++;
    } else {
        column++;
    }
    char c = board[row][column];
    if (c != '.') {
        return solveRecursive(board, row, column);
    }
    boolean[] rowSet = rows[row];
    boolean[] columnSet = columns[column];
    boolean[] boxSet = boxes[row - row % 3 + column / 3];
    // try all possible numbers
    for (int k = 0; k < 9; k++) {
        if (rowSet[k] || columnSet[k] || boxSet[k]) {
            continue;
        }
        board[row][column] = (char)('1' + k);
        rowSet[k] = columnSet[k] = boxSet[k] = true;
        if (solveRecursive(board, row, column)) {
            return true;
        } else {
            rowSet[k] = columnSet[k] = boxSet[k] = false;
            board[row][column] = '.';
        }
    }
    return false;
}

    // get the options for every cell, sorted - cells with fewest options come first
//        List<Cell> cellOptions = getCellOptions(board);
//        solveRecursive(board, cellOptions.listIterator());
//        boolean found = true;
//        while (!cellOptions.isEmpty() && found) {
//            found = false;
//            Iterator<Cell> iterator = cellOptions.iterator();
//            while (iterator.hasNext()) {
//                Cell cell = iterator.next();
//                int row = cell.row;
//                int column = cell.column;
//                boolean[] rowSet = rows[row];
//                boolean[] columnSet = columns[column];
//                boolean[] boxSet = boxes[row - row % 3 + column / 3];
//                if (cell.options.size() != 1) {
//                    // remove options that have been populated
//                    cell.options.removeIf(option -> rowSet[option] || columnSet[option] || boxSet[option]);
//                }
//                if (cell.options.size() == 1) {
//                    found = true;
//                    int option = cell.options.iterator().next();
//                    board[row][column] = (char)('1' + option);
//                    rowSet[option] = true;
//                    columnSet[option] = true;
//                    boxSet[option] = true;
//                    iterator.remove();
//                }
//            }
//        }
//        for (int i = 0; i < 9; i++) {
//            String line = CharBuffer.wrap(board[i])
//                    .chars().mapToObj(c -> c == '.' ? "." : Character.getNumericValue(c)).map(s -> "" + s)
//                    .collect(Collectors.joining(" "));
//            System.out.println(line);
//        }
//
//        if (!cellOptions.isEmpty()) {
//          solveRecursive(board, 0, -1);
    //        }
//    private List<Cell> getCellOptions(final char[][] board) {
//        List<Cell> sortedList = new LinkedList<>();
//        for (int row = 0; row < 9; row++) {
//            for (int column = 0; column < 9; column++) {
//                char c = board[row][column];
//                if (c != '.') {
//                    continue;
//                }
//                boolean[] rowSet = rows[row];
//                boolean[] columnSet = columns[column];
//                boolean[] boxSet = boxes[row - row % 3 + column / 3];
//                Set<Integer> options = new HashSet<>();
//                for (int k = 0; k < 9; k++) {
//                    if (rowSet[k] || columnSet[k] || boxSet[k]) {
//                        continue;
//                    }
//                    options.add(k);
//                }
//                sortedList.add(new Cell(row, column, options));
//            }
//        }
//        sortedList.sort(Comparator.comparingInt(c -> c.options.size()));
//        return sortedList;
//    }

//    static class Cell {
//        int row;
//        int column;
//        // the numbers that are available for a cell
//        Set<Integer> options;
//
//        public Cell(final int row, final int column, final Set<Integer> options) {
//            this.row = row;
//            this.column = column;
//            this.options = options;
//        }
//    }

//    boolean solveRecursive(char[][] board, ListIterator<Cell> iterator) {
//        if (!iterator.hasNext()) {
//            return true;
//        }
//        Cell cell = iterator.next();
//        boolean[] rowSet = rows[cell.row];
//        boolean[] columnSet = columns[cell.column];
//        boolean[] boxSet = boxes[cell.row - cell.row % 3 + cell.column / 3];
//        int[] filtered = cell.options.stream().filter(option -> !(rowSet[option] || columnSet[option] || boxSet[option])).mapToInt(i -> i).toArray();
//        for (int option: filtered) {
//            board[cell.row][cell.column] = (char)('1' + option);
//            rowSet[option] = true;
//            columnSet[option] = true;
//            boxSet[option] = true;
//            if (solveRecursive(board, iterator)) {
//                return true;
//            } else {
//                rowSet[option] = false;
//                columnSet[option] = false;
//                boxSet[option] = false;
//            }
//        }
//        iterator.previous();
//        return false;
//    }
}
