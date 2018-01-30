package com.company;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

/**
 * Created by nmpere15 on 5/12/2017.
 */
public class Server_GenPrime implements Runnable{
    handle_Client server;
    int bitLen;

    public Server_GenPrime(handle_Client server, int bitLen){
        this.server = server;
        this.bitLen = bitLen;
    }


    @Override
    public synchronized void run() {
        BigInteger bigInt = BigInteger.probablePrime(bitLen, new Random());
        server.bigInt = bigInt;
        try {
            server.output.println(bigInt);
            server.b.close();
            server.output.close();
            server.t.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notifyAll();
    }
}
