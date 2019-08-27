package com.neocop.neomcPlugin.netconnect.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnectorThread implements Runnable{

    public ServerConnectorThread(int portNumber){
        this.portNumber = portNumber;
    }

    boolean exit = false;

    static public int portNumber;
    ServerSocket serverSocket;
    Socket clientSocket;
    static boolean serverIsRunning = false;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            serverIsRunning = false;
            return;
        }
        serverIsRunning = true;
        System.out.println("Started new Server Connector Thread on: " + serverSocket.getInetAddress() + ":" + portNumber);
        while (true){
            clientSocket = new Socket();
            try {
                clientSocket = serverSocket.accept();
                clientSocket.setKeepAlive(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ClientMessageHandlerThread cst = new ClientMessageHandlerThread(clientSocket);
            cst.run();
        }
    }
}

