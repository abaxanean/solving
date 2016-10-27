/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.stream.IntStream;

import org.testng.annotations.Test;

import com.bax.euler.util.Primes;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=10
 * <p>
 * The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
 * <p>
 * Find the sum of all the primes below two million.
 */
public class P010_SummationOfPrimes extends Primes {

    @Test
    public void solve() {
        IntStream.range(2, 2_000_000).forEach(this::isPrime);
        long sum = primes.stream().mapToLong(Integer::longValue).sum();
        assertEquals(sum, 142913828922L);
    }

}
