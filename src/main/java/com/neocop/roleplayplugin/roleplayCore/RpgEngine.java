/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore;

import com.neocop.roleplayplugin.utils.Preferences;
import com.neocop.roleplayplugin.utils.pluginUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 *
 * @author Noah
 */
public class RpgEngine {

    public static HashMap<String, Player> onlinePlayer = new HashMap<>();
    public static HashMap<String, Player> rpgPlayer = new HashMap<>();
    public static HashMap<String, RPGPlayer> rpgRolePlayer = new HashMap<>();

    public static ArrayList<Player> allRpgPlayer = new ArrayList<>();

    public static ArrayList<RPGRole> rpgRoles = new ArrayList<>();

    public static ArrayList<String> playersWhichHasVoted = new ArrayList<>();
    public static ArrayList<String> playerWhichIsVoted = new ArrayList<>();
    public static Objective timer = null;
    public static Score night = null;
    public static Score day = null;
    public static Score vote = null;
    public static Scoreboard board = null;

    public static ArrayList<RPGPlayer> killerTeam = new ArrayList<>();
    public static ArrayList<RPGPlayer> extraVillager = new ArrayList<>();
    public static ArrayList<RPGPlayer> villagerTeam = new ArrayList<>();

    public static ArrayList<String> killedPlayer = new ArrayList<>();

    public static boolean rpgRunning = false;
    public static boolean voteAllowed = false;
    public static int rounds = 0;
    public static World world;

    public static void startRpg(Player player) {
        if (rpgRunning == false) {
            //init Scorboard
            System.out.println(Preferences.consoleDes + "Init Scoreboard");
            try {
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                board = manager.getNewScoreboard();
            } catch (Exception e) {
                System.out.println(e);
            }

            System.out.println(Preferences.consoleDes + "Init Objective");
            Objective objective = board.registerNewObjective("timer", "timer");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName("Timer");
            RpgEngine.timer = objective;

            System.out.println(Preferences.consoleDes + "Init Score");
            RpgEngine.night = RpgEngine.timer.getScore("Night: ");
            RpgEngine.day = RpgEngine.timer.getScore("Day: ");
            RpgEngine.vote = RpgEngine.timer.getScore("Vote: ");
            
            vote.setScore(0);
            day.setScore(0);
            night.setScore(0);
            
            System.out.println(Preferences.consoleDes + "start rpg");
            if (rpgPlayer.size() >= 3) {
                rpgRunning = true;
                System.out.println(Preferences.consoleDes + " Starts Roleplay");
                world = player.getWorld();
                world.setDifficulty(Difficulty.PEACEFUL);

                Object[] players = RpgEngine.rpgPlayer.values().toArray();

                //rollen die benutzt werden können
                ArrayList<RPGRole> useRoles = new ArrayList<>();

                for (int i = 1; i < rpgRoles.size(); i++) {
                    if (players.length >= rpgRoles.get(i).getNeededPlayers()) {
                        if (players.length > i) {
                            System.out.println("Add " + i + " " + rpgRoles.get(i).roleName);
                            useRoles.add(rpgRoles.get(i));
                        } else {
                            break;
                        }
                    }
                }

                Player p = null;
                ArrayList randomPlayerId = new ArrayList();
                int rdm = 0;

                System.out.println("Value of roles which going to give away: " + useRoles.size());
                for (int i = 0; i < useRoles.size(); i++) {
                    rdm = ThreadLocalRandom.current().nextInt(0, players.length);
                    if (randomPlayerId.contains(rdm)) {
                        for (int j = 0; j < 100; j++) {
                            rdm = ThreadLocalRandom.current().nextInt(0, players.length);
                            if (!randomPlayerId.contains(rdm)) {
                                break;
                            }
                        }
                    }
                    p = (Player) players[rdm];
                    randomPlayerId.add(rdm);
                    System.out.println("Player " + p.getDisplayName() + " with id " + rdm + " gets role " + useRoles.get(i).getRoleName());
                    rpgRolePlayer.put(p.getDisplayName(), new RPGPlayer(p, useRoles.get(i)));
                }

                for (int i = 0; i < players.length; i++) {
                    p = (Player) players[i];
                    allRpgPlayer.add(p);
                    p.setScoreboard(board);
                    if (!randomPlayerId.contains(i)) {
                        rpgRolePlayer.put(p.getDisplayName(), new RPGPlayer(p, rpgRoles.get(0)));
                    }
                }

            } else {
                player.sendMessage(Preferences.notEnoughPlayerForRpg);
            }
        }
    }

