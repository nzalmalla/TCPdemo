package com.anjal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {
    private final Server server;
    private final DataInputStream din;
    private final DataOutputStream dout;
    private final String name;
    private boolean isloggedin;
    private Socket s;


    public ClientHandler(Server server,
                         Socket s,
                         String name,
                         DataInputStream din,
                         DataOutputStream dout) {
        this.server = server;
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
                } else {
                    sendToClient(received);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        close();
    }

    private void sendToClient(String received) throws IOException {
        StringTokenizer st = new StringTokenizer(received, "#");
        if (st.countTokens() == 2) {
            String msgToSend = st.nextToken();
            String recipient = st.nextToken();

            for (ClientHandler mc : server.getAr()) {
                if (mc.name.equals(recipient) && mc.isloggedin) {
                    mc.dout.writeUTF(this.name + " : " + msgToSend);
                    break;
                }
            }
        }
    }

    private void close() {
        try {
            this.din.close();
            this.dout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
