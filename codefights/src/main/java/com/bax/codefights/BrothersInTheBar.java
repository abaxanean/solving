/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.codefights;

import java.util.Arrays;

public class BrothersInTheBar {

    int brothersInTheBar(int[] glasses) {
        int fullGlasses = glasses.length;
        int rounds = 0;
        boolean changed = true;
//        while (fullGlasses > 2 && changed) {
            changed = false;
            int size = glasses[0];
            int consecutive = 0;
            int index = 0;
            int[] cache = new int[]{0, 0, 0};
            while (++index < glasses.length) {
                if (glasses[index] == 0) {
                    continue;
                }
                if (size != glasses[index]) {
                    size = glasses[index];
                    consecutive = 0;
                    cache[0] = index;
                    continue;
                }
                if (size == glasses[index]) {
                    consecutive++;
                    cache[consecutive] = index;
                }
                if (consecutive == 2) {
                    fullGlasses -= 3;
                    rounds++;
                    changed = true;
                    Arrays.stream(cache).forEach(i -> glasses[i] = 0);
                    consecutive = 0;
                    size = 0;
                    int found = 0;
                    while (index > 0 && glasses[index] == 0 || found++ < 2) {
                        index--;
                    }
                    index = Math.max(0, index - 3);
                    size = glasses[index];
                }
            }

//        }
        return rounds;
    }
//
//    int brothers2InTheBar(int[] glasses) {
//       List<Integer> list = Arrays.stream(glasses).mapToObj(i -> i).collect(Collectors.toCollection(LinkedList::new));
//    }

    public static void main(String[] args) {
        int result = new BrothersInTheBar().brothersInTheBar(new int[]{6166, 8124, 10063, 10063, 10063, 12451, 19952, 22936, 31534, 32714, 39905, 39905, 39905, 32714, 32714, 31534, 31534, 22936, 22936, 19952, 19952, 12451, 12451, 8124, 8124, 6166, 6166, 39906, 45636, 45636, 45636, 46652, 56298, 56298, 56298, 46652, 46652, 56831, 56831, 61315, 61315, 61315, 65097, 65097, 67617, 71455, 71455, 78087, 78087, 78087, 71455, 85005, 85005, 93445, 93445, 93445, 85005, 67617, 67617, 65097, 103072, 103072, 103072, 108036, 108036, 113133, 113133, 113133, 116800, 116800, 120579, 120579, 120579, 121127, 121548, 126421, 130464, 137406, 137406, 137406, 130464, 142893, 147314, 152435, 152435, 152435, 147314, 147314, 142893, 142893, 130464, 126421, 126421, 121548, 121548, 121127, 121127, 116800, 108036, 56831});
        System.out.println(result);
    }
}
