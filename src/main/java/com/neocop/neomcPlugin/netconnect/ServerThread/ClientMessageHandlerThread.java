package com.neocop.neomcPlugin.netconnect.ServerThread;

import com.neocop.neomcPlugin.netconnect.interactModul;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientMessageHandlerThread implements Runnable{

    boolean exit = false;

    private Socket clientSocket;
    private InetAddress clientAdress;
    private long id;
    public int clientsConnectedToServer = 0;

    public ClientMessageHandlerThread(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        this.id = Thread.currentThread().getId();
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e){
            e.printStackTrace();
        }
        this.clientAdress = clientSocket.getInetAddress();
        clientsConnectedToServer ++;
        System.out.println(" Successfully connected to client: [" + id + ", " + clientSocket.getInetAddress() + "]");
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if(exit){
                    System.out.println(" Ended connection to: [" + id + ", " + clientSocket.getInetAddress() + "]");
                    return;
                }
                if(clientSocket.isClosed()){
                    System.out.println(" Lost connection to: [" + id + ", " + clientSocket.getInetAddress() + "]");
                    return;
                }
                System.out.println("[" + id + ", " + clientAdress + "] Client: " + inputLine);
                if (inputLine.equals("0X00")){
                    out.println("0X00");
                    break;
                } else {
                    //TODO: Do stuff here
                    out.println(interactModul.validate(inputLine.split(" ")));
                }
            }
        } catch (IOException e) {
            System.out.println(" Lost connection to: [" + id + ", " + clientSocket.getInetAddress() + "]");
            clientsConnectedToServer --;
            return;
        }
        System.out.println(" Ended connection to: [" + id + ", " + clientSocket.getInetAddress() + "]");
        clientsConnectedToServer --;
    }
}