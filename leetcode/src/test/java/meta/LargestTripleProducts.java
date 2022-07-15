/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LargestTripleProducts {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 2, 3, 4, 5}, new int[]{-1, -1, 6, 24, 60}},
                {new int[]{2, 4, 7, 1, 5, 3}, new int[]{-1, -1, 56, 56, 140, 140}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, int[] expected) {
        assertEquals(findMaxProduct(arr), expected);
    }


    int[] findMaxProduct(int[] arr) {
        int[] result = new int[arr.length];
        int smallest = Integer.MIN_VALUE;
        int middle = Integer.MIN_VALUE;
        int largest = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > largest) {
                smallest = middle;
                middle = largest;
                largest = arr[i];
            } else if (arr[i] > middle) {
                smallest = middle;
                middle = arr[i];
            } else if(arr[i] > smallest) {
                smallest = arr[i];
            }
            if (i < 2) {
                result[i] = -1;
                continue;
            }
            result[i] = smallest * middle * largest;
        }
        return result;
    }
}
