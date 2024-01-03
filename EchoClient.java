import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args)throws IOException { {
        try{
            System.out.println("Client started");
            Socket soc=new Socket("localhost",8000);
            BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the string:");
            String str=input.readLine();
            PrintWriter out=new PrintWriter(soc.getOutputStream(),true);
            out.println(str);
            BufferedReader in=new BufferedReader(new InputStreamReader(soc.getInputStream()));
            System.out.println(in.readLine());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
}
