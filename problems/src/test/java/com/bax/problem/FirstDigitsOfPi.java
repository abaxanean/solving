/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.problem;

import org.testng.annotations.Test;

/**
 * TODO: Document this class.
 */
public class FirstDigitsOfPi {

    @Test//(enabled = false)
    public void testIt() {
        System.out.println(computePiWithPrecision(7));
    }

    private double computePiWithPrecision(int digits) {
        double precision = (double)1 / Math.pow(10, digits + 1);
        int s = 4;
        int i = 1;
        double previous;
        double current = (double)s;
        do {
            i += 2;
            s = -s;
            previous = current;
            current += (double)s / i;
        } while (Math.abs(previous - current) >= precision);
        return current;
    }

}
