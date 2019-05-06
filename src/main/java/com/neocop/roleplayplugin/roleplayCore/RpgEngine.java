/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore;

import com.neocop.roleplayplugin.utils.Preferences;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class RpgEngine {

    public static HashMap<String, Player> onlinePlayer = new HashMap<>();
    public static HashMap<String, Player> rpgPlayer = new HashMap<>();
    public static HashMap<String, RPGPlayer> rpgRolePlayer = new HashMap<>();

    public static ArrayList<RPGRole> rpgRoles = new ArrayList<>();

    public static ArrayList<String> playersWhichHasVoted = new ArrayList<>();
    public static ArrayList<String> playerWhichIsVoted = new ArrayList<>();

    public static ArrayList<RPGPlayer> killer = new ArrayList<>();
    public static ArrayList<RPGPlayer> detective = new ArrayList<>();
    public static ArrayList<RPGPlayer> villagerTeam = new ArrayList<>();

    public static ArrayList<String> killedPlayer = new ArrayList<>();

    public static boolean rpgRunning = false;
    public static boolean voteAllowed = false;
    public static int rounds = 0;
    public static World world;

    public static void startRpg(Player player) {
        if (rpgPlayer.size() >= 3) {
            rpgRunning = true;

            world = player.getWorld();

            Object[] players = RpgEngine.rpgPlayer.values().toArray();

            //rollen die benutzt werden können
            ArrayList<RPGRole> useRoles = new ArrayList<>();

            for (int i = 1; i < rpgRoles.size(); i++) {
                if (players.length >= rpgRoles.get(i).getNeededPlayers()) {
                    if (players.length > i +1) {
                        useRoles.add(rpgRoles.get(i));
                    } else {
                        break;
                    }
                }
            }

            Player p = null;
            ArrayList randomRoles = new ArrayList();
            for (int i = 0; i < players.length; i++) {
                p = (Player) players[i];
                if (useRoles.size() <= randomRoles.size()) {
                    for (int j = 0; j < useRoles.size(); j++) {
                        int num = ThreadLocalRandom.current().nextInt(0, useRoles.size());
                        try {
                            for(int k = 0; k < 100; k++) {
                                if (randomRoles.contains(num)) {
                                    num = ThreadLocalRandom.current().nextInt(0, useRoles.size());
                                } else {
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        randomRoles.add(num);
                        rpgRolePlayer.put(p.getDisplayName(), new RPGPlayer(p, useRoles.get(num)));
                    }
                } else {
                    rpgRolePlayer.put(p.getDisplayName(), new RPGPlayer(p, rpgRoles.get(0)));
                }
            }
        } else {
            player.sendMessage(Preferences.notEnoughPlayerForRpg);
        }
    }

    public static void nightRpg() {
        //Vote kill
        if (rounds > 0) {
            try {
                int votedPlayers = RpgEngine.playerWhichIsVoted.size();
                double value = RpgEngine.rpgRolePlayer.size() / 4 * 3;
                boolean someoneDied = false;
                for (int i = 0; i < RpgEngine.playerWhichIsVoted.size(); i++) {
                    int votes = RpgEngine.playerWhichIsVoted.indexOf(RpgEngine.playerWhichIsVoted.get(i));
                    if (votes >= value) {
                        someoneDied = true;
                        int procentqValue = votedPlayers / 100 * votes;
                        int procent = procentqValue * 100;
                        Bukkit.broadcast("§eSpieler " + RpgEngine.playerWhichIsVoted.get(i) + " muss sterben! Er hat " + procent + "% der Stimmen", null);
                        killPlayer(rpgUtils.getRpgPlayerByName(RpgEngine.playerWhichIsVoted.get(i)), voteAllowed);
                        break;
                    }
                }
                if (!someoneDied) {
                    Bukkit.broadcast(Preferences.globalNobodyDied, null);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        //night
        Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
        Player p = null;
        RPGPlayer rpP = null;
        world.setTime(18000);
        for (int i = 0; players.length > i; i++) {
            rpP = (RPGPlayer) players[i];
            if (rpP.alive) {
                rpgRolePlayer.get(rpP.player.getDisplayName()).getRole().getRole().actionNight(rpP);
            }
        }
    }

    public static void dayRpg() {
        Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
        Player p = null;
        RPGPlayer rpP = null;
        world.setTime(0);
        for (int i = 0; players.length > i; i++) {
            rpP = (RPGPlayer) players[i];
            if (rpP.alive) {
                rpgRolePlayer.get(rpP.player.getDisplayName()).getRole().getRole().actionDay(rpP);
            }
        }
    }

    public static void voteRpg() {
        voteAllowed = true;
        Bukkit.broadcast(Preferences.globalVoteBegin, null);
    }

    public static void stopRpg(Player player, boolean abort) {
        if (rpgRunning) {
            if (abort) {
                Bukkit.broadcast(Preferences.globalRpgGetCanceled, null);
            }
            rounds = 0;
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

    public static void stopRpg(boolean abort) {
        if (rpgRunning) {
            if (abort) {
                Bukkit.broadcast(Preferences.globalRpgGetCanceled, null);
            }
            rounds = 0;
            rpgRunning = false;
            //clear Maps an Arrays
            rpgPlayer.clear();
            rpgRolePlayer.clear();
            killer.clear();
            villagerTeam.clear();
            detective.clear();

        }
    }

    public static boolean analyzePlayer(Player act, Player analye) {
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

    public static void killPlayer(RPGPlayer died, boolean voteKill) {
        System.out.println("Player " + died.player.getDisplayName() + " died!");
        killedPlayer.add(died.getPlayer().getDisplayName());
        rpgRolePlayer.remove(died.getPlayer().getDisplayName());
        rpgPlayer.remove(died.getPlayer().getDisplayName());
        died.player.setGameMode(GameMode.SPECTATOR);
        if (voteKill) {
            died.player.sendMessage(Preferences.playerDiedByVoteKill);
        } else {
            died.player.sendMessage(Preferences.playerDiedByOtherPlayer);
            Bukkit.broadcast(Preferences.globalPlayerDiedByOtherPlayer, null);
        }
        died.player.setHealth(0.0D);
    }

    public static void addRpgPlayer(Player player) {
        rpgPlayer.put(player.getDisplayName(), player);
    }

    public static void deleteRpgPlayer(Player player) {
        rpgPlayer.remove(player.getDisplayName());
    }
}
