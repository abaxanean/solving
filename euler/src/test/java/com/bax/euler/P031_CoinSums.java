/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


/**
 * https://projecteuler.net/problem=31
 */
public class P031_CoinSums {

    @Test
    public void solve() {
        assertEquals(countWaysBetter(200, new int[]{ 1, 2, 5, 10, 20, 50, 100, 200 }), 73682);

    }

    private int countWaysBetter(int amount, int[] coins) {
        int[] ways = new int[amount+1];
        ways[0] = 1;

        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                ways[j] += ways[j - coins[i]];
            }
        }
        return ways[amount];
    }

    private int countWays(int amount) {
        int ways = 0;
        for (int a = amount; a >= 0; a -= 200) {
            for (int b = a; b >= 0; b -= 100) {
                for (int c = b; c >= 0; c -= 50) {
                    for (int d = c; d >= 0; d -= 20) {
                        for (int e = d; e >= 0; e -= 10) {
                            for (int f = e; f >= 0; f -= 5) {
                                for (int g = f; g >= 0; g -= 2) {
                                    ways++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return ways;
    }

}
