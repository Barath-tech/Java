import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer{
    public static void main(String[] args) {
        try{
            System.out.println("Waiting for Clients..");
            ServerSocket ss=new ServerSocket(8000);
            Socket soc=ss.accept();
            System.out.println("Connection established");
        }catch(Exception e){
            System.out.println("Error while connecting to Clients");
            e.printStackTrace();
        }

    }
}