/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=1
 * <pre>
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9.
 * The sum of these multiples is 23.
 *
 * Find the sum of all the multiples of 3 or 5 below 1000.
 * </pre>
 */
public class P001_MultiplesOf3And5 {

    @Test
    public void solve() {
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            if (i % 3 == 0 || i % 5 == 0) {
                sum += i;
            }
        }
        assertEquals(sum, 233168);
    }

}