    public static void nightRpg() {
        System.out.println(Preferences.consoleDes + "vote rpg");
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
                        pluginUtils.sendMessageToAllAliveRpgPlayer("§eSpieler §c" + RpgEngine.playerWhichIsVoted.get(i) + " §emuss sterben! Er hat " + votes + "Stimmen");
                        killPlayer(rpgUtils.getRpgPlayerByName(RpgEngine.playerWhichIsVoted.get(i)), 0);
                        break;
                    }
                }
                if (!someoneDied) {
                    pluginUtils.sendMessageToAllAliveRpgPlayer(Preferences.globalNobodyDied);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println(Preferences.consoleDes + "night rpg");
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
        System.out.println(Preferences.consoleDes + "day rpg");
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
        System.out.println(Preferences.consoleDes + "vote rpg");
        voteAllowed = true;
        pluginUtils.sendMessageToAllAliveRpgPlayer(Preferences.globalVoteBegin);
    }

    public static void stopRpg(Player player, boolean abort) {
        System.out.println(Preferences.consoleDes + "stop rpg");
        if (rpgRunning) {
            if (abort) {
                pluginUtils.sendMessageToAllRpgPlayer(Preferences.globalRpgGetCanceled);
            }
            rounds = 0;
            rpgRunning = false;
            //clear Maps an Arrays
            rpgPlayer.clear();
            rpgRolePlayer.clear();
            killerTeam.clear();
            villagerTeam.clear();
            extraVillager.clear();
            board.clearSlot(DisplaySlot.SIDEBAR);
            board.clearSlot(DisplaySlot.PLAYER_LIST);
            board.clearSlot(DisplaySlot.SIDEBAR);
            Player p = null;
            for (int i = 0; i < allRpgPlayer.size(); i++) {
                p = allRpgPlayer.get(i);
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        } else {
            player.sendMessage(Preferences.noRunningRpg);
        }
    }

    public static void stopRpg(boolean abort) {
        System.out.println(Preferences.consoleDes + "stop rpg");
        if (rpgRunning) {
            if (abort) {
                pluginUtils.sendMessageToAllAliveRpgPlayer(Preferences.globalRpgGetCanceled);
            }
            rounds = 0;
            rpgRunning = false;
            //clear Maps an Arrays
            rpgPlayer.clear();
            rpgRolePlayer.clear();
            killerTeam.clear();
            villagerTeam.clear();
            extraVillager.clear();
            allRpgPlayer.clear();
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

    public static void killPlayer(RPGPlayer died, int reason) {
        System.out.println(Preferences.consoleDes + "kill rpg");
        System.out.println("Player " + died.player.getDisplayName() + " died!");
        killedPlayer.add(died.getPlayer().getDisplayName());
        rpgRolePlayer.remove(died.getPlayer().getDisplayName());
        if (reason >= 0) {
            died.player.setGameMode(GameMode.SPECTATOR);
            if (reason == 0) {
                died.player.sendMessage(Preferences.playerDiedByVoteKill);
            } else if (reason == 1) {
                died.player.sendMessage(Preferences.playerDiedByOtherPlayer);
                pluginUtils.sendMessageToAllAliveRpgPlayer(Preferences.globalPlayerDiedByOtherPlayer);
            }
        }
        if (died.getRole().getRoleTyp() == 0) {
            for (int i = 0; i < killerTeam.size(); i++) {
                if (killerTeam.get(i).getPlayer().getDisplayName().equalsIgnoreCase(died.getPlayer().getDisplayName())) {
                    killerTeam.remove(i);
                }
            }
        } else if (died.getRole().getRoleTyp() == 2) {
            for (int i = 0; i < extraVillager.size(); i++) {
                if (extraVillager.get(i).getPlayer().getDisplayName().equalsIgnoreCase(died.getPlayer().getDisplayName())) {
                    extraVillager.remove(i);
                }
            }
        } else if (died.getRole().getRoleTyp() == 1) {
            for (int i = 0; i < villagerTeam.size(); i++) {
                if (villagerTeam.get(i).getPlayer().getDisplayName().equalsIgnoreCase(died.getPlayer().getDisplayName())) {
                    villagerTeam.remove(i);
                }
            }
        }
    }

    public static void addRpgPlayer(Player player) {
        rpgPlayer.put(player.getDisplayName(), player);
    }

    public static void deleteRpgPlayer(Player player) {
        rpgPlayer.remove(player.getDisplayName());
    }
}
