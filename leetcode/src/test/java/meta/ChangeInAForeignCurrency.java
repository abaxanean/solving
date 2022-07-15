/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * You likely know that different currencies have coins and bills of different denominations. In some currencies, it's actually impossible to receive change for a given amount of money. For example, Canada has given up the 1-cent penny. If you're owed 94 cents in Canada, a shopkeeper will graciously supply you with 95 cents instead since there exists a 5-cent coin.
 * Given a list of the available denominations, determine if it's possible to receive exact change for an amount of money targetMoney. Both the denominations and target amount will be given in generic units of that currency.
 */
public class ChangeInAForeignCurrency {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {94, new int[]{5, 10, 25, 100, 200}, false},
                {75, new int[]{4, 17, 29}, true},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int targetMoney, int[] denominations, boolean expected) {
        assertEquals(canGetExactChange(targetMoney, denominations), expected);
    }


    boolean canGetExactChange(int targetMoney, int[] denominations) {
        return canGetExactChange(targetMoney, 0, denominations);

    }

    boolean canGetExactChange(int amountLeft, int index, int[] denominations) {
        if (amountLeft == 0) {
            return true;
        }
        if (amountLeft < 0 || index == denominations.length) {
            return false;
        }
        // optimization for early match
        int denomination = denominations[index];
        if (amountLeft % denomination == 0) {
            return true;
        }
        return canGetExactChange(amountLeft, index + 1, denominations)
                || canGetExactChange(amountLeft - denominations[index], index, denominations);
    }
}
