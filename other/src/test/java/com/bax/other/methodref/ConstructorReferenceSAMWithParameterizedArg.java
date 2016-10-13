/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.other.methodref;


import java.util.List;

/**
 * TODO: Document this interface.
 */
public interface ConstructorReferenceSAMWithParameterizedArg<T, U> {
    T magic(List<U> arg);
}
