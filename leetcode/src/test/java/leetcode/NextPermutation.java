package leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
 * <p>
 * If such an arrangement is not possible, it must rearrange it as the lowest possible order (i.e., sorted in ascending order).
 * <p>
 * The replacement must be in place and use only constant extra memory.
 */
public class NextPermutation {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 2, 3}, new int[]{1, 3, 2}},
                {new int[]{1, 3, 2}, new int[]{2, 1, 3}},
                {new int[]{2, 3, 1}, new int[]{3, 1, 2}},
                {new int[]{3, 2, 1}, new int[]{1, 2, 3}},
                {new int[]{1, 1, 5}, new int[]{1, 5, 1}},
                {new int[]{1}, new int[]{1}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int[] expected) {
        nextPermutation(nums);
        assertEquals(nums, expected);
    }

    public void nextPermutation(int[] nums) {
        int index = nums.length - 2;
        while (index >= 0 && nums[index] >= nums[index + 1]) {
            index--;
        }
        if (index == -1) {
            // the given permutation is the max, reverse to get the min
            reverse(nums, 0);
            return;
        }
        int swapIndex = nums.length - 1;
        // find the first number from the end bigger than nums[index]
        while (nums[swapIndex] <= nums[index]) {
            swapIndex--;
        }
        // swap, this will make our number bigger
        int temp = nums[index];
        nums[index] = nums[swapIndex];
        nums[swapIndex] = temp;
        // the numbers to the right of index are in descending order, we need them is ascending order
        reverse(nums, index + 1);
    }


    private void reverse(final int[] nums, int from) {
        for (int i = from, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
