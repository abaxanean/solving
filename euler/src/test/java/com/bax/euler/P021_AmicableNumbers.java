/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.testng.annotations.Test;

import com.bax.euler.util.Divisors;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=21
 * <pre>
 * Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
 * If d(a) = b and d(b) = a, where a ≠ b, then a and b are an amicable pair and each of a and b are called amicable numbers.
 * For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284.
 * The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.
 *
 * Evaluate the sum of all the amicable numbers under 10000.
 * </pre>
 */
public class P021_AmicableNumbers extends Divisors {

    @Test
    public void solve() {
        List<Integer> amicable = new ArrayList<>();
        for (int i = 1; i < 10000; i++) {
            Collection<Integer> divisors = divisors(i);
            int sum = divisors.stream().mapToInt(j -> j).sum();
            // we add 2 numbers at once, for one of them the sum of divisors is greater than the number
            if (sum > i) {
                continue;
            }
            int sumDivisorsSum = divisors(sum).stream().mapToInt(j -> j).sum();
            if (sumDivisorsSum == i && sum != i) {
                // we add 2 numbers at once
                amicable.add(i);
                amicable.add(sum);
            }
        }
        System.out.println(amicable);
        assertEquals(amicable.stream().mapToInt(j -> j).sum(), 31626);
    }

}
