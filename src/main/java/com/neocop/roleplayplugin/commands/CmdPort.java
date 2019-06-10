/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.commands;

import com.neocop.roleplayplugin.port.Port;
import com.neocop.roleplayplugin.port.PortList;
import com.neocop.roleplayplugin.port.PortLocation;
import com.neocop.roleplayplugin.utils.Preferences;
import com.neocop.roleplayplugin.utils.fileUtils;
import com.neocop.roleplayplugin.utils.pluginUtils;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
                    port = new Port(0, args[2], player.getDisplayName(), true, new PortLocation(player.getLocation()));
                    if (pList.addToList(port)) {
                        player.sendMessage(Preferences.portCreated);
                    } else {
                        player.sendMessage(Preferences.portExistAlready);
                    }
                } else {
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
                if (args.length > 1) {
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

            case "list":
                if (args.length > 1) {
                    if (args[1].equalsIgnoreCase("private")) {
                        Port po;
                        String s = "List of private Ports:";
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
                    String s = "List of global Ports:";
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
                if (args.length > 1) {
                    if (args[2].equalsIgnoreCase(Preferences.portAdminKey)) {
                        player.teleport(getLocationFromPort(port));
                        player.sendMessage("§aUsed Admin Key to remove this Port!");
                        return;
                    }
                }
                if (playerHasPortPermission(port, player.getDisplayName())) {
                    player.teleport(getLocationFromPort(port));
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
