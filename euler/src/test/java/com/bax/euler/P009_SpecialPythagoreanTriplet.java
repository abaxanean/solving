/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=9
 * <p>
 * A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,
 * <p>
 * a2 + b2 = c2
 * For example, 32 + 42 = 9 + 16 = 25 = 52.
 * <p>
 * There exists exactly one Pythagorean triplet for which a + b + c = 1000.
 * Find the product abc.
 */
public class P009_SpecialPythagoreanTriplet {

    @Test
    public void solve() {
        assertEquals(findTriplet(1000), 31875000);
    }

    int findTriplet(final int sum) {
        for (int a = 2; a < sum -4; a++) {
            for (int b = 2; b < sum -4; b++) {
                int c = sum - a - b;
                if (c * c == a * a + b * b) {
                    return a * b * c;
                }
            }
        }
        return -1;
    }

}
