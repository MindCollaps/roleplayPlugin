/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore;

import com.neocop.roleplayplugin.roleplayCore.roles.detektiv;
import com.neocop.roleplayplugin.roleplayCore.roles.killer;
import com.neocop.roleplayplugin.roleplayCore.roles.villager;
import com.neocop.roleplayplugin.utils.Preferences;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class RpgEngine {

    public static boolean rpgRunning;
    public static HashMap<String, Player> onlinePlayer = new HashMap<>();
    public static HashMap<String, Player> rpgPlayer = new HashMap<>();
    public static HashMap<String, RPGPlayer> rpgRolePlayer = new HashMap<>();

    public static ArrayList<RPGPlayer> killer = new ArrayList<>();
    public static ArrayList<RPGPlayer> detective = new ArrayList<>();
    public static ArrayList<RPGPlayer> villagerTeam = new ArrayList<>();

    public static void startRpg(Player player) {
        //if (rpgPlayer.size() >= 3) { 
        rpgRunning = true;

        Object[] players = RpgEngine.rpgPlayer.values().toArray();
        int killerNum = ThreadLocalRandom.current().nextInt(0, players.length);
        int detektivNum = -400;
        if (players.length >= 4) {
            System.out.println("[Roleplay Plugin]Detective Enabled");
            detektivNum = ThreadLocalRandom.current().nextInt(0, players.length);
            while (true) {
                if (detektivNum == killerNum) {
                    detektivNum = ThreadLocalRandom.current().nextInt(0, players.length);
                } else {
                    return;
                }
            }
        }
        Player p = null;
        for (int i = 0; players.length > i; i++) {
            p = (Player) players[i];
            if (i == killerNum) {
                rpgRolePlayer.put(player.getDisplayName(), new RPGPlayer(player, new killer()));
            } else if (i == detektivNum) {
                rpgRolePlayer.put(player.getDisplayName(), new RPGPlayer(player, new detektiv()));
            } else {
                rpgRolePlayer.put(player.getDisplayName(), new RPGPlayer(player, new villager()));
            }
        }
        //start roleplay
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Player p = null;
                Object[] playerz = RpgEngine.rpgRolePlayer.values().toArray();
                RPGPlayer rpP = null;
                for (int i = 0; playerz.length > i; i++) {
                    rpP = (RPGPlayer) playerz[i];
                    p = rpP.player;
                    p.setGameMode(GameMode.SURVIVAL);
                    rpgRolePlayer.get(p.getDisplayName()).role.start(p);
                }
            }
        }, 10600);
        //} else {
        //   player.sendMessage(Preferences.notEnoughPlayerForRpg);
        // }
    }

    public static void nightRpg() {
        Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
        Player p = null;
        RPGPlayer rpP = null;
        for (int i = 0; players.length > i; i++) {
            rpP = (RPGPlayer) players[i];
            p = rpP.player;
            rpgRolePlayer.get(p.getDisplayName()).role.actionNight(p);
        }
    }

    public static void dayRpg() {
        Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
        Player p = null;
        RPGPlayer rpP = null;
        for (int i = 0; players.length > i; i++) {
            rpP = (RPGPlayer) players[i];
            p = rpP.player;
            rpgRolePlayer.get(p.getDisplayName()).role.actionDay(p);
        }
    }

    public static void stopRpg(Player player, boolean abort) {
        if(rpgRunning){
        if (abort) {
            Bukkit.broadcast(Preferences.globalRpgGetCanceled, null);
        }
        rpgRunning = false;
        //clear Maps an Arrays
        rpgPlayer.clear();
        rpgRolePlayer.clear();
        killer.clear();
        villagerTeam.clear();
        detective.clear();
        } else {
            player.sendMessage(Preferences.noRunningRpg);
        }
    }

    public static boolean analyzePlayer(Player act, Player analye) {
        //TODO: test with instanceof
        for (int i = 0; villagerTeam.size() > i; i++) {
            //is lieb
            if (villagerTeam.get(i).player.getDisplayName().equalsIgnoreCase(analye.getDisplayName())) {
                return false;
            }
        }
        //is böse
        int num = ThreadLocalRandom.current().nextInt(0, 100);
        if (num >= 60) {
            return true;
        } else {
            //gibt aber falschen wert
            return false;
        }
    }

    public static void killPlayer(Player died, Player murder) {
        System.out.println("Player " + died.getDisplayName() + " died!");
        died.setHealth(0.0D);
        died.sendMessage(Preferences.playerDiedByOtherPlayer);
        died.setGameMode(GameMode.SPECTATOR);
        Bukkit.broadcast(Preferences.globalPlayerDiedByOtherPlayer, null);
    }

    public static void addRpgPlayer(Player player) {
        rpgPlayer.put(player.getDisplayName(), player);
    }

    public static void deleteRpgPlayer(Player player) {
        rpgPlayer.remove(player.getDisplayName());
    }
}
