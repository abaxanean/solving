/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.codefights;

public class MirrorBase {

    public static void main(String[] args) {
        boolean result = new MirrorBase().mirrorBase("121", 4, 2);
        System.out.println(result);
    }

boolean mirrorBase(String number, int base1, int base2) {
    String s = Long.toString(Long.parseLong(number, base1), base2);
    int i = 0, j = s.length() - 1;
    while (i < j) {
        if (s.charAt(i) != s.charAt(j)) {
            return false;
        }
        i++;
        j--;
    }
    return true;
}

}
