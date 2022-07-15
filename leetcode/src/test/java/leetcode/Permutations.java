/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.
 */
public class Permutations {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 2, 3}, Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(1, 3, 2), Arrays.asList(2, 1, 3), Arrays.asList(2, 3, 1), Arrays.asList(3, 1, 2), Arrays.asList(3, 2, 1))},
                {new int[]{0, 1}, Arrays.asList(Arrays.asList(0, 1), Arrays.asList(1, 0))},
                {new int[]{1}, Collections.singletonList(Collections.singletonList(1))},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, List<List<Integer>> expected) {
        assertEquals(permute(nums), expected);
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permute(nums, 0, result);
        return result;
    }

    private void permute(int[] nums, int index, List<List<Integer>> result) {
        if (index == nums.length - 1) {
            result.add(arrayToList(nums));
            return;
        }
        for (int i = index; i < nums.length; i++) {
            // swap
            int temp = nums[index];
            nums[index] = nums[i];
            nums[i] = temp;
            // permute the rest
            permute(nums, index + 1, result);
            // swap back
            nums[i] = nums[index];
            nums[index] = temp;
        }
    }

    List<Integer> arrayToList(int[] nums) {
        List<Integer> permutation = new ArrayList<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            permutation.add(nums[i]);
        }
        return permutation;
    }


}
