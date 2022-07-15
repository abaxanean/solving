/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PassingYearbooks {


    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{2, 1}, new int[]{2, 2}},
                {new int[]{1, 2}, new int[]{1, 1}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, int[] expected) {
        assertEquals(findSignatureCounts(arr), expected);
    }

    int[] findSignatureCounts(int[] arr) {
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int signingStudent = arr[i];
            do {
                result[signingStudent - 1]++;
                signingStudent = arr[signingStudent - 1];
            } while (signingStudent != arr[i]);
        }
        return result;
    }

}
