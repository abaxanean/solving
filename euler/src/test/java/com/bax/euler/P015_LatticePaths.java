/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=15
 * <pre>
 * Starting in the top left corner of a 2×2 grid, and only being able to move to the right and down,
 * there are exactly 6 routes to the bottom right corner.
 *
 * How many such routes are there through a 20×20 grid?
 * </pre>
 */
public class P015_LatticePaths {
    Map<Pair, Long> cache = new HashMap<>();

    @Test
    public void solve() {
        assertEquals(compute(20, 20), 137846528820L);
    }

    /**
     * Compute the number of ways you can get from 0,0 to x,y
     *
     * @param x grid width
     * @param y grid height
     * @return number of lattice paths
     */
    long compute(int x, int y) {
        Pair pair = new Pair(x, y);
        if (cache.containsKey(pair)) {
            return cache.get(pair);
        }
        if (x == 0 || y == 0) {
            return 1;
        }
        long result = compute(x - 1, y) + compute(x, y - 1);
        cache.put(pair, result);
        return result;
    }

    static class Pair {
        final int x, y;

        public Pair(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(final Object o) {
            final Pair pair = (Pair)o;
            return this.x == pair.x && this.y == pair.y;
        }

        @Override
        public int hashCode() {
            return x * 37 + y * 31;
        }
    }

}
