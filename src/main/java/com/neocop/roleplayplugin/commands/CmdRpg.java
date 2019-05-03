/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.commands;

import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.utils.Preferences;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        switch (args[0]) {
            case "start":
                RpgEngine.startRpg((Player) sender);
                break;

            case "add":
                switch (args[1]) {
                    case "player":
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
                            invPlayer.sendMessage(Preferences.playerAlreadyInRpg);
                            return;
                        }
                        invPlayer.sendMessage(Preferences.playerAddToRpg);
                        sender.sendMessage(Preferences.succPlayerAddToRpg);
                        RpgEngine.addRpgPlayer(invPlayer);
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
                        break;
                    default:
                    case "help":
                        sender.sendMessage(Preferences.helpRpgCommon);
                        break;
                }
                break;

            case "stop":
                RpgEngine.stopRpg((Player) sender, true);
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
                    System.out.println("[RPG PLUGIN ERROR]" + e);
                }
                break;

            case "join":
                Player joinPlayer = (Player) sender;
                if (RpgEngine.rpgPlayer.containsKey(joinPlayer.getDisplayName())) {
                    joinPlayer.sendMessage(Preferences.playerAlreadyInRpg);
                    return;
                }
                joinPlayer.sendMessage(Preferences.playerAddToRpg);
                RpgEngine.addRpgPlayer(joinPlayer);
                break;
                
            case "night":
                Bukkit.dispatchCommand(sender, "/time set night");
                RpgEngine.nightRpg();
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
