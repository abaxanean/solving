/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindDuplicates {

    static int[] findDuplicates(int[] arr1, int[] arr2) {
        int i = 0, j = 0;
        List<Integer> list = new ArrayList<>();
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] == arr2[j]) {
                list.add(arr1[i]);
                i++;
                j++;
            } else if (arr1[i] < arr2[j]) {
                i++;
            } else {
                j++;
            }
        }
        return list.stream().mapToInt(a -> a).toArray();
    }

    public static void main(String[] args) {
        int[] result = findDuplicates(new int[]{1, 2, 3}, new int[]{3, 4, 5});
        System.out.println(Arrays.toString(result));
    }
}
