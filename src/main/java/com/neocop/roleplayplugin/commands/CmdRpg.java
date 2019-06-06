/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.commands;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.roleplayCore.rpgUtils;
import com.neocop.roleplayplugin.utils.Preferences;
import com.neocop.roleplayplugin.utils.pluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Noa
 */
public class CmdRpg implements IntCommand {

    @Override
    public boolean calledUser(String[] args, CommandSender sender, Command command) {
        return true;
    }

    @Override
    public void actionUser(String[] args, CommandSender sender, Command command) {
        Player current = (Player) sender;
        RPGPlayer rpp = RpgEngine.rpgRolePlayer.get(current.getDisplayName());
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

                    case "all":
                        if (!RpgEngine.rpgRunning) {
                            Player invPlayer = null;
                            RpgEngine.rpgPlayer.clear();
                            Object[] players = RpgEngine.onlinePlayer.values().toArray();
                            for (int i = 0; i < players.length; i++) {
                                invPlayer = (Player) players[i];
                                RpgEngine.rpgPlayer.put(invPlayer.getDisplayName(), invPlayer);
                                invPlayer.sendMessage(Preferences.playerAddToRpg);
                            }
                            sender.sendMessage(Preferences.succPlayerAddToRpg);
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

            case "preferences":
                switch (args[1]) {
                    case "days":
                        if (Integer.valueOf(args[2]) >= 30) {
                            Preferences.daysDuration = Integer.valueOf(args[2]);
                            sender.sendMessage("§aZeit auf " + args[2] + " gesetzt!");
                        } else {
                            sender.sendMessage("§cEs müssen mindestens 30 Sekunden angegeben werden!");
                        }

                        break;

                    case "nights":
                        if (Integer.valueOf(args[2]) >= 30) {
                            Preferences.voteDuration = Integer.valueOf(args[2]);
                            sender.sendMessage("§aZeit auf " + args[2] + " gesetzt!");
                        } else {
                            sender.sendMessage("§cEs müssen mindestens 30 Sekunden angegeben werden!");
                        }

                        break;

                    case "votes":
                        if (Integer.valueOf(args[2]) >= 30) {
                            Preferences.nightsDuration = Integer.valueOf(args[2]);
                            sender.sendMessage("§aZeit auf " + args[2] + " gesetzt!");
                        } else {
                            sender.sendMessage("§cEs müssen mindestens 30 Sekunden angegeben werden!");
                        }
                        break;
                    case "detectivsucc":
                        try {
                            Preferences.detectivSucc = Integer.valueOf(args[2]);
                            current.sendMessage("§aDetectiv erfolgschance auf " + args[2] + " gesetzt!");
                        } catch (Exception e) {
                            current.sendMessage("§cBitte gebe eine gültige Zahl an!");
                        }
                        break;

                    case "help":
                    default:
                        sender.sendMessage(Preferences.helpRpgPreferences);
                        break;
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

            case "extra":
                String[] argi = new String[args.length - 1];
                int b = 0;
                for (int i = 1; i < args.length; i++) {
                    argi[b] = args[i];
                    b++;
                }
                rpp.getRole().getRole().extra(rpp, argi);
                break;

            case "vote":
                if (rpgUtils.hasPermission(current, rpp)) {
                    if (RpgEngine.voteAllowed) {
                        try {
                            if (rpgUtils.getRpgPlayerByName(current.getDisplayName()).isAlive()) {
                                if (RpgEngine.playersWhichHasVoted.contains(current.getDisplayName())) {
                                    sender.sendMessage(Preferences.noPermissionAlreadyVoted);
                                    break;
                                }
                                if (RpgEngine.rpgRolePlayer.containsKey(args[1])) {
                                    RpgEngine.playerWhichIsVoted.add(args[1]);
                                    RpgEngine.playersWhichHasVoted.add(current.getDisplayName());
                                    current.sendMessage(Preferences.playerAddToVoteList);
                                } else {
                                    current.sendMessage(Preferences.playerNotFound);
                                }
                            } else {
                                sender.sendMessage(Preferences.noPermissionAlreadyDead);
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    } else {
                        sender.sendMessage(Preferences.noPermissionVoteNotActive);
                        break;
                    }
                }
                break;

            case "dev":
                pluginUtils.spawnRandomFireworkAroundPlayer(current);
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
