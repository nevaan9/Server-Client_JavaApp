package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by nmpere15 on 5/12/2017.
 */
public class handle_Client implements Runnable {
    protected ServerSocket s;
    protected BigInteger bigInt;
    protected ObjectInputStream b;
    protected Socket t;
    protected PrintWriter output;

    public handle_Client(ServerSocket s){this.s = s;}


    @Override
    public synchronized void run() {
        System.out.println("Server started");
        while (true) {;// wait for client to connect
            try {
                t = s.accept();
                System.out.println("server connected");
                b = new ObjectInputStream(t.getInputStream());
                int received = b.readInt();
                output = new PrintWriter(t.getOutputStream(), true);

                //bigInt = BigInteger.probablePrime(received, new Random());
                Thread t1 = new Thread(new Server_GenPrime(this, received));

                System.out.println("Thread started");
                t1.start();

                try {
                    boolean x = b.readBoolean();
                    t1.stop();
                    System.out.println("Thread stoped as client requested to do so" + '\n');
                }
                catch (SocketException e){
                    System.out.print("Prime sent to client: " + t.getInetAddress());
                }

                notifyAll();

            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
