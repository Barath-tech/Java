package Multithreading_Client_Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers= new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;

    public  ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientUserName=bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastmessage("SERVER:"+clientUserName+"has joined the chat !!");

        }catch(IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);

        }
    }

    public void run(){
        String messagefromclient;
            while(socket.isConnected()){
                try{
                    messagefromclient =bufferedReader.readLine();
                    broadcastmessage(messagefromclient);

                }catch(IOException e){
                    closeEverything(socket,bufferedReader,bufferedWriter);
                    break;
                }
            }
        
    }

    public void broadcastmessage(String messageToSend){
        for(ClientHandler clientHandler:clientHandlers){
            try{
                if(!clientHandler.clientUserName.equals(clientUserName)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch(IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastmessage("SERVER:"+clientUserName+" has left the chat !!");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        removeClientHandler();
        try{
            if(socket!=null){
                socket.close();
            }
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
