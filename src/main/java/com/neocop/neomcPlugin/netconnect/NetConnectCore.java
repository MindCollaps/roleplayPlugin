package com.neocop.neomcPlugin.netconnect;

import com.neocop.neomcPlugin.netconnect.ClientThread.ClientConnectThread;
import com.neocop.neomcPlugin.netconnect.ServerThread.ServerConnectorThread;


public class NetConnectCore {

    public static int portNumber = -1;
    public static String hostName;

    public static void startServer(){
        if(portNumber == -1){
            System.out.println("Port Number was never defined!");
            return;
        }
        ServerConnectorThread sct = new ServerConnectorThread(portNumber);
        sct.run();
    }

    public static void startServer(int portNumber){
        ServerConnectorThread sct = new ServerConnectorThread(portNumber);
        sct.run();
    }

    public static ClientConnectThread startClientConnection(){
        if(portNumber == -1||hostName == null){
            System.out.println("Ether port number or host name was never defined!");
            return null;
        }
        ClientConnectThread cct = new ClientConnectThread(hostName, portNumber);
        cct.run();
        return cct;
    }

    public static ClientConnectThread startClientConnection(String hostName, int portNumber){
        ClientConnectThread cct = new ClientConnectThread(hostName, portNumber);
        cct.run();
        return cct;
    }

    public static int getPortNumber() {
        return portNumber;
    }

    public static void setPortNumber(int portNumber) {
        NetConnectCore.portNumber = portNumber;
    }

    public static String getHostName() {
        return hostName;
    }

    public static void setHostName(String hostName) {
        NetConnectCore.hostName = hostName;
    }
}