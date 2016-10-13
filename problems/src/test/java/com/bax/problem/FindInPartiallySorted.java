/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.problem;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Find an element into a partially sorted array (sorted and rotated).
 */
public class FindInPartiallySorted {

    int[] array = new int[]{4, 6, 8, 10, 0, 2};

    @DataProvider(name = "findPivot")
    public Object[][] findPivot() {
        return new Object[][]{
                new Object[]{new int[]{4, 6, 8, 10, 0, 2}, 10},
                new Object[]{new int[]{4, 6, 8, 10, 11, 2}, 11},
                new Object[]{new int[]{4, 6, 8, 10, 11, 12}, -1},
                new Object[]{new int[]{7, 8, 1, 2, 3, 4, 5}, 8},
                new Object[]{new int[]{8, 1, 2, 3, 4, 5, 6}, 8},
                new Object[]{new int[]{8, 9, 10, 3, 4, 5, 6}, 10},
        };
    }

    @Test(dataProvider = "findPivot")
    public void findPivot(int[] array, int pivot) {
        int foundPivot = findPivotR(array, 0, array.length - 1);
        assertEquals(foundPivot != -1 ? array[foundPivot] : -1, pivot);
    }

    private int findPivotR(int[] array, int start, int end) {
        if (start == array.length - 1) {
            return -1;
        }
        if (start == end) {
            return start;
        }
        int pivot = (start + end) / 2;
        if (array[pivot] > array[pivot + 1]) {
            return pivot;
        }
        if (array[pivot] > array[start]) {
            return findPivotR(array, pivot + 1, end);
        } else {
            return findPivotR(array, start, pivot - 1);
        }
    }

    @DataProvider(name = "findElement")
    public Object[][] findElement() {
        return new Object[][]{
                new Object[]{new int[]{4, 6, 8, 10, 0, 2}, 10, 3},
                new Object[]{new int[]{4, 6, 8, 10, 11, 2}, 10, 3},
                new Object[]{new int[]{4, 6, 8, 10, 11, 2}, 8, 2},
                new Object[]{new int[]{4, 6, 8, 10, 11, 12}, 4, 0},
                new Object[]{new int[]{7, 8, 1, 2, 3, 4, 5}, 8, 1},
                new Object[]{new int[]{8, 1, 2, 3, 4, 5, 6}, 6, 6},
                new Object[]{new int[]{8, 9, 10, 3, 4, 5, 6}, 11, -1},
                new Object[]{new int[]{8, 9, 10, 3, 4, 5, 6}, 2, -1},
        };
    }

    @Test(dataProvider = "findElement")
    public void findElementInSortedAndRotatedArray(int[] array, int element, int index) {
        int foundIndex = searchElement(array, element);
        assertEquals(foundIndex, index);
    }

    private int searchElement(final int[] array, final int element) {
        int pivot = findPivotR(array, 0, array.length - 1);
        if (pivot == -1) {
            return searchR(array, 0, array.length - 1, element);
        }
        if (element == array[pivot]) {
            return pivot;
        } else if(element >= array[0] && element < array[pivot]) {
            return searchR(array, 0, pivot - 1, element);
        } else {
            return searchR(array, pivot + 1, array.length - 1, element);
        }
    }

    private int searchR(final int[] array, final int start, final int end, int element) {
        if (start > end) {
            return -1;
        }
        int mid = (start + end) / 2;
        if (array[mid] == element) {
            return mid;
        }
        if (array[mid] > element) {
            return searchR(array, start, mid - 1, element);
        } else {
            return searchR(array, mid + 1, end, element);
        }
    }

}
