import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerA {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);

        Socket s = serverSocket.accept();
        System.out.println("connected");

        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);

        Scanner input = new Scanner(System.in);

        String line;
        while (!(line = reader.readLine()).equalsIgnoreCase("exit")) {
            System.out.println("client:- " + line);
            String nextLine = input.nextLine();
            writer.println("Server Response :- " + nextLine);
        }

        System.out.println("Exiting");

        s.close();
    }
}
