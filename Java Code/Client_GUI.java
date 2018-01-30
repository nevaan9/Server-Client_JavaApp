package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by nmpere15 on 5/12/2017.
 */
public class Client_GUI extends JPanel{
    protected JPanel ip_Panel;
    protected JPanel answer_Panel;
    protected JLabel ip_Jlable;
    protected JLabel bitLen_Jlable;
    protected JTextField ip_enter;
    protected JTextField bitLen_enter;
    protected JButton Gen_Prime;
    protected JButton stopButton;
    protected JTextArea Answer;
    protected Client_Connection ourClient;
    protected int bitLen;
    protected Client_GUI client_gui = this;
    protected Thread t1;
    protected JButton testButton;


    public Client_GUI(){

        this.setPreferredSize(new Dimension(500, 500));
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ip_Panel = new JPanel();
        ip_Jlable = new JLabel("Enter an ip address");
        ip_Panel.add(ip_Jlable);
        ip_Jlable.setFont(new Font("Arial", Font.PLAIN, 12));
        ip_enter = new JTextField();
        ip_Panel.add(ip_enter);
        ip_Panel.setLayout(new BoxLayout(ip_Panel, BoxLayout.Y_AXIS));
        ip_Panel.setPreferredSize(new Dimension(270, 180));

        bitLen_Jlable = new JLabel("Enter Bit-Length");
        bitLen_Jlable.setFont(new Font("Arial", Font.PLAIN, 12));
        ip_Panel.add(bitLen_Jlable);
        bitLen_enter = new JTextField();
        ip_Panel.add(bitLen_enter);

        Gen_Prime = new JButton("Generate Prime");
        Gen_Prime.addActionListener(new PushButtonListener());
        ip_Panel.add(Gen_Prime);

        //Create test button
        testButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!ourClient.connected) {
                    Answer.setText("Please wait. Still trying to connect...\nBut, the test button works!");
                }
                else {
                    Answer.setText("Please wait. Still trying to connect...\nBut, the test button works!");
                }
            }
        });
        testButton.setText("Test Button");
        ip_Panel.add(testButton);
        testButton.setEnabled(false);


        //Create a stop button
        stopButton = new JButton("Stop");
        stopButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Write code to stop thread.
                if (ourClient.connected) {
                    try {
                        ourClient.toServer.writeBoolean(true);
                        ourClient.toServer.flush();
                        Answer.setText("Stoped Generating Prime." + '\n' + "Enter another bit length or ip address");
                        stopButton.setEnabled(false);
                        testButton.setEnabled(false);
                        Gen_Prime.setEnabled(true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    t1.stop();
                    Answer.setText("");
                    stopButton.setEnabled(false);
                    testButton.setEnabled(false);
                    Gen_Prime.setEnabled(true);
                }
            }
        });
        ip_Panel.add(stopButton);
        stopButton.setEnabled(false);
        this.add(ip_Panel);


        answer_Panel = new JPanel();
        Answer = new JTextArea();
        answer_Panel.add(Answer);
        answer_Panel.setLayout(new BoxLayout(answer_Panel, BoxLayout.Y_AXIS));
        answer_Panel.setPreferredSize(new Dimension(500, 40));
        Answer.setEditable(false);
        this.add(answer_Panel);

        this.setBackground(Color.lightGray);

    }
    private class PushButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(Gen_Prime)) {
                Answer.setText("");
                //Check if inputs are valid
                if (bitLen_enter.getText().equals("")) {
                    bitLen_enter.setBackground(Color.red);
                    Answer.setText("Please enter a valid bit-length.");
                    ip_enter.setBackground(Color.WHITE);
                } else if (ip_enter.getText().equals("")) {
                    ip_enter.setBackground(Color.red);
                    Answer.setText("Please enter a valid ip address.");
                    bitLen_enter.setBackground(Color.WHITE);
                }
                //Inputs are valid!
                else {
                    bitLen_enter.setBackground(Color.WHITE);
                    ip_enter.setBackground(Color.WHITE);

                    //Check if the number entered in bitLen is a number!
                    try {
                        bitLen = Integer.parseInt(bitLen_enter.getText());

                        //Check if number entered is less than 2
                        if (bitLen < 2) {
                            bitLen_enter.setBackground(Color.red);
                            Answer.setText("Please enter a numeric value more than 1.");
                            return;
                        }
                    } catch (NumberFormatException n) {
                        bitLen_enter.setBackground(Color.red);
                        Answer.setText("Please enter numeric values only.");
                        return;
                    }

                    //If we get here, the user has entered a possibly correct ip_address and a bit-value more than 1
                    stopButton.setEnabled(true);
                    testButton.setEnabled(true);
                    Gen_Prime.setEnabled(false);
                    ourClient = new Client_Connection();
                    t1 = new Thread(new Client_Thread(client_gui, ourClient, ip_enter.getText(), bitLen));
                    t1.start();
                    Answer.setText("Please wait. Connecting...");
                }
            }
        }

    }
}
