/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.other.methodref;

/**
 * TODO: Document this class.
 */
public class MethodReference {

    public static String toUpperStatic(String input)
    {
        return input.toUpperCase();
    }

    public String toUpperInstance(String input)
    {
        return input.toUpperCase();
    }

    public String toUpperFromThis(String input)
    {
        MethodReferenceSAM sam = this::toUpperInstance;
        return sam.process(input);
    }

    public static void main(String... args)
    {
//        MethodReferenceSAM sam = MethodReference::toUpperStatic;
//        System.out.println("\n\n\tstatic method reference = "+sam.process("static_input"));
//
//        MethodReference methodReference = new MethodReference();
//        sam = methodReference::toUpperInstance;
//        System.out.println("\n\n\tinstance method reference = "+sam.process("instance_input"));
//
//        System.out.println("\n\n\t\"this\" method reference = "+methodReference.toUpperFromThis("this_input"));
//        System.out.println("");
        nextIndex(8, 7);
        nextIndexObvious(8, 7);
    }

    private static void nextIndex(int i, int len) {
        System.out.println(((i + 1 < len) ? i + 1 : 0));
    }

    private static void nextIndexObvious(int i, int len) {
        System.out.println((i + 1) % len);
    }

}
