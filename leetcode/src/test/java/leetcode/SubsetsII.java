/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static java.util.Arrays.asList;

/**
 * Given an integer array nums that may contain duplicates, return all possible subsets (the power set).
 *
 * The solution set must not contain duplicate subsets. Return the solution in any order.
 */
public class SubsetsII {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1,2,2}, Arrays.asList(Collections.emptyList(),asList(1),asList(1,2),asList(1,2,2),asList(2),asList(2,2))},
                {new int[]{4,4,4,1,4}, Arrays.asList(Collections.emptyList(),asList(1),asList(4),asList(1, 4),asList(1, 4, 4),asList(1, 4, 4, 4), asList(1, 4, 4, 4, 4), asList(4, 4), asList(4, 4, 4), asList(4, 4, 4, 4))},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, List<List<Integer>> expected) {
        assertEquals(subsetsWithDup(nums), expected);
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        subsetsWithDup(nums, 0, new ArrayDeque<>(nums.length));
        return this.result;
    }

    List<List<Integer>> result = new ArrayList<>();

    public void subsetsWithDup(int[] nums, int index, Deque<Integer> current) {
        this.result.add(new ArrayList<>(current));
        for(int i = index; i < nums.length; i++) {
            if (i > index && nums[i] == nums[i-1]) {
                continue;
            }
            current.push(nums[i]);
            subsetsWithDup(nums, i + 1, current);
            current.pop();
        }
    }


    // copied from LC

    private static final int MIN = -10;
    private static final int MAX = 10;
    public static final int LENGTH = MAX - MIN + 1;

    public List<List<Integer>> subsetsWithDup2(int[] nums) {
        int[] freq = new int[LENGTH];
        for (int num : nums) {
            freq[num - MIN]++;
        }
        ArrayList<List<Integer>> subsets = new ArrayList<>();
        subsets2(freq, 0, new int[LENGTH], subsets);
        return subsets;

    }

    private void subsets2(int[] freq, int i, int[] subset, List<List<Integer>> subsets) {
        if (i == freq.length) {
            ArrayList<Integer> s = new ArrayList<>();
            for (int j = 0; j < subset.length; j++) {
                for (int k = 0; k < subset[j]; k++)
                    s.add(j + MIN);
            }
            subsets.add(s);
        } else {
            for (int j = 0; j <= freq[i]; j++) {
                subset[i] = j;
                subsets2(freq, i + 1, subset, subsets);
            }
        }
    }

}
