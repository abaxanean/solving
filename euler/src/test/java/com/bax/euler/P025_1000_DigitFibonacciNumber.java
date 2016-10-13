/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.math.BigInteger;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bax.euler.util.Primes;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=25
 * <pre>
 * The Fibonacci sequence is defined by the recurrence relation:
 * F(n) = F(n-1) + F(n-2), F(1) = 1, F(2) = 1
 * 1 1 2 3 5 8 13 ...
 * What is the index of the first term in the Fibonacci sequence to contain 1000 digits ?
 * </pre>
 */
public class P025_1000_DigitFibonacciNumber extends Primes {

    //cache first 10000 numbers
    BigInteger[] cache = new BigInteger[10000];

    @BeforeTest
    public void beforeTest() {
        cache[1] = BigInteger.valueOf(1L);
        cache[2] = BigInteger.valueOf(1L);
    }

    @Test
    public void solve() {
        int count = 1;
        for (BigInteger i = fib(count); i.toString().length() < 1000; i = fib(++count)) ;
        assertEquals(count, 4782);
    }

    private BigInteger fib(int i) {
        if (cache.length > i && cache[i] != null) {
            return cache[i];
        }
        BigInteger result = fib(i - 1).add(fib(i - 2));
        if (cache.length > i) {
            cache[i] = result;
        }
        return result;
    }

}
