/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.euler;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * https://projecteuler.net/problem=40
 */
public class P040_ReciprocalCycles {

    @Test
    public void solve() {
        int produce = 1;
        int index = 0;
        int number = 0;
        int nextTarget = 10;
        while (index < 1_000_000) {
            number++;
            String numberS = Integer.toString(number);
            if (nextTarget - index <= numberS.length()) {
                produce *= Character.getNumericValue(numberS.charAt(nextTarget - index - 1));
                nextTarget *= 10;
            }
            index += numberS.length();
        }
        assertEquals(produce, 210);
    }
}
