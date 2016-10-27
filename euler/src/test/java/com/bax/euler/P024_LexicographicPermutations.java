/*
 * Copyright 2016 MobileIronInc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.Arrays;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * https://projecteuler.net/problem=24
 * <pre>
 * A permutation is an ordered arrangement of objects. For example3124 is one possible permutation of the digits
 * 123 and 4. If all of the permutations are listed numerically or alphabeticallywe call it lexicographic order.
 * The lexicographic permutations of 01 and 2 are: 012   021   102   120   201   210
 *
 * What is the millionth lexicographic permutation of the digits 012345678 and 9?
 * </pre>
 */
public class P024_LexicographicPermutations {

    int[] s = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    int count = 0;
    int[] result;

    @Test
    public void solve() {
        orderedPermutations(new int[]{}, s);
        assertEquals(Arrays.toString(result).replaceAll("\\D", ""), "2783915460");
    }

    private void unorderedPermutations(int[] prefix, int[] suffix) {
        if (suffix.length == s.length) {
            System.out.println(Arrays.toString(suffix));
            return;
        }
        int last = prefix[prefix.length - 1];
        for (int i = 0; i <= suffix.length; i++) {
            int[] newSuffix = new int[suffix.length + 1];
            System.arraycopy(suffix, 0, newSuffix, 0, i);
            newSuffix[i] = last;
            System.arraycopy(suffix, i, newSuffix, i + 1, suffix.length - i);
            unorderedPermutations(Arrays.copyOfRange(prefix, 0, prefix.length - 1), newSuffix);
        }

    }

    private void orderedPermutations(int[] prefix, int[] suffix) {
        if (prefix.length == s.length && ++ count == 1_000_000) {
            result = prefix;
            return;
        }
        for (int i = 0; i < suffix.length; i++) {
            int[] newPrefix = Arrays.copyOf(prefix, prefix.length + 1);
            newPrefix[prefix.length] = suffix[i];
            int[] newSuffix = new int[suffix.length - 1];
            System.arraycopy(suffix, 0, newSuffix, 0, i);
            System.arraycopy(suffix, i + 1, newSuffix, i, newSuffix.length - i);
            orderedPermutations(newPrefix, newSuffix);
        }
    }
}
