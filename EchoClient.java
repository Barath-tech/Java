import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) {
        try{
            System.out.println("Client started");
            Socket soc=new Socket("localhost",8000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
