/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.utils;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class pluginUtils {

    public static boolean isUserCommand(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        } else {
            return false;
        }
    }

    public static void sendMessageToAllAliveRpgPlayer(String txt) {
        Object[] players = RpgEngine.rpgPlayer.values().toArray();
        Player p = null;
        for (int i = 0; i < players.length; i++) {
            p = (Player) players[i];
            p.sendMessage(txt);
        }
    }

    public static void sendMessageToAllRpgPlayer(String txt) {
        Player p = null;
        for (int i = 0; i < RpgEngine.allRpgPlayer.size(); i++) {
            p = RpgEngine.allRpgPlayer.get(i);
            p.sendMessage(txt);
        }
    }

    public static void sendTitleToAllRpgPlayer(String txt, String txt2) {
        Player p = null;
        for (int i = 0; i < RpgEngine.allRpgPlayer.size(); i++) {
            p = RpgEngine.allRpgPlayer.get(i);
            p.sendTitle(txt, txt2);
        }
    }

    public static void sendTitleToAllGoodRpgPlayer(String txt, String txt2) {
        Player p = null;
        RPGPlayer rpp = null;
        Object[] players = RpgEngine.villagerTeam.toArray();
        for (int i = 0; i < RpgEngine.villagerTeam.size(); i++) {
            rpp = (RPGPlayer) players[i];
            p = rpp.getPlayer();
            p.sendTitle(txt, txt2);
        }
    }
    
    public static void sendTitleToAllBadRpgPlayer(String txt, String txt2) {
        Player p = null;
        RPGPlayer rpp = null;
        Object[] players = RpgEngine.killerTeam.toArray();
        for (int i = 0; i < RpgEngine.killerTeam.size(); i++) {
            rpp = (RPGPlayer) players[i];
            p = rpp.getPlayer();
            p.sendTitle(txt, txt2);
        }
    }
}
