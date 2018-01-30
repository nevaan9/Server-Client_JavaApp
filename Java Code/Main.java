package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Client_GUI mainJpanel = new Client_GUI();
        JFrame mainFrame = new JFrame("Prime-Gen");
        mainFrame.setSize(1000, 1000);
        mainFrame.add(mainJpanel);
        mainFrame.setVisible(true);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE);
    }
}
