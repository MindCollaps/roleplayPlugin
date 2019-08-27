package com.neocop.neomcPlugin.netconnect.ClientThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnectThread implements Runnable{

    private String hostName;
    private int portNumber;
    static InetAddress inetAddress;
    static PrintWriter out = null;
    static BufferedReader in = null;
    static Socket serverSocket = null;
    public static boolean hasConnection;
    
    private String lastMessage;

    public ClientConnectThread(String hostName, int portNumber){
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        hasConnection = false;
        System.out.println("Server: " + hostName + ":" + portNumber);
        while (connectToServer()){}
        hasConnection = false;
        System.out.println("Client Connector stopped!");
    }

    public boolean connectToServer(){
        hasConnection = false;
        while (true){
            try {
                System.out.println("Connect to Server...");
                serverSocket = new Socket(hostName, portNumber);
            } catch (Exception e){
                System.out.println("Cant connect to Server! Try again in a few moments!");
                try {
                    Thread.sleep(10*3);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return true;
            }
            this.inetAddress = serverSocket.getInetAddress();
            hasConnection = true;
            System.out.println("Connected!");


            try {
                out = new PrintWriter(serverSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            } catch (Exception e){
                e.printStackTrace();
                return true;
            }

            String fromServer;
            try {
                while ((fromServer = in.readLine()) != null) {
                    if(serverSocket.isClosed()){
                        System.out.println(" lost connection! " + inetAddress);
                    }
                    System.out.println("Server: " + fromServer);
                    if (fromServer.equals("0X00")){
                        return false;
                    } else if(fromServer.equals("0X10")) {
                        System.out.println("Problem with request: " + lastMessage);
                    } else if(fromServer.equals("0X11")) {
                        System.out.println("requst creates error on server: " + lastMessage);
                    } else {
                        //TODO: Do Stuff here
                    }
                }
            } catch (Exception e){
                System.out.println("Lost connection to: " + inetAddress);
                return true;
            }
            return true;
        }
    }

    public void sendMessageToServer(String message){
        if(hasConnection){
            lastMessage = message;
            out.println(message);
        } else {
            System.out.println("Cant send Message to Server, because there is no connection!");
        }
    }
}