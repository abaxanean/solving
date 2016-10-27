/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.stream.IntStream;

import org.testng.annotations.Test;

import com.bax.euler.util.Primes;

/**
 * https://projecteuler.net/problem=35
 */
public class P035_CircularPrimes extends Primes {

    @Test
    public void solve() {
        int count = 0;
        IntStream.range(3, 1_000_000).forEach(this::isPrime);
        for (int prime : primes) {
            if (isCircularPrime(prime)) {
                count++;
            }
        }
        System.out.println(count);
    }

    private boolean isCircularPrime(final int prime) {
        String s = Integer.toString(prime);
        for (int i = 1; i < s.length(); i ++) {
            s = rotate(s);
            if (!primesSet.contains(Integer.valueOf(s))) {
                return false;
            }
        }
        return true;
    }

    private String rotate(final String s) {
        return s.substring(1) + s.charAt(0);
    }

}
