/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */
package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a collection of numbers, nums, that might contain duplicates, return all possible unique permutations in any order.
 */
public class PermutationsII {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 1, 2}, Arrays.asList(Arrays.asList(1, 1, 2), Arrays.asList(1, 2, 1), Arrays.asList(2, 1, 1))},
                {new int[]{1, 2, 3}, Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(1, 3, 2), Arrays.asList(2, 1, 3), Arrays.asList(2, 3, 1), Arrays.asList(3, 1, 2), Arrays.asList(3, 2, 1))},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, List<List<Integer>> expected) {
        assertEquals(permuteUnique(nums), expected);
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permuteUnique(nums, 0, result);
        return result;
    }

    private void permuteUnique(int[] nums, int index, List<List<Integer>> result) {
        if (index == nums.length - 1) {
            result.add(arrayToList(nums));
            return;
        }
        // the addition of this set is the only modification needed to handle duplicates
        Set<Integer> ints = new HashSet<>();
        for (int i = index; i < nums.length; i++) {
            if (ints.contains(nums[i])) {
                continue;
            }
            ints.add(nums[i]);
            // swap
            int temp = nums[index];
            nums[index] = nums[i];
            nums[i] = temp;
            // permute the rest
            permuteUnique(nums, index + 1, result);
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
