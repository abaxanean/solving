/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class _1_BillionUsers {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new float[]{1.5f}, 52},
                {new float[]{1.1f, 1.2f, 1.3f}, 79},
                {new float[]{1.01f, 1.02f}, 1047},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(float[] growthRates, int expected) {
        assertEquals(getBillionUsersDay(growthRates), expected);
    }

    int getBillionUsersDay(float[] growthRates) {
        int days = 0;
        double users;
        do {
            days++;
            users = 0.0d;
            for (int i = 0; i < growthRates.length; i++) {
                users += Math.pow(growthRates[i], days);
            }
        } while (users < 1_000_000_000);
        return days;
    }
}
