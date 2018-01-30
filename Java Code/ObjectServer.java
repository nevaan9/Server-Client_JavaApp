package com.company;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by nmpere15 on 5/12/2017.
 */
public class ObjectServer {
    public void connect() {
        try {
            ServerSocket s = new ServerSocket(12345);
            Thread t1 = new Thread(new handle_Client(s));
            t1.start();
        }
        catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Connection failed. Try again");
        }
    }
}
