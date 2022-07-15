/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a list of N triangles with integer side lengths, determine how many different triangles there are. Two triangles are considered to be the same if they can both be placed on the plane such that their vertices occupy exactly the same three points.
 */
public class CountingTriangles {



    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {Arrays.asList(new Sides(2, 2, 3), new Sides(3, 2, 2), new Sides(2, 5, 6)), 2},
                {Arrays.asList(new Sides(8, 4, 6), new Sides(100, 101, 102), new Sides(84, 93, 173)), 3},
                {Arrays.asList(new Sides(5, 8, 9), new Sides(5, 9, 8), new Sides(9, 5, 8),
                        new Sides(9, 8, 5), new Sides(8, 9, 5), new Sides(8, 5, 9)), 1},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(List<Sides> arr, int expected) {
        assertEquals(countDistinctTriangles(arr), expected);
    }


    int countDistinctTriangles(List<Sides> arr) {
        int result = 0;
        Set<Sides> sides = new HashSet<>();
        for (Sides side : arr) {
            if (sides.add(side)) {
                result++;
            }
        }
        return result;
    }

    static class Sides {

        int[] arr;
        int hash;

        Sides(int a, int b, int c) {
            this.arr = new int[]{a, b, c};
            Arrays.sort(arr);
            this.hash = Arrays.hashCode(this.arr);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            final Sides that = (Sides)o;
            return Arrays.equals(this.arr, that.arr);
        }

        @Override
        public int hashCode() {
            return this.hash;
        }
    }


}
