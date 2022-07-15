/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package meta;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RotationalCipher {

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {"All-convoYs-9-be:Alert1.", 4, "Epp-gsrzsCw-3-fi:Epivx5."},
                {"abcdZXYzxy-999.@", 200, "stuvRPQrpq-999.@"},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(String input, int rotationFactor, String expected) {
        assertEquals(rotationalCipher(input, rotationFactor), expected);
    }

    String rotationalCipher(String input, int rotationFactor) {
        if (rotationFactor == 0) {
            return input;
        }
        char[] chars = input.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            chars[i] = rotate(chars[i], rotationFactor);
        }
        // Write your code here
        return new String(chars);
    }

    char rotate(char c, int rotationFactor) {
        if (c >= 'A' && c <= 'Z') {
            return rotate(c, 'A', rotationFactor, 26);
        }
        if (c >= 'a' && c <= 'z') {
            return rotate(c, 'a', rotationFactor, 26);
        }
        if (c >= '0' && c <= '9') {
            return rotate(c, '0', rotationFactor, 10);
        }
        return c;
    }

    char rotate(char c, char base, int rotationFactor, int alphabetLength) {
        int position = c - base;
        int newPosition = (position + rotationFactor) % alphabetLength;
        return (char) (base + newPosition);
    }
}
