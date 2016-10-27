/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.math.BigInteger;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * https://projecteuler.net/problem=48
 * <pre>
 * The series, 1^1 + 2^2 + 3^3 + ... + 10^10 = 10405071317.
 * Find the last ten digits of the series, 1^1 + 2^2 + 3^3 + ... + 1000^1000.
 * </pre>
 */
public class P048_SelfPowers {

    @Test
    public void solve() {
        BigInteger sum = BigInteger.valueOf(0);
        for (int i = 1; i <= 1000; i++) {
            sum = sum.add(BigInteger.valueOf(i).pow(i));
        }
        String s = sum.toString();
        assertEquals(s.substring(s.length() - 10, s.length()), "9110846700");
    }
}
