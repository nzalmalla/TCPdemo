package com.anjal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientA {

    final static int SERVER_PORT = 9999;

    public static void main(String[] args) throws IOException {
        Scanner scn = new Scanner(System.in);

        Socket s = new Socket("localhost", SERVER_PORT);
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());


        Thread sendMessage = new Thread(() -> {
            while (true) {
                String msg = scn.nextLine();
                try {
                    dout.writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread readMessage = new Thread(() -> {
            while (true) {
                try {
                    String msg = din.readUTF();
                    System.out.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        sendMessage.start();
        readMessage.start();


    }
}