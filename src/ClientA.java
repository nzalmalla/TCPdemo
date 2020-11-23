import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientA {
    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 9999);

        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);

        Scanner input = new Scanner(System.in);

        String line;

        while (!(line = input.nextLine()).equalsIgnoreCase("exit")) {
            writer.println(line);
            String response = reader.readLine();
            System.out.println(response);
        }

        s.close();
    }
}
