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
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
        rpgUtils.sendMessageToAllAliveRpgPlayer("§cVorzeitger Tod oder das Spiel wurde verlassen! Spieler wurde aus dem Spiel entfernt!");
    }

    public static boolean hasPermission(Player current, RPGPlayer rpp) {
        if (RpgEngine.rpgRunning) {
            try {
                if (rpgUtils.getRpgPlayerByName(current.getDisplayName()).isAlive()) {

                    return true;
                } else {
                    current.sendMessage(Preferences.noPermissionAlreadyDead);
                    return false;
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            current.sendMessage(Preferences.noRunningRpg);
            return false;
        }
        return false;
    }
    
    public static void playEffektToAllRpgPlayer(Location loc, int times, Effect ef){
        Object[] players = RpgEngine.rpgPlayer.values().toArray();
        Player p = null;
        for (int i = 0; i < players.length; i++) {
            p = (Player) players[i];
            p.playEffect(loc, ef, times);
        }
    }
    
    public static void sendMessageToAllAliveRpgPlayer(String txt) {
        Object[] players = RpgEngine.rpgPlayer.values().toArray();
        Player p = null;
        for (int i = 0; i < players.length; i++) {
            p = (Player) players[i];
            System.out.println("Send Message to " + p.getDisplayName());
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
        Object[] players = RpgEngine.villagerTeam.values().toArray();
        for (int i = 0; i < RpgEngine.villagerTeam.size(); i++) {
            rpp = (RPGPlayer) players[i];
            p = rpp.getPlayer();
            p.sendTitle(txt, txt2);
        }
    }
    
    public static void sendTitleToAllBadRpgPlayer(String txt, String txt2) {
        Player p = null;
        RPGPlayer rpp = null;
        Object[] players = RpgEngine.killerTeam.values().toArray();
        for (int i = 0; i < RpgEngine.killerTeam.size(); i++) {
            rpp = (RPGPlayer) players[i];
            p = rpp.getPlayer();
            p.sendTitle(txt, txt2);
        }
    }
    
    public static RPGPlayer getRPGPlayerByRoleName(String name) throws Exception{
        Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
        RPGPlayer player = null;
        for (int i = 0; i < players.length; i++) {
            player = (RPGPlayer) players[i];
            if(player.getRole().getRoleName().equalsIgnoreCase(name)){
                return player;
            }
        }
        throw new Exception("Role not found!");
    }
}
