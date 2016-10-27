/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import org.testng.annotations.Test;

/**
 * https://projecteuler.net/problem=28
 * <pre>
 * Starting with the number 1 and moving to the right in a clockwise direction a 5 by 5 spiral is formed as follows:
 * 21 22 23 24 25
 * 20  7  8  9 10
 * 19  6  1  2 11
 * 18  5  4  3 12
 * 17 16 15 14 13
 * It can be verified that the sum of the numbers on the diagonals is 101.
 * What is the sum of the numbers on the diagonals in a 1001 by 1001 spiral formed in the same way?
 * </pre>
 */
public class P028_NumberSpiralDiagonals {

    @Test
    public void solve() {
        int sum = 1;
        int number = 1;
        int increment = 0;
        for (int i = 3; i <= 1001; i += 2) {
            increment += 2;
            // computing the diagonal means adding sets of 4 numbers that differ by 2, than by 4 , than by 6 etc
            for(int j = 0; j < 4; j++) {
                number += increment;
                sum += number;
            }
        }
        System.out.println(sum);
    }

}
