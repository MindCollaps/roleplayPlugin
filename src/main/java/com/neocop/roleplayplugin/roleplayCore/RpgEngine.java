/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore;

import com.neocop.roleplayplugin.roleplayCore.abilitys.RPGAbility;
import com.neocop.roleplayplugin.roleplayCore.abilitys.detektiv;
import com.neocop.roleplayplugin.roleplayCore.abilitys.killer;
import com.neocop.roleplayplugin.roleplayCore.abilitys.villager;
import com.neocop.roleplayplugin.roleplayCore.roles.RPGRole;
import com.neocop.roleplayplugin.roleplayCore.roles.harterTyp;
import com.neocop.roleplayplugin.roleplayCore.roles.hacker;
import com.neocop.roleplayplugin.roleplayCore.roles.kpopFan;
import com.neocop.roleplayplugin.roleplayCore.roles.kpopStar;
import com.neocop.roleplayplugin.roleplayCore.roles.otaku;
import com.neocop.roleplayplugin.roleplayCore.roles.person;
import com.neocop.roleplayplugin.utils.Preferences;
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
import org.bukkit.scoreboard.Team;

/**
 *
 * @author Noah
 */
public class RpgEngine {

    public static HashMap<String, Player> onlinePlayer = new HashMap<>();
    public static HashMap<String, Player> rpgPlayer = new HashMap<>();
    public static HashMap<String, RPGPlayer> rpgRolePlayer = new HashMap<>();

    public static ArrayList<Player> allRpgPlayer = new ArrayList<>();

    public static ArrayList<RPGAbility> rpgAbilitys = new ArrayList<>();

    public static ArrayList<RPGRole> rpgRoles = new ArrayList<>();

    public static ArrayList<String> playersWhichHasVoted = new ArrayList<>();
    public static ArrayList<String> playerWhichIsVoted = new ArrayList<>();
    public static Objective timer = null;
    public static Team rpgSc = null;
    public static Team killerSc = null;
    public static Team extraSc = null;
    public static Score night = null;
    public static Score day = null;
    public static Score vote = null;
    public static Scoreboard board = null;
    public static Scoreboard badSc = null;

    public static HashMap<String, RPGPlayer> killerTeam = new HashMap<>();
    public static HashMap<String, RPGPlayer> extraVillager = new HashMap<>();
    public static HashMap<String, RPGPlayer> villagerTeam = new HashMap<>();

    public static ArrayList<String> killedPlayer = new ArrayList<>();

    public static boolean rpgRunning = false;
    public static boolean voteAllowed = false;
    public static int rounds = 0;
    public static World world;

    public static boolean killerKilled;

    public static int phase = 0;

    private static ArrayList randomRoleId = new ArrayList();

