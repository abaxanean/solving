/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.stream.IntStream;

import org.testng.annotations.Test;

import com.bax.euler.util.Primes;

/**
 * https://projecteuler.net/problem=27
 */
public class P027_QuadraticPrimes extends Primes {

    @Test
    public void solve() {
        int max = Integer.MIN_VALUE;
        int aMax = 0;
        int bMax = 0;
        for (int a = -999; a < 1000; a ++) {
            for (int b = -999; b < 1000; b ++) {
                int primes = countPrimes(a,b);
                if (primes > max) {
                    aMax = a;
                    bMax = b;
                    max = primes;
                }
            }
        }
        System.out.printf("%s %s %s", max, aMax, bMax);
        System.out.println(aMax * bMax);
    }

    private int countPrimes(final int a, final int b) {
        int count = 0;
        for (int n = 0; ; n++) {
            int value = compute(a, b, n);
            if (isAPrime(value)) {
                count++;
            } else {
                return count;
            }
        }
    }

    private boolean isAPrime(final int value) {
        if (value < 2) {
            return false;
        }
        if (primes.getLast() < value) {
            // make sure all the primes up to the number are computed
            IntStream.range(primes.getLast() + 1, value).forEach(this::isPrime);
            return isPrime(value);
        } else {
            return primesSet.contains(value);
        }
    }

    private int compute(int a, int b, int n) {
        return n * n + a * n + b;
    }
}
