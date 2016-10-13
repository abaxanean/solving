/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import org.testng.annotations.Test;

import com.bax.euler.util.Primes;

/**
 * TODO: Document this class.
 */
public class PrimesLessThan extends Primes {

    @Test
    public void primesLessThan() {
        int lessThan = 1000;
        // check only odd numbers
        for (int i = 3; i <= lessThan; i += 2) {
            isPrime(i);
        }
    }

}
