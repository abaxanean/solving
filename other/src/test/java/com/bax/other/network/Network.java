/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.other.network;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;

/**
 * TODO: Document this class.
 */
public class Network {

    public static void main(String[] args) throws SocketException {
        for (NetworkInterface network : Collections.list(NetworkInterface.getNetworkInterfaces())) {
            System.out.println(network.getDisplayName());
            System.out.println(Arrays.toString(network.getHardwareAddress()));
            System.out.println(Collections.list(network.getInetAddresses()));
        }
    }

}
