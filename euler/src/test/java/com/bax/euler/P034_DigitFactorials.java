/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.stream.IntStream;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=34
 */
public class P034_DigitFactorials {

    @Test
    public void solve() {
        int limit = findLimit();
        int sum = 0;
        for (int i = 3; i < limit; i++) {
            if (factorialSum(i) == i) {
                sum += i;
            }
        }
        assertEquals(sum, 40730);
    }

    private int findLimit() {
        int min = 1;
        int max = 2;
        int digits = 1;
        while (min < max) {
            min = (int)Math.pow(10, digits);
            max = digits * factorial(9);
            digits++;
        }
        return max;
    }

    private int factorialSum(int i) {
        int sum = 0;
        while (i > 0) {
            sum += factorial(i % 10);
            i = i/10;
        }
        return sum;
    }

    private int factorial(final int number) {
        if (number < 2) {
            return 1;
        }
        return IntStream.rangeClosed(1, number).reduce(1, (a, b) -> a * b);
    }

}
