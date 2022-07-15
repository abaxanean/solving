/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class MedianStream {


    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{5, 15, 1, 3}, new int[]{5, 10, 5, 4}},
                {new int[]{2, 4, 7, 1, 5, 3}, new int[]{2, 3, 4, 3, 4, 3}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, int[] expected) {
        assertEquals(findMedian(arr), expected);
    }



    int[] findMedian(int[] arr) {
        // use two heaps, all numbers in the max heap are less than equal to numbers in the min heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.naturalOrder());
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.<Integer>naturalOrder().reversed());
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if (minHeap.isEmpty() && maxHeap.isEmpty()) {
                maxHeap.add(arr[i]);
                result[i] = arr[i];
                continue;
            }
            if (arr[i] > maxHeap.peek()) {
                minHeap.add(arr[i]);
            } else {
                maxHeap.add(arr[i]);
            }
            // balance the heaps
            if (maxHeap.size() - minHeap.size() > 1) {
                minHeap.add(maxHeap.poll());
            }
            if (minHeap.size() - maxHeap.size() > 1) {
                maxHeap.add(minHeap.poll());
            }
            if (i % 2 == 1) {
                result[i] = (maxHeap.peek() + minHeap.peek()) / 2;
            } else {
                result[i] = maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek();
            }
        }
        return result;
    }
}
