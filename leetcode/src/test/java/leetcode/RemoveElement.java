package leetcode;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given an integer array nums and an integer val, remove all occurrences of val in nums in-place. The relative order of the elements may be changed.
 *
 * Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the first part of the array nums. More formally, if there are k elements after removing the duplicates, then the first k elements of nums should hold the final result. It does not matter what you leave beyond the first k elements.
 *
 * Return k after placing the final result in the first k slots of nums.
 *
 * Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory..
 */
public class RemoveElement {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{3,2,2,3}, 3, new int[]{2, 2}},
                {new int[]{0,1,2,2,3,0,4,2}, 2, new int[]{0,1,4,0,3}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] nums, int val, int[] expectedNums) {
        int k = removeElement(nums, val);
        assertEquals(k, expectedNums.length);
        for (int i = 0; i < k; i++) {
            assertEquals(nums[i], expectedNums[i]);
        }
    }

    public int removeElement(int[] nums, int val) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            nums[index] = nums[i];
            if (nums[i] != val) {
                index++;
            }
        }
        return index;
    }
}
