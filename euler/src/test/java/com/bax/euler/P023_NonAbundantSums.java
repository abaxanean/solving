/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import org.testng.annotations.Test;

import com.bax.euler.util.Divisors;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=23
 */
public class P023_NonAbundantSums extends Divisors {

    @Test
    public void solve() {
        int[] numbers = IntStream.rangeClosed(0, 28123).toArray();
        List<Integer> abundants = getAbundant(28123);
        for (int i = 0; i < abundants.size(); i++) {
            int index;
            for (int j = i; (index = abundants.get(i) + abundants.get(j)) < numbers.length; j++) {
                numbers[index] = 0;
            }
        }
        assertEquals(IntStream.of(numbers).sum(), 4179871);
    }

    private List<Integer> getAbundant(int under) {
        List<Integer> abundant = new ArrayList<>();
        for (int i = 1; i < under; i++) {
            Collection<Integer> divisors = divisors(i);
            int sum = divisors.stream().mapToInt(x -> x).sum();
            if (sum > i) {
                abundant.add(i);
            }
        }
        return abundant;
    }

}
