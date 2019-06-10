/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.port;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Noah
 */
public class PortList implements Serializable {

    ArrayList<Port> list;
    boolean create;

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public ArrayList<Port> getList() {
        return list;
    }

    public boolean addToList(Port savingPort){
        String name = savingPort.getPortName();
        Port port;
        try {
            boolean test = list.isEmpty();
        } catch (Exception e) {
            list = new ArrayList<>();
            list.add(savingPort);
            return true;
        }
        for (int i = 0; i < list.size(); i++) {
             port = list.get(i);
            if (port.getPortName().equalsIgnoreCase(name)) {
                return false;
            }
        }
         list.add(savingPort);
         return true;
    }
    
    public boolean addPlayerToPort(String portName, String addPlayer){
        Port port;
        for (int i = 0; i < list.size(); i++) {
             port = list.get(i);
            if (port.getPortName().equalsIgnoreCase(portName)) {
                 port.setPlayerName(port.getPlayerName() + "," + addPlayer);
                 list.remove(i);
                 list.add(port);
                return true;
            }
        }
        return false;
    }

    public Port getById(int id) throws Exception{
        Port p;
        for (int i = 0; i < list.size(); i++) {
            p = list.get(i);
            if (p.getId() == id) {
                return p;
            }
        }
        throw new Exception();
    }

    public Port getByName(String name) throws Exception{
        Port p;
        for (int i = 0; i < list.size(); i++) {
            p = list.get(i);
            if (p.getPortName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        throw new Exception();
    }

    public void removeById(int id) {
        Port p;
        for (int i = 0; i < list.size(); i++) {
            p = list.get(i);
            if (p.getId() == id) {
                list.remove(i);
            }
        }
    }

    public void removeByName(String name) {
        Port p;
        for (int i = 0; i < list.size(); i++) {
            p = list.get(i);
            if (p.getPortName().equalsIgnoreCase(name)) {
                list.remove(i);
            }
        }
    }
}
