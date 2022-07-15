package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that each unique element appears only once. The relative order of the elements should be kept the same.
 * <p>
 * Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the first part of the array nums. More formally, if there are k elements after removing the duplicates, then the first k elements of nums should hold the final result. It does not matter what you leave beyond the first k elements.
 * <p>
 * Return k after placing the final result in the first k slots of nums.
 * <p>
 * Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory.
 */
public class RemoveDuplicates {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 1, 2}, new int[]{1, 2}},
                {new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}, new int[]{0, 1, 2, 3, 4}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int[] expectedNums) {
        int k = removeDuplicates(nums);
        assertEquals(k, expectedNums.length);
        for (int i = 0; i < k; i++) {
            assertEquals(nums[i], expectedNums[i]);
        }
    }

    public int removeDuplicates(int[] nums) {
        int shifts = 0;
        for (int i = 1; i < nums.length; i++) {
            int j = i - shifts;
            nums[j] = nums[i];
            if (nums[j] == nums[j - 1]) {
                shifts++;
            }
        }
        return nums.length - shifts;
    }
}
