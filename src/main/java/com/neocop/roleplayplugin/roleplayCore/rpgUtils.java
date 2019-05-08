/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore;

import static com.neocop.roleplayplugin.roleplayCore.RpgEngine.killedPlayer;
import static com.neocop.roleplayplugin.roleplayCore.RpgEngine.rpgPlayer;
import static com.neocop.roleplayplugin.roleplayCore.RpgEngine.rpgRolePlayer;
import com.neocop.roleplayplugin.utils.Preferences;
import com.neocop.roleplayplugin.utils.pluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class rpgUtils {

    public static RPGPlayer getRpgPlayerByName(String userName) throws Exception {
        Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
        Player p = null;
        RPGPlayer rpP = null;
        for (int i = 0; players.length > i; i++) {
            rpP = (RPGPlayer) players[i];
            p = rpP.player;
            if (p.getDisplayName().equalsIgnoreCase(userName)) {
                return (RPGPlayer) players[i];
            }
        }
        throw new Exception("User " + userName + " cant found!");
    }

    public static void playerDiedOrLeaveWithoutExpection(Player died) {
        System.out.println(Preferences.consoleDes + " rpg player died or leave without killer or vote!");
        killedPlayer.add(died.getDisplayName());
        rpgRolePlayer.remove(died.getDisplayName());
        rpgPlayer.remove(died.getDisplayName());
        died.setGameMode(GameMode.SPECTATOR);
        pluginUtils.sendMessageToAllAliveRpgPlayer("§cVorzeitger Tod oder das Spiel wurde verlassen! Spieler wurde aus dem Spiel entfernt!");
    }
}
