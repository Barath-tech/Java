package Multithreading_Client_Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startserver(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket=serverSocket.accept();
                System.out.println("A new client has joined");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread=new Thread(clientHandler);
                thread.start();
            }

        }catch(IOException e){

        }
    }

    public void closeServerSocket(){
        try{
            if(serverSocket != null){
                serverSocket.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args)throws IOException {
        ServerSocket serverSocket=new ServerSocket(2000);
        Server server=new Server(serverSocket);
        server.startserver();
    }
}
