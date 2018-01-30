package com.company;

/**
 * Created by nmpere15 on 5/12/2017.
 */
public class Client_Thread implements Runnable {
    Client_Connection client;
    String ip_adress;
    int bitLen;
    Client_GUI client_gui;

    public Client_Thread(Client_GUI client_gui, Client_Connection client, String ip_adress, int bitLen){
        this.client_gui = client_gui;
        this.client = client;
        this.ip_adress = ip_adress;
        this.bitLen = bitLen;
    }


    @Override
    public void run() {
        try {
            client.connectToServer(ip_adress, bitLen);
        }
        catch (NullPointerException n){
            client.connected = false;
        }
        if (client.connected){
            client_gui.Answer.setText(client.getAnswer());
            client_gui.Gen_Prime.setEnabled(true);
            client_gui.stopButton.setEnabled(false);
            client_gui.testButton.setEnabled(false);
        }
        else {
            client_gui.Answer.setText("Could not connect to ip address " + ip_adress +".");
            client_gui.Gen_Prime.setEnabled(true);
            client_gui.stopButton.setEnabled(false);
            client_gui.testButton.setEnabled(false);
        }
    }
}
