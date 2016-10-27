/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.stream.IntStream;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=6
 * <pre>
 * The sum of the squares of the first ten natural numbers is,
 * 1^2 + 2^2 + ... + 10^2 = 385
 * The square of the sum of the first ten natural numbers is,
 * (1 + 2 + ... + 10)^2 = 552 = 3025
 * Hence the difference between the sum of the squares of the first ten natural numbers
 * and the square of the sum is 3025 − 385 = 2640.
 *
 * Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.
 * </pre>
 */
public class P006_SumSquareDifference {

    @Test
    public void solve() {
        int sumOfSquares = IntStream.rangeClosed(1, 100).map(i -> i * i).sum();
        int sum = IntStream.rangeClosed(1, 100).sum();
        int squareOfSum = sum * sum;
        assertEquals(Math.abs(sumOfSquares - squareOfSum), 25164150);
    }

}
