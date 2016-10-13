/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=4
 * <pre>
 * A palindromic number reads the same both ways.
 * The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
 *
 * Find the largest palindrome made from the product of two 3-digit numbers.
 * </pre>
 */
public class P004_LargestPalindromeProduct {

    @Test
    public void solve() {
        int max = Integer.MIN_VALUE;
        for (int i = 100; i < 999; i++) {
            for (int j = 100; j < 999; j++) {
                int product = i * j;
                String productString = Integer.toString(product);
                if (productString.equals(new StringBuilder(productString).reverse().toString())) {
                    max = Math.max(product, max);
                }
            }
        }
        assertEquals(max, 906609);
    }

}
