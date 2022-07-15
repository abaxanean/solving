/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * An n-bit gray code sequence is a sequence of 2n integers where:
 * <p>
 * Every integer is in the inclusive range [0, 2n - 1],
 * The first integer is 0,
 * An integer appears no more than once in the sequence,
 * The binary representation of every pair of adjacent integers differs by exactly one bit, and
 * The binary representation of the first and last integers differs by exactly one bit.
 * Given an integer n, return any valid n-bit gray code sequence.
 */
public class GrayCode {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {2, Arrays.asList(0, 1, 3, 2)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int n, List<Integer> expected) {
        assertEquals(grayCode2(n), expected);
    }

    public List<Integer> grayCode2(int n) {
        int count = (int)Math.pow(2, n);
        List<Integer> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(i ^ (i >> 1));
        }
        return result;
    }

    public List<Integer> grayCode(int n) {
        return new GrayList(n);
    }

    private static class GrayList extends AbstractList<Integer> {

        private final int max;

        public GrayList(final int n) {
            this.max = (int)Math.pow(2, n);
        }

        @Override
        public Integer get(final int index) {
            return index ^ (index >> 1);
        }

        @Override
        public int size() {
            return this.max;
        }

        @Override
        public Iterator<Integer> iterator() {
            return new GrayCodeIterator(this.max);
        }
    }

    private static class GrayCodeIterator implements Iterator<Integer> {
        private int n = 0;
        private final int max;

        public GrayCodeIterator(final int max) {
            this.max = max;
        }

        @Override
        public boolean hasNext() {
            return this.n < max;
        }

        @Override
        public Integer next() {
            return this.n ^ (this.n++ >> 1);
        }
    }


}
