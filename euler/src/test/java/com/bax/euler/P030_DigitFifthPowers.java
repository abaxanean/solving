/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.stream.IntStream;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * https://projecteuler.net/problem=30
 * <pre>
 * Surprisingly there are only three numbers that can be written as the sum of fourth powers of their digits:
 * 1634 = 1^4 + 6^4 + 3^4 + 4^4
 * 8208 = 8^4 + 2^4 + 0^4 + 8^4
 * 9474 = 9^4 + 4^4 + 7^4 + 4^4
 * </pre>
 */
public class P030_DigitFifthPowers {

    @Test
    public void solve() {
        assertEquals(solve(5), 443839);
    }

    public int solve(int power) {
        int limit = findLimit(power);
        int sum = 0;
        for (int i = 10; i < limit; i++) {
            if (sumOfPower(i) == i) {
                sum += i;
            }
        }
        return sum;
    }

    private int findLimit(final int power) {
        int digits = 1;
        int min = 0, max = 1;
        while(min < max) {
            digits++;
            min = (int)Math.pow(10, digits - 1);
            max = IntStream.range(0, digits).map(i -> (int) Math.pow(9, power)).sum();
        }
        return IntStream.range(1, digits).map(i -> (int) Math.pow(9, power)).sum();
    }

    private int sumOfPower(int i) {
        int sum = 0;
        while (i > 0) {
            int j = i % 10;
            sum += Math.pow(j, 5);
            i = i / 10;
        }
        return sum;
    }

}