    public static void startRpg(Player player) {
        if (rpgRunning == false) {
            //init Scorboard
            addAbilitys();
            addRoles();
            try {
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                board = manager.getNewScoreboard();
                badSc = manager.getNewScoreboard();
            } catch (Exception e) {
                System.out.println(e);
            }

            Objective objective = board.registerNewObjective("timer", "timer");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName("Timer");
            RpgEngine.timer = objective;

            RpgEngine.night = RpgEngine.timer.getScore("Night: ");
            RpgEngine.day = RpgEngine.timer.getScore("Day: ");
            RpgEngine.vote = RpgEngine.timer.getScore("Vote: ");

            //rpg team
            rpgSc = board.registerNewTeam("rpg");
            rpgSc.setAllowFriendlyFire(false);
            rpgSc.setPrefix("§a[RPG] ");

            //killer team
            killerSc = badSc.registerNewTeam("killer");
            killerSc.setAllowFriendlyFire(false);
            killerSc.setPrefix("§c[RPG] ");

            extraSc = board.registerNewTeam("extra");
            extraSc.setAllowFriendlyFire(false);
            extraSc.setPrefix("§9[RPG] ");
            //score
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
                ArrayList<RPGAbility> useAbilitys = new ArrayList<>();

                for (int i = 1; i < rpgAbilitys.size(); i++) {
                    if (players.length >= rpgAbilitys.get(i).getNeededPlayers()) {
                        if (players.length > i) {
                            useAbilitys.add(rpgAbilitys.get(i));
                        } else {
                            break;
                        }
                    }
                }

                Player p = null;
                ArrayList randomPlayerId = new ArrayList();

                int rdm = 0;
                //rollen rdm zuweisen
                System.out.println("Value of roles which going to give away: " + useAbilitys.size());
                for (int i = 0; i < useAbilitys.size(); i++) {
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
                    System.out.println("Player " + p.getDisplayName() + " with id " + rdm + " gets role " + useAbilitys.get(i).getAbilityName());
                    rpgRolePlayer.put(p.getDisplayName(), new RPGPlayer(p, useAbilitys.get(i), getRandomRole()));
                }

                for (int i = 0; i < players.length; i++) {
                    p = (Player) players[i];
                    allRpgPlayer.add(p);
                    p.setScoreboard(board);
                    rpgSc.addPlayer(p);
                    if (!randomPlayerId.contains(i)) {
                        rpgRolePlayer.put(p.getDisplayName(), new RPGPlayer(p, rpgAbilitys.get(0), getRandomRole()));
                    }
                }
                rpgUtils.sendMessageToAllAliveRpgPlayer(Preferences.globalRules);
            } else {
                player.sendMessage(Preferences.notEnoughPlayerForRpg);
            }
        }
    }

    public static RPGRole getRandomRole() {
        System.out.println("----------------------");
        System.out.println("getRandomRole");
        int rdm = ThreadLocalRandom.current().nextInt(1, rpgRoles.size());
        System.out.println("rdm number: " + rdm);
        RPGRole role = null;
        for (int i = 0; i < 100; i++) {
            System.out.println("trie " + i);
            role = rpgRoles.get(rdm);
            System.out.println("get role " + role.getRoleName());
            System.out.println("index of rdm: " + randomRoleId.indexOf(rdm) + " max amount: " + role.getMaxAmount());
            if (role.getMaxAmount() < randomRoleId.indexOf(rdm)) {
                rdm = ThreadLocalRandom.current().nextInt(0, rpgRoles.size());
                System.out.println("needs new number!");
            } else {
                System.out.println("Funzt! Added " + rdm + "  " + role.getRoleName());
                randomRoleId.add(rdm);
                System.out.println("----------------------");
                return role;
            }
        }
        System.out.println("Retunrt Person!");
        System.out.println("----------------------");
        return rpgRoles.get(0);
    }

    public static void addAbilitys() {
        RpgEngine.rpgAbilitys.add(new RPGAbility(new villager(), "villager", 1, 0));
        for (int i = 0; i <= rpgPlayer.size(); i++) {
            RpgEngine.rpgAbilitys.add(new RPGAbility(new killer(), "killer", 0, 0));
            RpgEngine.rpgAbilitys.add(new RPGAbility(new detektiv(), "detektiv", 2, 2));
            i = i + 5;
        }
    }

    public static void addRoles() {
        rpgRoles.add(new RPGRole("person", new person(), -1));
        rpgRoles.add(new RPGRole("kpopfan", new kpopFan(), 1));
        rpgRoles.add(new RPGRole("kpopstar", new kpopStar(), 0));
        rpgRoles.add(new RPGRole("otaku", new otaku(), 1));
        rpgRoles.add(new RPGRole("hacker", new hacker(), 1));
        rpgRoles.add(new RPGRole("hartertyp", new harterTyp(), 1));
    }

    public static void nightRpg() {
        phase = 0;
        System.out.println(Preferences.consoleDes + "vote rpg");
        //Vote kill
        if (rounds > 0) {
            try {
                int votedPlayers = RpgEngine.playerWhichIsVoted.size();
                int value = RpgEngine.rpgRolePlayer.size() - 1;
                boolean someoneDied = false;
                System.out.println(Preferences.consoleDes + "Vote...needed value: " + value + " voted players: " + votedPlayers);
                for (int i = 0; i < votedPlayers; i++) {
                    int playerVoteValue = 0;
                    for (int j = 0; j < playerWhichIsVoted.size(); j++) {
                        if (playerWhichIsVoted.get(i).equals(playerWhichIsVoted.get(j))) {
                            playerVoteValue++;
                        }
                    }
                    if (playerVoteValue >= value) {
                        someoneDied = true;
                        rpgUtils.sendMessageToAllAliveRpgPlayer("§eSpieler §c" + RpgEngine.playerWhichIsVoted.get(i) + " §emuss sterben! Er hat " + playerVoteValue + " Stimmen");
                        killPlayer(rpgUtils.getRpgPlayerByName(RpgEngine.playerWhichIsVoted.get(i)), 0);
                        break;
                    }
                }

                if (!someoneDied) {
                    rpgUtils.sendMessageToAllAliveRpgPlayer(Preferences.globalNobodyDied);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            playerWhichIsVoted.clear();
            playersWhichHasVoted.clear();
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
                rpgRolePlayer.get(rpP.player.getDisplayName()).getAbility().getAbility().actionNight(rpP);
            }
        }
    }

    public static void dayRpg() {
        phase = 1;
        System.out.println(Preferences.consoleDes + "day rpg");
        Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
        Player p = null;
        RPGPlayer rpP = null;
        world.setTime(0);
        for (int i = 0; players.length > i; i++) {
            rpP = (RPGPlayer) players[i];
            rpP.getRole().getRole().resetNightsExtra();
            if (rpP.alive) {
                rpgRolePlayer.get(rpP.player.getDisplayName()).getAbility().getAbility().actionDay(rpP);
            }
        }
    }

    public static void voteRpg() {
        phase = 2;
        System.out.println(Preferences.consoleDes + "vote rpg");
        voteAllowed = true;
        rpgUtils.sendMessageToAllAliveRpgPlayer(Preferences.globalVoteBegin);
    }

    public static void stopRpg(Player player, boolean abort) {
        System.out.println(Preferences.consoleDes + "stop rpg");
        if (rpgRunning) {
            if (abort) {
                rpgUtils.sendMessageToAllRpgPlayer(Preferences.globalRpgGetCanceled);
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
            randomRoleId.clear();
            rpgRoles.clear();
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
                rpgUtils.sendMessageToAllAliveRpgPlayer(Preferences.globalRpgGetCanceled);
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
            randomRoleId.clear();
            rpgRoles.clear();
            Player p = null;
            for (int i = 0; i < allRpgPlayer.size(); i++) {
                p = allRpgPlayer.get(i);
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
        }
    }

    public static boolean analyzePlayer(Player act, Player analye) {
        Object[] players = villagerTeam.values().toArray();
        RPGPlayer rpp = null;
        for (int i = 0; players.length > i; i++) {
            rpp = (RPGPlayer) players[i];
            //is lieb
            if (rpp.getPlayer().getDisplayName().equalsIgnoreCase(analye.getDisplayName())) {
                return false;
            }
        }
        //is böse
        int num = ThreadLocalRandom.current().nextInt(0, 100);
        if (num <= Preferences.detectivSucc) {
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
        rpgPlayer.remove(died.getPlayer().getDisplayName());
        if (reason >= 0) {
            died.player.setGameMode(GameMode.SPECTATOR);
            if (reason == 0) {
                died.player.sendMessage(Preferences.playerDiedByVoteKill);
            } else if (reason == 1) {
                died.player.sendMessage(Preferences.playerDiedByOtherPlayer);
                rpgUtils.sendMessageToAllAliveRpgPlayer(Preferences.globalPlayerDiedByOtherPlayer);
            }
        }

        if (died.getAbility().getRoleTyp() == 0) {
            System.out.println("Found Killer");
            killerTeam.remove(died.getPlayer().getDisplayName());
        }
        if (died.getAbility().getRoleTyp() == 2) {
            System.out.println("Found extra");
            villagerTeam.remove(died.getPlayer().getDisplayName());
            extraVillager.remove(died.getPlayer().getDisplayName());
        }
        if (died.getAbility().getRoleTyp() == 1) {
            System.out.println("Found Villager");
            villagerTeam.remove(died.getPlayer().getDisplayName());
        }
    }

    public static void addRpgPlayer(Player player) {
        rpgPlayer.put(player.getDisplayName(), player);
    }

    public static void deleteRpgPlayer(Player player) {
        rpgPlayer.remove(player.getDisplayName());
    }
}
