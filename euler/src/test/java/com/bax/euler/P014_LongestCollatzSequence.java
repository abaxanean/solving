/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * https://projecteuler.net/problem=14
 * <pre>
 * The following iterative sequence is defined for the set of positive integers:
 * n → n/2 (n is even)
 * n → 3n + 1 (n is odd)
 * Using the rule above and starting with 13, we generate the following sequence:
 * 13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1
 * It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms.
 * Although it has not been proved yet (Collatz Problem), it is thought that all starting numbers finish at 1.
 * Which starting number, under one million, produces the longest chain?
 * NOTE: Once the chain starts the terms are allowed to go above one million.
 * </pre>
 */
public class P014_LongestCollatzSequence {

    @Test
    public void solve() {
        int maxChain = 0;
        int number = 0;
        for (int i = 1; i < 1_000_000; i++) {
            long j = i;
            int currentChain = 1;
            do {
                if (j % 2 == 0) {
                    j = j / 2;
                } else {
                    j = 3 * j + 1;
                }
                currentChain++;
            } while (j > 1);
            if (currentChain > maxChain) {
                maxChain = currentChain;
                number = i;
            }
        }
        assertEquals(number, 837799);
    }
}
