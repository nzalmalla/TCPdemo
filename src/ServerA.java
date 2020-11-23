import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerA {
    public static void main(String[] args)throws Exception{
        ServerSocket s = new ServerSocket(9999);
        Socket ss = s.accept();
        System.out.println("connected");
        DataInputStream dout = new DataInputStream((ss.getInputStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            String a= dout.readUTF();
            System.out.println("client:- " +a);
             if(a.equalsIgnoreCase("exit"))
                 break;
        }
        ss.close();
    }}
