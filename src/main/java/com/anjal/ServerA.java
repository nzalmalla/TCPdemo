package com.anjal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerA {
    static List<ClientHandler> ar = new ArrayList<>();
    static int i = 0;

    public ServerA {
        try (ServerSocket ss = new ServerSocket(9999)) {
            System.out.println("Server started at port " + 9999);
            Socket s;
            while (true) {
                s = ss.accept();
                System.out.println("Accepting new client: " + s);
                DataInputStream din = new DataInputStream(s.getInputStream());
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                System.out.println("Created new client handler");
                ClientHandler mtch = new ClientHandler(s, "client" + i, din, dout);
            }
        }

        public static void main (String[]args) throws IOException {
            ServerA newServer = new ServerA();
            Thread t = new Thread(mtch);
            System.out.println("Added to the Client list");
            ar.add(mtch);
            t.start();
            i++;

        }
    }
}