/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

/**
 * https://projecteuler.net/problem=29
 */
public class P029_DistinctPowers {

    @Test
    public void solve() {
        System.out.println(solve(100, 100));
    }

    private int solve(final int a, final int b) {
        Set<BigInteger> set = new HashSet<>(a * b);
        for (int i = 2; i <= a; i++) {
            for (int j = 2; j <= b; j++) {
                set.add(BigInteger.valueOf(i).pow(j));
            }
        }
        return set.size();
    }

}
