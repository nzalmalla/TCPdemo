package com.anjal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int port;
    private List<ClientHandler> ar = new ArrayList<>();

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        Server server = new Server(9999);
        server.start();
    }

    public List<ClientHandler> getAr() {
        return ar;
    }

    public void start() {
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Server started at port " + port);
            Socket s;
            while (true) {
                s = ss.accept();
                System.out.println("Accepting new client: " + s);
                DataInputStream din = new DataInputStream(s.getInputStream());
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                System.out.println("Created new client handler");
                ClientHandler mtch = new ClientHandler(this, s, "client" + ar.size() + 1, din, dout);
                Thread thread = new Thread(mtch);
                System.out.println("Added to the Client list");
                ar.add(mtch);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}