/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.commands;

import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.utils.Preferences;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class CmdRpg implements IntCommand {

    @Override
    public boolean calledUser(String[] args, CommandSender sender, Command command) {
        return true;
    }

    @Override
    public void actionUser(String[] args, CommandSender sender, Command command) {
        Player current = (Player) sender;
        switch (args[0]) {
            case "start":
                RpgEngine.startRpg((Player) sender);
                break;

            case "add":
                switch (args[1]) {
                    case "player":
                        if (!RpgEngine.rpgRunning) {
                            Player invPlayer = null;
                            if (!RpgEngine.onlinePlayer.containsKey(args[2])) {
                                sender.sendMessage(Preferences.playerNotFound);
                                return;
                            }
                            try {
                                invPlayer = Bukkit.getPlayer(args[2]);
                            } catch (Exception e) {
                                return;
                            }
                            if (RpgEngine.rpgPlayer.containsKey(invPlayer.getDisplayName())) {
                                sender.sendMessage(Preferences.playerAlreadyInRpg);
                                return;
                            }
                            invPlayer.sendMessage(Preferences.playerAddToRpg);
                            sender.sendMessage(Preferences.succPlayerAddToRpg);
                            RpgEngine.addRpgPlayer(invPlayer);
                        }
                        break;

                    default:
                    case "help":
                        sender.sendMessage(Preferences.helpRpgCommon);
                        break;
                }
                break;

            case "remove":
                switch (args[1]) {
                    case "player":
                        if (!RpgEngine.rpgRunning) {
                            Player dellPlayer;
                            try {
                                dellPlayer = Bukkit.getPlayer(args[2]);
                            } catch (Exception e) {
                                sender.sendMessage(Preferences.playerNotFound);
                                return;
                            }
                            if (!RpgEngine.rpgPlayer.containsKey(dellPlayer.getDisplayName())) {
                                dellPlayer.sendMessage(Preferences.playerIsNotInRpg);
                                return;
                            }
                            dellPlayer.sendMessage(Preferences.playerRemoveFromRpg);
                            sender.sendMessage(Preferences.succPlayerRemoveFromRpg);
                            RpgEngine.deleteRpgPlayer(dellPlayer);
                        }
                        break;
                    default:
                    case "help":
                        sender.sendMessage(Preferences.helpRpgCommon);
                        break;
                }
                break;

            case "stop":
                RpgEngine.stopRpg(current, true);
                break;

            case "list":
                try {
                    if (RpgEngine.rpgPlayer.isEmpty()) {
                        sender.sendMessage(Preferences.playerRpgListEmpty);
                    } else {
                        Object[] players = RpgEngine.rpgPlayer.values().toArray();
                        Player player = null;
                        String answer = "";
                        for (int i = 0; players.length > i; i++) {
                            player = (Player) players[i];
                            answer = answer + player.getDisplayName() + ", ";
                        }
                        sender.sendMessage(answer);
                    }
                } catch (Exception e) {
                    System.out.println(Preferences.consoleDes + " [ERROR]" + e);
                }
                break;

            case "join":
                if (!RpgEngine.rpgRunning) {
                    if (RpgEngine.rpgPlayer.containsKey(current.getDisplayName())) {
                        current.sendMessage(Preferences.playerAlreadyInRpg);
                        return;
                    }
                    current.sendMessage(Preferences.playerAddToRpg);
                    RpgEngine.addRpgPlayer(current);
                }
                break;

            case "info":
                sender.sendMessage(Preferences.infoAboutPlugin);
                break;

            default:
            case "help":
                sender.sendMessage(Preferences.helpRpgCommon);
                break;
        }
    }

    @Override
    public void actionServer(String[] args, CommandSender sender, Command command) {
        System.out.println(Preferences.noPermissionCommandOnlyForUser);
    }

}
