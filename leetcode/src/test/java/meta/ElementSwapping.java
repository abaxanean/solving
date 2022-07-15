/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.PriorityQueue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ElementSwapping {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{5, 3, 1}, 2, new int[]{1, 5, 3}},
                {new int[]{8, 9, 11, 2, 1}, 3, new int[]{2, 8, 9, 11, 1}},
                {new int[]{1, 2, 3, 1}, 3, new int[]{1, 1, 2, 3}},
                {new int[]{1, 2}, 3, new int[]{1, 2}},
                {new int[]{4, 3, 2, 1}, 3, new int[]{1, 4, 3, 2}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] arr, int k, int[] expected) {
        assertEquals(findMinArray(arr, k), expected);
    }

    int[] findMinArray(int[] arr, int k) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < Math.min(i + k + 1, arr.length); j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                k -= (minIndex - i);
                swap(arr, i, minIndex);
            }
        }
        return arr;
    }

    private void swap(final int[] arr, final int i1, final int i2) {
        for (int i = i2; i > i1; i--) {
            int temp = arr[i - 1];
            arr[i - 1] = arr[i];
            arr[i] = temp;
        }
    }

//    int[] findMinArray(int[] arr, int k) {
//        final PriorityQueue<Element> heap = new PriorityQueue<>((e1, e2) -> {
//            if (e1.value != e2.value) {
//                return e1.value - e2.value;
//            }
//            return e1.index - e2.index;
//        });
//        int runningMax = Integer.MIN_VALUE;
//        for (int i = 0; i < Math.min(k + 1, arr.length); i++) {
//            runningMax = Math.max(runningMax, arr[i]);
//            if (arr[i] < runningMax) {
//                heap.add(new Element(arr[i], i));
//            }
//        }
//        int index = 0;
//        while (k > 0 && !heap.isEmpty()) {
//            Element element = heap.poll();
//            if (element.index > k) {
//                continue;
//            }
//            k -= element.index;
//            swap(arr, index, element.index);
//            index++;
//        }
//        return arr;
//
//    }


    static class Element {
        int value;
        int index;

        public Element(final int value, final int index) {
            this.value = value;
            this.index = index;
        }
    }

}

//Tu-ntalezhi, Hrushiov, miner din donbas, el o porshit si o dat Crimeea Ukrainei.
//        Da eu inteleg, acuma citeshte ce inseamna sa recunosti hotarele altui stat.

// Daca nu ti-i destul memorandumul Budapest, poftim document aditional:
//Договор о дружбе, сотрудничестве и партнёрстве между Российской Федерацией и Украиной
//Semnat 31 mai 1997
//Intrat in vigoare  1 Aprilie 1999