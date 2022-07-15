/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ContiguousSubarrays {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{3, 4, 1, 6, 2}, new int[]{1, 3, 1, 5, 1}},
                {new int[]{2, 4, 7, 1, 5, 3}, new int[]{1, 2, 6, 1, 3, 1}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, int[] expected) {
        assertEquals(countSubarrays(arr), expected);
    }

    int[] countSubarraysN2(int[] arr) {
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int count = 1;
            for (int j = i - 1; j >= 0 && arr[j] < arr[i]; j--) {
                count++;
            }
            for (int j = i + 1; j < arr.length && arr[j] < arr[i]; j++) {
                count++;
            }
            result[i] = count;
        }
        return result;
    }

    int[] countSubarrays(int[] arr) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] result = new int[arr.length];
        // fill with ones to account for the 1-item contiguous array that every element has
        Arrays.fill(result, 1);
        // count contiguous arrays from start to i, i.e. to the left of the element
        for (int i = 0; i < arr.length; i++) {
            int indexOfFurthestElementLessThan = i;
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                indexOfFurthestElementLessThan = stack.pop();
            }
            // if stack is empty, arr[i] is greater than all numbers to its left
            result[i] += stack.isEmpty() ? i : i - indexOfFurthestElementLessThan;
            stack.push(i);
        }
        stack.clear();
        // count contiguous arrays from i to end, i.e. to the right of the element
        for (int i = arr.length - 1; i >= 0; i--) {
            int indexOfFurthestElementLessThan = i;
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                indexOfFurthestElementLessThan = stack.pop();
            }
            // if stack is empty, arr[i] is greater than all numbers to its right
            result[i] += stack.isEmpty() ? arr.length - 1 - i : indexOfFurthestElementLessThan - i;
            stack.push(i);
        }
        return result;
    }


}
