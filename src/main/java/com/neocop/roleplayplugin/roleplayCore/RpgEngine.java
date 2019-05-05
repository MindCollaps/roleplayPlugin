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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class RpgEngine {

    public static HashMap<String, Player> onlinePlayer = new HashMap<>();
    public static HashMap<String, Player> rpgPlayer = new HashMap<>();
    public static HashMap<String, RPGPlayer> rpgRolePlayer = new HashMap<>();

    public static ArrayList<String> playersWhichHasVoted = new ArrayList<>();
    public static ArrayList<String> playerWhichIsVoted = new ArrayList<>();

    public static ArrayList<RPGPlayer> killer = new ArrayList<>();
    public static ArrayList<RPGPlayer> detective = new ArrayList<>();
    public static ArrayList<RPGPlayer> villagerTeam = new ArrayList<>();
    
    public static ArrayList<String> killedPlayer = new ArrayList<>();

    public static boolean rpgRunning = false;
    public static boolean voteAllowed = false;
    public static int rounds = 0;

    public static void startRpg(Player player) {
        if (rpgPlayer.size() >= 3) {
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
                        break;
                    }
                }
            }
            Player p = null;
            for (int i = 0; players.length > i; i++) {
                p = (Player) players[i];
                if (i == killerNum) {
                    rpgRolePlayer.put(player.getDisplayName(), new RPGPlayer(player, new killer(), "killer"));
                } else if (i == detektivNum) {
                    rpgRolePlayer.put(player.getDisplayName(), new RPGPlayer(player, new detektiv(), "detektiv"));
                } else {
                    rpgRolePlayer.put(player.getDisplayName(), new RPGPlayer(player, new villager(), "villager"));
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
                        rpgRolePlayer.get(p.getDisplayName()).role.start(rpP);
                    }
                }
            }, 11000);
            
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Bukkit.broadcast(Preferences.globalRules, null);
                }
            }, 20000);
            
            final Player playerForTimer = player;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    ccNight(playerForTimer);
                }
            }, 25000);
        } else {
            player.sendMessage(Preferences.notEnoughPlayerForRpg);
        }
    }

    public static void nightRpg(CommandSender senderForCmd) {
        //Vote kill
        try{
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
        } catch (Exception e){
            System.out.println(e);
        }

        //night
        Bukkit.dispatchCommand(senderForCmd, "/time set night");
        Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
        Player p = null;
        RPGPlayer rpP = null;
        for (int i = 0; players.length > i; i++) {
            rpP = (RPGPlayer) players[i];
            if (rpP.alive) {
                rpgRolePlayer.get(rpP.player.getDisplayName()).role.actionNight(rpP);
            }
        }
    }

    public static void dayRpg(CommandSender senderForCmd) {
        Bukkit.dispatchCommand(senderForCmd, "/time set day");
        Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
        Player p = null;
        RPGPlayer rpP = null;
        for (int i = 0; players.length > i; i++) {
            rpP = (RPGPlayer) players[i];
            if (rpP.alive) {
                rpgRolePlayer.get(rpP.player.getDisplayName()).role.actionDay(rpP);
            }
        }
    }

    public static void voteRpg(CommandSender senderForCmd) {
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

    public static void ccDay(CommandSender senderForCmd) {
        final CommandSender s = senderForCmd;
        if (rounds >= Preferences.roundsValue) {
            stopRpg((Player) s, false);
            return;
        } else if (villagerTeam.size() <= 1) {
            stopRpg((Player) s, false);
            return;
        } else {
            dayRpg(s);
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ccVote(s);
            }
        }, 10 * Preferences.daysDuration);
    }

    private static void ccNight(CommandSender senderForCmd) {
        //undo change from ccVote
        voteAllowed = false;

        rounds++;
        final CommandSender s = senderForCmd;
        nightRpg(s);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ccDay(s);
            }
        }, 10 * Preferences.nightsDuration);
    }

    private static void ccVote(CommandSender senderForCmd) {
        final CommandSender s = senderForCmd;
        voteRpg(s);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ccNight(s);
            }
        }, 10 * Preferences.voteDuration);
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
        System.out.println("§ePlayer " + died.player.getDisplayName() + " died!");
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
