/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.LinkedHashSet;
import java.util.Set;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=26
 */
public class P026_ReciprocalCycles {

    @Test
    public void solve() {
        int max = Integer.MIN_VALUE;
        int maxI = -1;
        for (int i = 2; i < 1000; i++) {
            int cycle = length(i);
            if (cycle > max) {
                max = cycle;
                maxI = i;
            }

        }
        assertEquals(maxI, 983);
    }

    private int length(final int divisor) {
        Set<Integer> remainders = new LinkedHashSet<>();
        int remainder = 1 % divisor;
        do {
            remainder *= 10;
            remainder = remainder % divisor;
        } while (remainders.add(remainder));
        return remainders.stream()
                .map(Object::toString)
                .mapToInt(String::length)
                .sum();
    }

}
