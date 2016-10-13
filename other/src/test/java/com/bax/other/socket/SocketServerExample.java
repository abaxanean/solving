/*
 * Copyright 2016 MobileIron, Inc.
 * All rights reserved.
 */

package com.bax.other.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO: Document this class.
 */
public class SocketServerExample {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;

    static ExecutorService service = Executors.newFixedThreadPool(5);

    static boolean continu = true;

    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {
        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        while (continu) {
            System.out.println("Waiting for client request");
            //creating socket and waiting for client connection
            Socket socket = server.accept();
            service.submit((Runnable)() -> {
                try {
                    System.out.println("LocalAddress: " + socket.getLocalAddress().getHostAddress() + " LocalPort: " + socket.getLocalPort());
                    System.out.println("InetHostAddress: " + socket.getInetAddress().getHostAddress() + " Port: " + socket.getPort());
                    InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
                    System.out.println("RemoteSocketAddress: " + socketAddress.getHostString() + " Port: " + socketAddress.getPort());
                    Thread.sleep(1000);

                    //read from socket to ObjectInputStream object
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    //convert ObjectInputStream object to String
                    String message = (String)ois.readObject();
//                    System.out.println(LocalTime.now() + ": Message Received: " + message);
                    //create ObjectOutputStream object
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    //write object to Socket
                    oos.writeObject("Hi Client " + message);
                    //close resources
                    ois.close();
                    oos.close();
                    socket.close();
                    //terminate the server if client sends exit request
                    if (message.equalsIgnoreCase("exit")) {
                        continu = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
//        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }

}
