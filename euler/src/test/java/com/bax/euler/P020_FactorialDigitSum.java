/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.math.BigInteger;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=20
 * <pre>
 * n! means n × (n − 1) × ... × 3 × 2 × 1
 * For example, 10! = 10 × 9 × ... × 3 × 2 × 1 = 3628800,
 * and the sum of the digits in the number 10! is 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.
 *
 * Find the sum of the digits in the number 100!
 * </pre>
 */
public class P020_FactorialDigitSum {

    @Test
    public void testIt() {
        BigInteger factorial = BigInteger.valueOf(1L);
        for (int i = 1; i <= 100; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        int sum = 0;
        String digits = factorial.toString();
        for(int i = 0; i < digits.length(); i++) {
            int digit = (int) (digits.charAt(i) - '0');
            sum = sum + digit;
        }
        assertEquals(sum, 648);
    }

}
