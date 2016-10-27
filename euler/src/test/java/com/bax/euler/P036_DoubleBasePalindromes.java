/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * https://projecteuler.net/problem=36
 */
public class P036_DoubleBasePalindromes {

    @Test
    public void solve() {
        int sum = 0;
        for (int i = 1; i < 1_000_000; i++) {
            String s = Integer.toString(i);
            if (s.equals(new StringBuilder(s).reverse().toString())) {
                s = Integer.toString(i, 2);
                if (s.equals(new StringBuilder(s).reverse().toString())) {
                    sum += i;
                }
            }
        }
        assertEquals(sum, 872187);
    }

}
