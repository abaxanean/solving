/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=5
 * <pre>
 * 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
 * What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?
 * </pre>
 */
public class P005_SmallestMultiple {

    @Test
    public void solve() {
        assertEquals(smallestMultiple(20), 232792560);
    }

    private int smallestMultiple(int to) {
        Map<Integer, Integer> powers = new TreeMap<>();
        for (int i = 2; i <= to; i++) {
            Map<Integer, Integer> factors = primeFactors(i);
            factors.forEach((k, v) -> powers.merge(k, v, Integer::max));
        }
        return powers.entrySet().stream()
                .mapToInt(entry -> (int)Math.pow(entry.getKey(), entry.getValue()))
                .reduce(1, (i, j) -> i * j);
    }

    static Map<Integer, Integer> primeFactors(int number) {
        int n = number;
        Map<Integer, Integer> factors = new HashMap<>();
        for (int i = 2; i <= n / i; i++) {
            int power = 0;
            while (n % i == 0) {
                power++;
                n /= i;
            }
            if (power > 0) {
                factors.merge(i, power, Math::max);
            }
        }
        if (n > 1) {
            factors.put(n, 1);
        }
        return factors;
    }

}
