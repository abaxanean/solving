/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

public class GCD {

    public static void main(String[] args) {
        int arr[] = { 2, 3 };
        System.out.println(generalizedGCD(arr.length, arr));
    }

    public static int generalizedGCD(int num, int[] arr) {
        int result = arr[0];
        for (int i = 1; i < num; i++)
            result = gcd(arr[i], result);

        return result;
    }

    static int gcd(int a, int b) {
        if (a == 0) {
            return b;
        }
        return gcd(b % a, a);
    }
}
