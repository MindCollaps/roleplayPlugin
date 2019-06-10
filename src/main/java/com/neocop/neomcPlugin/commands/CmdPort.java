/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.neomcPlugin.commands;

import com.neocop.neomcPlugin.port.Port;
import com.neocop.neomcPlugin.port.PortList;
import com.neocop.neomcPlugin.port.PortLocation;
import com.neocop.neomcPlugin.utils.Preferences;
import com.neocop.neomcPlugin.utils.fileUtils;
import com.neocop.neomcPlugin.utils.pluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class CmdPort implements IntCommand {

    public static PortList pList;

    @Override
    public boolean calledUser(String[] args, CommandSender sender, Command command) {
        return true;
    }

    @Override
    public void actionUser(String[] args, CommandSender sender, Command command) {
        Player player = (Player) sender;
        Port port;
        switch (args[0]) {
            case "create":
                if (args[1].equalsIgnoreCase("private")) {
                    if(args[1].length() >20){
                        player.sendMessage(Preferences.portToMuchChars);
                        return;
                    }
                    port = new Port(0, args[2], player.getDisplayName(), true, new PortLocation(player.getLocation()));
                    if (pList.addToList(port)) {
                        player.sendMessage(Preferences.portCreated);
                    } else {
                        player.sendMessage(Preferences.portExistAlready);
                    }
                } else {
                    if(args[1].length() >20){
                        player.sendMessage(Preferences.portToMuchChars);
                        return;
                    }
                    port = new Port(0, args[1], player.getDisplayName(), false, new PortLocation(player.getLocation()));
                    if (pList.addToList(port)) {
                        player.sendMessage(Preferences.portCreated);
                    } else {
                        player.sendMessage(Preferences.portExistAlready);
                    }
                }
                break;

            case "remove":
                try {
                    port = pList.getByName(args[1]);
                } catch (Exception ex) {
                    player.sendMessage(Preferences.portDoesntExist);
                    return;
                }
                if (args.length > 2) {
                    if (args[2].equalsIgnoreCase(Preferences.portAdminKey)) {
                        pList.removeByName(port.getPortName());
                        player.sendMessage("§aUsed Admin Key to remove this Port!");
                        return;
                    }
                }
                String[] portPlayer = port.getPlayerName().split(",");
                if (playerHasPortPermission(port, player.getDisplayName())) {
                    pList.removeByName(port.getPortName());
                } else {
                    player.sendMessage(Preferences.portIsntYours);
                    break;
                }
                player.sendMessage(Preferences.portDeleted);
                break;
                
            case "info":
                try {
                    port = pList.getByName(args[1]);
                } catch (Exception ex) {
                    player.sendMessage(Preferences.portDoesntExist);
                    return;
                }
                if(port.getDescription() == ""||port.getDescription() == null){
                    player.sendMessage("§cDer Port hat noch keine info!");
                    return;
                }
                player.sendMessage(port.getDescription());
                break;

            case "list":
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("private")) {
                        if (args.length > 2) {
                            if (args[2].equalsIgnoreCase(Preferences.portAdminKey)) {
                                Port po;
                                String s = "§eList of private Ports:§4";
                                for (int i = 0; i < pList.getList().size(); i++) {
                                    po = pList.getList().get(i);
                                    if (po.isPrivat()) {
                                        s = s + "\n-" + po.getPortName() + " (" + po.getPlayerName() + ")";
                                    }
                                }
                                player.sendMessage("§aUsed Admin Key to show this list!");
                                player.sendMessage(s);
                                return;
                            }

                        }
                        Port po;
                        String s = "§eList of private Ports:§6";
                        for (int i = 0; i < pList.getList().size(); i++) {
                            po = pList.getList().get(i);
                            if (po.isPrivat()) {
                                if (po.getPlayerName().equalsIgnoreCase(player.getDisplayName())) {
                                    s = s + "\n-" + po.getPortName();
                                }
                            }
                        }
                        player.sendMessage(s);
                    }
                } else {
                    Port po;
                    String s = "§eList of global Ports:§3";
                    for (int i = 0; i < pList.getList().size(); i++) {
                        po = pList.getList().get(i);
                        if (!po.isPrivat()) {
                            s = s + "\n-" + po.getPortName();
                        }
                    }
                    player.sendMessage(s);
                }
                break;

            case "edit": {
                try {
                    port = pList.getByName(args[1]);
                } catch (Exception ex) {
                    player.sendMessage(Preferences.portDoesntExist);
                    return;
                }
                if (playerHasPortPermission(port, player.getDisplayName())) {
                    switch (args[2]) {
                        case "share":
                            if (pList.addPlayerToPort(port.getPortName(), args[3])) {
                                player.sendMessage(Preferences.portEdited);
                            } else {
                                player.sendMessage(Preferences.portDoesntExist);
                            }
                            break;

                        case "welcomeMsg":
                            String msg = "";
                            if (args.length >= 3) {
                                for (int i = 3; i < args.length; i++) {
                                    if (args[i].startsWith("format.")) {
                                        msg = msg + pluginUtils.getMinecraftColorCode(args[i].substring(7));
                                    } else {
                                        if (i == 3) {
                                            msg = args[3];
                                        } else {
                                            msg = msg + " " + args[i];
                                        }
                                    }
                                }
                            }
                            port.setMessage(msg);
                            pList.removeByName(port.getPortName());
                            pList.addToList(port);
                            player.sendMessage(Preferences.portMessageSucces);
                            break;
                            
                        case "description":
                            String des = "";
                            if (args.length >= 3) {
                                for (int i = 3; i < args.length; i++) {
                                    if (args[i].startsWith("format.")) {
                                        des = des + pluginUtils.getMinecraftColorCode(args[i].substring(7));
                                    } else {
                                        if (i == 3) {
                                            des = args[3];
                                        } else {
                                            des = des + " " + args[i];
                                        }
                                    }
                                }
                            }
                            port.setDescription(des);
                            pList.removeByName(port.getPortName());
                            pList.addToList(port);
                            player.sendMessage(Preferences.portDescriptionSucces);
                            break;
                    }
                } else {
                    player.sendMessage(Preferences.portIsntYours);
                }
            }
            break;

            case "private":
                try {
                    port = pList.getByName(args[1]);
                } catch (Exception ex) {
                    player.sendMessage(Preferences.portDoesntExist);
                    return;
                }
                if (args.length > 2) {
                    if (args[2].equalsIgnoreCase(Preferences.portAdminKey)) {
                        player.teleport(getLocationFromPort(port));
                        player.sendMessage("§aUsed Admin Key to remove this Port!");
                        if (port.getMessage() != "" || port.getMessage() != null) {
                            player.sendMessage(port.getMessage());
                        }
                        return;
                    }
                }
                if (playerHasPortPermission(port, player.getDisplayName())) {
                    player.teleport(getLocationFromPort(port));
                    if (port.getMessage() != "" || port.getMessage() != null) {
                        player.sendMessage(port.getMessage());
                    }
                } else {
                    player.sendMessage(Preferences.portIsntYours);
                    return;
                }
                break;

            default:
                if (args.length >= 0) {
                    try {
                        port = pList.getByName(args[0]);
                    } catch (Exception ex) {
                        player.sendMessage(Preferences.portDoesntExist);
                        return;
                    }
                    if (port.isPrivat()) {
                        player.sendMessage(Preferences.portIsPrivate);
                        return;
                    } else {
                        player.teleport(getLocationFromPort(port));
                        if (port.getMessage() != "" || port.getMessage() != null) {
                            player.sendMessage(port.getMessage());
                        }
                    }
                } else {
                    player.sendMessage(Preferences.helpPortCommon);
                }
                break;

            case "help":
                player.sendMessage(Preferences.helpPortCommon);
                break;
        }
    }

    @Override
    public void actionServer(String[] args, CommandSender sender, Command command) {
        if (args[0].equalsIgnoreCase("save")) {
            savePorts();
        } else if (args[0].equalsIgnoreCase("load")) {
            loadPorts();
        } else if (args[0].equalsIgnoreCase("admin")) {
            pluginUtils.generatePortAdminKey();
        }
    }

    public static void loadPorts() {
        PortList list = (PortList) fileUtils.loadPorts(Preferences.filePath + "/" + "ports" + "/" + "portlist.ports");
        boolean create;
        try {
            create = list.isCreate();
        } catch (Exception e) {
            pList = new PortList();
            pList.setCreate(true);
            savePorts();
            return;
        }
        pList = list;
    }

    public static void savePorts() {
        fileUtils.savePorts(Preferences.filePath + "/" + "ports" + "/" + "portlist.ports", CmdPort.pList);
    }

    private static boolean playerHasPortPermission(Port port, String playerName) {
        String[] portPlayer = port.getPlayerName().split(",");
        for (int i = 0; i < portPlayer.length; i++) {
            if (portPlayer[i].equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }

    private static Location getLocationFromPort(Port port) {
        Location loc = new Location(Bukkit.getWorld(port.getLoc().getWorld()), port.getLoc().getX(), port.getLoc().getY(), port.getLoc().getZ());
        return loc;
    }
}
