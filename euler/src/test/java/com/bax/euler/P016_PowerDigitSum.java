/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.math.BigInteger;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=16
 * <p>
 * 2^15 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.
 * <p>
 * What is the sum of the digits of the number 2^1000?
 */
public class P016_PowerDigitSum {

    @Test
    public void solve() {
        BigInteger bigInteger = BigInteger.valueOf(2).pow(1000);
        int sum = bigInteger.toString().chars()
                .mapToObj(i -> (char)(i))
                .mapToInt(Character::getNumericValue)
                .sum();
        assertEquals(sum, 1366);
    }

}

