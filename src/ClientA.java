import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientA {
public static void main(String[] args) throws Exception {

    Socket s = new Socket("localhost",9999);
    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while (true){
        String a = br.readLine();
        dout.writeUTF(a);
          if(a.equalsIgnoreCase("exit"))
              break;
    }
s.close();
}
}
