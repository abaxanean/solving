/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

/**
 * Given an integer array nums of unique elements, return all possible subsets (the power set).
 * <p>
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 */
public class Subsets {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 2, 3}, asList(asList(), asList(1), asList(2), asList(3), asList(1, 2), asList(1, 3), asList(2, 3), asList(1, 2, 3))},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, List<List<Integer>> expected) {
        assertEquals(subsets(nums), expected);
    }


    List<List<Integer>> output = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
//        subsets(new ArrayDeque<>(nums.length), nums, 0);
//        return this.output;
        return new SubsetList(nums);
    }

    void subsets(Deque<Integer> current, int[] nums, int index) {
        this.output.add(new ArrayList<>(current));
        for (int i = index; i < nums.length; i++) {
            current.push(nums[i]);
            subsets(current, nums, i + 1);
            current.pop();
        }
    }

    private static class SubsetList extends AbstractList<List<Integer>> {

        private final int max;
        private final int[] nums;

        public SubsetList(final int[] nums) {
            this.max = (int)Math.pow(2, nums.length);
            this.nums = nums;
        }

        @Override
        public List<Integer> get(final int index) {
            return null;
        }

        @Override
        public int size() {
            return this.max;
        }

        @Override
        public Iterator<List<Integer>> iterator() {
            return new SublistIterator(this.nums);
        }
    }

    private static class SublistIterator implements Iterator<List<Integer>> {
        private int n = 0;
        private final int max;
        private final int[] nums;
        private final int len;

        public SublistIterator(final int[] nums) {
            this.max = (int)Math.pow(2, nums.length);
            this.nums = nums;
            this.len = nums.length;
        }

        @Override
        public boolean hasNext() {
            return this.n < max;
        }

        @Override
        public List<Integer> next() {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < this.len; i++) {
                if ((this.n & (1 << i)) > 0) {
                    list.add(this.nums[i]);
                }
            }
            this.n++;
            return list;
        }
    }

    public static void main(String[] args) {
        int n = 3;
        int nthBit = 1 << n;
        for (int i = 0; i < (int)Math.pow(2, n); ++i) {
            // generate bitmask, from 0..00 to 1..11
            String bitmask = Integer.toBinaryString(i | nthBit).substring(1);
            System.out.println(bitmask);
            System.out.println(Integer.toBinaryString(i));
        }
    }
}
