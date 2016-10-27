/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.other;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class StatComputer {

    @Test
    public void computeStats() {
//        System.out.println(TimeUnit.DAYS.toMillis(1));
//        System.out.println(24 * 3600 * 1000L);
        assertEquals(TimeUnit.MILLISECONDS.convert(2, TimeUnit.MINUTES), TimeUnit.MINUTES.toMillis(2));
//        int sumDK = 0;
//        int sumMG = 0;
//        double deltaDK = 0.0;
//        double deltaMG = 0.0;
//        for (int i = 1; i < 1000; i++) {
//            sumDK += 5;
//            sumMG += 7;
//            if (i > 220) {
//                sumDK += 1;
//            }
//            if (i > 350) {
//                deltaDK += 0.3;
//                deltaMG += 0.35;
//                sumDK = (int)(sumDK + deltaDK);
//                sumMG = (int)(sumMG + deltaMG);
//                System.out.println((int)deltaDK + " " + (int)deltaMG);
//                System.out.println(String.format("DK : %8s : %10s", i, sumDK));
//                System.out.println(String.format("MG : %8s : %10s", i, sumMG));
//            }
//        }

    }

}
