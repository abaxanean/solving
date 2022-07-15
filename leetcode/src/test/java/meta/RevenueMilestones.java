/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import java.util.Arrays;
import java.util.Comparator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RevenueMilestones {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100}, new int[]{100, 200, 500}, new int[]{4, 6, 10}},
                {new int[]{100, 200, 300, 400, 500}, new int[]{300, 800, 1000, 1400}, new int[]{2, 4, 4, 5}},
                {new int[]{700, 800, 600, 400, 600, 700}, new int[]{3100, 2200, 800, 2100, 1000}, new int[]{5, 4, 2, 3, 2}},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] revenues, int[] milestones, int[] expected) {
        assertEquals(getMilestoneDays(revenues, milestones), expected);
    }

    int[] getMilestoneDays(int[] revenues, int[] milestones) {
        Milestone[] milestoneArray = new Milestone[milestones.length];
        for (int i = 0; i < milestones.length; i++) {
            milestoneArray[i] = new Milestone(milestones[i], i);
        }
        Arrays.sort(milestoneArray, Comparator.comparingInt(m -> m.milestone));
        int sum = 0;
        int mileStoneIndex = 0;
        int[] result = new int[milestones.length];
        Arrays.fill(result, -1);
        for (int i = 0; i < revenues.length; i++) {
            sum += revenues[i];
            while (mileStoneIndex < milestones.length && sum >= milestoneArray[mileStoneIndex].milestone) {
                result[milestoneArray[mileStoneIndex].index] = i + 1;
                mileStoneIndex++;
            }
        }
        return result;
    }

    static class Milestone {
        int milestone;
        int index;

        public Milestone(final int milestone, final int index) {
            this.milestone = milestone;
            this.index = index;
        }
    }

}
