/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.other.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO: Document this class.
 */
public class SocketClientExample {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        ExecutorService service = Executors.newFixedThreadPool(5);
        AtomicInteger integer = new AtomicInteger();
        for (int i=0; i<1;i++) {
            service.submit((Runnable)() -> {
                try {
                    //establish socket connection to server
                    Socket socket = null;

                    socket = new Socket(host.getHostName(), 9876);
                    System.out.println("LocalAddress: " + socket.getLocalAddress().getHostAddress() + " LocalPort: " + socket.getLocalPort());
                    System.out.println("InetHostAddress: " + socket.getInetAddress().getHostAddress() + " Port: " + socket.getPort());
                    InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
                    System.out.println("RemoteSocketAddress: " + socketAddress.getHostString() + " Port: " + socketAddress.getPort());

                    //write to socket using ObjectOutputStream
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//                    System.out.println(LocalTime.now() + ": Sending request to Socket Server");
                    if (integer.incrementAndGet() == 4) {
                        oos.writeObject("exit");
                    } else {
                        oos.writeObject("" + integer.get());
                    }
                    //read the server response message
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    String message = (String)ois.readObject();
//                    System.out.println(LocalTime.now() + ": Message: " + message);
                    //close resources
                    ois.close();
                    oos.close();
//                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }

}
