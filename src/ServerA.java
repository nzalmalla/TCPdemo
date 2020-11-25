import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class ServerA {
    static Vector<ClientHandler> ar = new Vector<>();


    static int i = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9999);
        Socket s;
        while (true) {
            s = ss.accept();

            System.out.println("Accepting new client: " + s);

            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            System.out.println("Created new client handler");

            ClientHandler mtch = new ClientHandler(s, "client " + i, din, dout);

            Thread t = new Thread(mtch);

            System.out.println("Added to the Client list");

            ar.add(mtch);

            t.start();
            i++;

        }
    }
}


class ClientHandler implements Runnable {
    Scanner scn = new Scanner(System.in);
    private String name;
    final DataInputStream din;
    final DataOutputStream dout;
    Socket s;
    boolean isloggedin;


    public ClientHandler(Socket s, String name,
                         DataInputStream din, DataOutputStream dout) {
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
                String MsgToSend = st.nextToken();
                String recipient = st.nextToken();

                for (ClientHandler mc : ServerA.ar) {

                    if (mc.name.equals(recipient) && mc.isloggedin == true) {
                        mc.dout.writeUTF(this.name + " : " + MsgToSend);
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