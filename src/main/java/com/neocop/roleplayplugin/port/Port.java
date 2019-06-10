/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.port;

import java.io.Serializable;
import org.bukkit.Location;

/**
 *
 * @author Noah
 */
public class Port implements Serializable{
    
    int id;
    String portName;
    String playerName;
    boolean privat;
    PortLocation loc;
    
    public Port(int id, String portName, String playerName, boolean privat, PortLocation loc){
        this.id = id;
        this.portName = portName;
        this.playerName = playerName;
        this.privat = privat;
        this.loc = loc;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isPrivat() {
        return privat;
    }

    public void setPrivat(boolean privat) {
        this.privat = privat;
    }
    
    public PortLocation getLoc() {
        return loc;
    }

    public void setLoc(PortLocation loc) {
        this.loc = loc;
    }
}
