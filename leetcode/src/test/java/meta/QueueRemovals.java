/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.ArrayDeque;
import java.util.Deque;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * You're given a list of n integers arr, which represent elements in a queue (in order from front to back). You're also given an integer x, and must perform x iterations of the following 3-step process:
 * Pop x elements from the front of queue (or, if it contains fewer than x elements, pop all of them)
 * Of the elements that were popped, find the one with the largest value (if there are multiple such elements, take the one which had been popped the earliest), and remove it
 * For each one of the remaining elements that were popped (in the order they had been popped), decrement its value by 1 if it's positive (otherwise, if its value is 0, then it's left unchanged), and then add it back to the queue
 * Compute a list of x integers output, the ith of which is the 1-based index in the original array of the element which had been removed in step 2 during the ith iteration.
 */
public class QueueRemovals {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{1, 2, 2, 3, 4, 5}, 5, new int[]{5, 6, 4, 1, 2}},
                {new int[]{2, 4, 2, 4, 3, 1, 2, 2, 3, 4, 3, 4, 4}, 4, new int[]{2, 5, 10, 13}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, int x, int[] expected) {
        assertEquals(findPositions(arr, x), expected);
    }

    int[] findPositions(int[] arr, int x) {
        Deque<Element> queue = new ArrayDeque<>();
        int[] result = new int[x];
        for (int i = 0; i < arr.length; i++) {
            queue.addLast(new Element(arr[i], i + 1));
        }

        for (int i = 0; i < x; i++) {
            Element maxElement = null;
            Element currentElement = null;
            int j = 0;
            Element lastElement = queue.getLast();
            int max = -1;
            while (j < x && currentElement != lastElement) {
                currentElement = queue.removeFirst();
                if (currentElement.removed) {
                    continue;
                }
                j++;
                if (currentElement.value > max) {
                    maxElement = currentElement;
                    max = maxElement.value;
                }
                if (currentElement.value > 0) {
                    currentElement.value--;
                }
                queue.addLast(currentElement);
            }
            maxElement.removed = true;
            result[i] = maxElement.index;
        }
        return result;
    }

    static class Element {
        int value;
        int index;
        boolean removed;

        public Element(final int value, final int index) {
            this.value = value;
            this.index = index;
        }

        @Override
        public String toString() {
            return "{" +
                    "value=" + value +
                    ", index=" + index +
                    ", removed=" + removed +
                    '}';
        }
    }
}
