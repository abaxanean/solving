/*
 * Copyright 2015 Mobile Iron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import org.testng.annotations.Test;

import com.bax.euler.util.Primes;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=7
 * <p>
 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
 * What is the 10 001st prime number?
 */
public class P007_10001stPrime extends Primes {

    @Test
    public void solve() {
        assertEquals(nthPrime(10_001), 104743);
    }

    int nthPrime(int n) {
        int i = 3;
        while (primes.size() < n) {
            isPrime(i);
            // check only odd numbers
            i += 2;
        }
        return primes.getLast();
    }

}
