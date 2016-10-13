/*
 * Copyright 2015 Mobile Iron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import org.testng.annotations.Test;

import com.bax.euler.util.Primes;

import static org.testng.Assert.*;


/**
 * https://projecteuler.net/problem=3
 * <pre>
 * The prime factors of 13195 are 5, 7, 13 and 29.
 * What is the largest prime factor of the number 600851475143 ?
 * </pre>
 */
public class P003_LargestPrimeFactor extends Primes {

    @Test
    public void solve() {
        assertEquals(largestPrimeFactor(600851475143L), 6857);
    }

    int largestPrimeFactor(long n) {
        int sqrt = (int)Math.sqrt(n) + 1;

        int largestPrimeFactor = 1;
        for (int i = 2; i < sqrt; i ++) {
            if (isPrime(i) && n % i == 0) {
                largestPrimeFactor = i;
                n = n / i;
            }
        }
        return largestPrimeFactor;
    }

}
