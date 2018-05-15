package com.bax.other.unsigned;

/**
 * Created by abaxanean on 2/16/17.
 */
public class Unsigned {

    public static void main(String[] args) {
        int a = Integer.parseUnsignedInt("4294967295");
        Unsigned unsigned = new Unsigned();
        System.out.println(unsigned.compare(1, -1));
        byte b =-1;
        System.out.println(Byte.toUnsignedInt(b));
//        System.out.println(Integer.MIN_VALUE);
    }

    boolean compare(int a, int b) {
        return Integer.compare(a, b) != Integer.compareUnsigned(a, b);
    }

}
