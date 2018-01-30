package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by nmpere15 on 5/12/2017.
 */
public class Client_Connection {
    Socket requestSocket;
    boolean connected = false;
    ObjectOutputStream toServer;
    BufferedReader response;
    String answer = "";
    boolean primeGenerated;

    public Client_Connection(){}

    public synchronized void connectToServer(String ip_address, int bitLen){
        try {
            requestSocket = new Socket(ip_address, 12345);
            connected = true;
            try {
                toServer = new ObjectOutputStream(requestSocket.getOutputStream());

                toServer.writeInt(bitLen);
                toServer.flush();

                //get the details from the server
                response = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
                this.setAnswer(response.readLine());
                primeGenerated = true;

                toServer.close();
                response.close();
                requestSocket.close();

            } catch (IOException e) {
                primeGenerated = false;
                //e.printStackTrace();
            }
        } catch (IOException e) {
            //e.printStackTrace();
            connected = false;
        }
        notifyAll();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
