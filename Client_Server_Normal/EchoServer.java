package Client_Server_Normal;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer{
    public static void main(String[] args) {
        try{
            System.out.println("Waiting for Clients..");
            ServerSocket ss=new ServerSocket(8000);
            Socket soc=ss.accept();
            System.out.println("Connection established");
            BufferedReader in=new BufferedReader(new InputStreamReader(soc.getInputStream()));
            String str=in.readLine();
            PrintWriter out=new PrintWriter(soc.getOutputStream(),true);
            out.println("Server says: " + str);
        }catch(Exception e){
            System.out.println("Error while connecting to Clients");
            e.printStackTrace();
        }

    }
}