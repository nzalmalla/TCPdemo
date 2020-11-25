package com.anjal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {
    final DataInputStream din;
    final DataOutputStream dout;
    private final String name;
    boolean isloggedin;
    Socket s;


    public ClientHandler(Socket s,
                         String name,
                         DataInputStream din,
                         DataOutputStream dout) {
        this.din = din;
        this.dout = dout;
        this.name = name;
        this.s = s;
        this.isloggedin = true;
    }

    @Override
    public void run() {
        String received;
        while (true) {
            try {
                received = din.readUTF();

                System.out.println(received);

                if (received.equals("logout")) {
                    this.isloggedin = false;
                    this.s.close();
                    break;
                }
                StringTokenizer st = new StringTokenizer(received, "#");
                if (st.countTokens() < 2) {
                    System.out.println("Not valid token : " + st);
                    break;
                }
                String msgToSend = st.nextToken();
                String recipient = st.nextToken();

                for (ClientHandler mc : ServerA.ar) {

                    if (mc.name.equals(recipient) && mc.isloggedin) {
                        mc.dout.writeUTF(this.name + " : " + msgToSend);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            this.din.close();
            this.dout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
