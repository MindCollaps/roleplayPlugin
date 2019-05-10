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
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class CmdRpgVote implements IntCommand {

    @Override
    public boolean calledUser(String[] args, CommandSender sender, Command command) {
        Player current = (Player) sender;
        RPGPlayer rpp = RpgEngine.rpgRolePlayer.get(current.getDisplayName());
        if (rpgUtils.hasPermission(current, rpp)) {
            if (RpgEngine.voteAllowed) {
                try {
                    if (rpgUtils.getRpgPlayerByName(current.getDisplayName()).isAlive()) {
                        if (RpgEngine.playersWhichHasVoted.contains(current.getDisplayName())) {
                            sender.sendMessage(Preferences.noPermissionAlreadyVoted);
                            return false;
                        }

                        return true;
                    } else {
                        sender.sendMessage(Preferences.noPermissionAlreadyDead);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            } else {
                sender.sendMessage(Preferences.noPermissionVoteNotActive);
                return false;
            }
        }
        return false;
    }

    @Override
    public void actionUser(String[] args, CommandSender sender, Command command) {
        Player current = (Player) sender;
        if (RpgEngine.rpgRolePlayer.containsKey(args[0])) {
            RpgEngine.playerWhichIsVoted.add(args[0]);
            RpgEngine.playersWhichHasVoted.add(current.getDisplayName());
            current.sendMessage(Preferences.playerAddToVoteList);
        } else {
            current.sendMessage(Preferences.playerNotFound);
        }
    }

    @Override
    public void actionServer(String[] args, CommandSender sender, Command command) {
        System.out.println(Preferences.noPermissionCommandOnlyForUser);
    }

}
