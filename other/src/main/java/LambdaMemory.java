/*
 * Copyright 2018 MobileIron, Inc.
 * All rights reserved.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.LongSupplier;

public class LambdaMemory {

    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();

        new LambdaMemory().checkLambdaInstanceIsReused();
    }

    boolean makeMeReturnTrue(Object o1, Object o2) {
        return o1 == o2 && System.identityHashCode(o1) != System.identityHashCode(o2);
    }

    //    final LongSupplier member = this::usedMemory;
    final Runtime rt = Runtime.getRuntime();

    private void test() {
        LongSupplier supplier;
        long cycles = 0L;
        long used = 0L;
        int constantMemoryCount = 0;
        while (constantMemoryCount < 30_000) {
//            supplier = this.member;
            supplier = this::supplier;
            System.out.println(supplier.hashCode());
            if (cycles++ % 100000 == 0) {
                long newUsed = supplier.getAsLong();
                if (newUsed != used) {
                    used = newUsed;
                    System.out.println(newUsed);
                } else {
                    constantMemoryCount++;
                }
            }
        }
    }

    private void checkLambdaInstanceIsReused() {
//        // check by method reference
//        LongSupplier supplier;
//        for (int i = 0; i < 2; i++) {
//            supplier = this::supplier;
//            System.out.println(supplier.hashCode());
//        }
//
//        // check equals
//        final LongSupplier supplier1 = this::supplier;
//        final LongSupplier supplier2 = this::supplier;
//        System.out.println(supplier1 == supplier2);
//        System.out.println(supplier1.equals(supplier2));

        // check stream operation
        List<Object> list = new ArrayList<Object>() {

            @Override
            public void forEach(final Consumer action) {
                System.out.println(action.hashCode());
            }
        };
        for (int i = 0; i < 2; i++) {
            list.forEach(System.out::println);
        }
    }

    private long supplier() {
        return 0L;
    }

    private void printHashCode(LongSupplier suplier) {

    }

}
