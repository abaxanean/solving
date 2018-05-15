package com.bax.codefights;

import java.util.Arrays;

/**
 * You have an array of integers, and each integer denotes the length of a segment. Determine whether it is possible to pick three segments and construct a triangle out of them.
 * It is possible to construct a triangle if the combined length of the two shortest segments is greater than the length of the longest segment.
 */
public class Triangularity {

    public static void main(String[] args) {
        System.out.println(new Triangularity().triangularity(new int[]{1, 2, 3, 4}));
    }

boolean triangularity(int[] segments) {
    Arrays.sort(segments);
    for (int i = 0; i < segments.length - 2; i++) {
        if (segments[i] + segments[i + 1] > segments[i + 2]) {
            return true;
        }
    }
    return false;
}

}
