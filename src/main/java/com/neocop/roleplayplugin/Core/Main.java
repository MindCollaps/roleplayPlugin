/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.Core;

import com.neocop.roleplayplugin.commands.CmdHelloWorld;
import com.neocop.roleplayplugin.commands.CmdRpg;
import com.neocop.roleplayplugin.commands.CmdRpgDetective;
import com.neocop.roleplayplugin.commands.CmdRpgVote;
import com.neocop.roleplayplugin.listener.rpgListener;
import com.neocop.roleplayplugin.listener.leaveJoinListener;
import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.RPGRole;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import static com.neocop.roleplayplugin.roleplayCore.RpgEngine.rpgRolePlayer;
import com.neocop.roleplayplugin.roleplayCore.roles.detektiv;
import com.neocop.roleplayplugin.roleplayCore.roles.killer;
import com.neocop.roleplayplugin.roleplayCore.roles.villager;
import com.neocop.roleplayplugin.utils.Preferences;
import com.neocop.roleplayplugin.utils.pluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Noah
 */
public class Main extends JavaPlugin {

    int repTaskId = 0;
    int dayTask = 0;
    int nightTask = 0;
    int voteTask = 0;
    int startTask = 0;
    int coutTask = 0;

    @Override
    public void onEnable() {
        getLogger().info("----------Roleplay plugin starts!----------");
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            RpgEngine.onlinePlayer.put(player.getDisplayName(), player);
        }
        addListeners();
        addCommands();
        addRoles();
        getLogger().info("----------Roleplay plugin is enabled now!----------");
    }

    @Override
    public void onDisable() {
        getLogger().info("----------Roleplay plugin is disabled now!----------");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean worked = true;
        try {
            commandHandler.handleCommand(commandHandler.commandParser(args, sender, label, command));
        } catch (Exception e) {
            System.out.println(e);
            worked = false;
        }
        if (worked) {
            if ("rpg".equals(label) && "start".equals(args[0]) && RpgEngine.rpgRunning) {
                roleplayCountdown(10, "§4§k12§r§aRoleplay Beginnt!§r§4§k12", "§4§k12§r§bRoleplay startet in §a", "§b!§r§4§k12");
            } else if ("rpg".equals(label) && "stop".equals(args[0]) && !RpgEngine.rpgRunning) {
                abortRoleplay();

            }
        }
        return worked;
    }

    public void addListeners() {
        getServer().getPluginManager().registerEvents(new leaveJoinListener(), this);
        getServer().getPluginManager().registerEvents(new rpgListener(), this);
    }

    public void addCommands() {
        commandHandler.commands.put("helloworld", new CmdHelloWorld());
        commandHandler.commands.put("rpg", new CmdRpg());
        commandHandler.commands.put("detectiv", new CmdRpgDetective());
        commandHandler.commands.put("vote", new CmdRpgVote());
        //commandHandler.commands.put("troll", new CmdTrole());
    }

    public void addRoles() {
        RpgEngine.rpgRoles.add(new RPGRole(new villager(), "villager", 1, 0));
        RpgEngine.rpgRoles.add(new RPGRole(new killer(), "killer", 0, 0));
        RpgEngine.rpgRoles.add(new RPGRole(new detektiv(), "deteltiv", 2, 2));
    }

    public void roleplayCountdown(final int countdown, final String endText, final String countText1, final String countText2) {
        repTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            int count = countdown;

            @Override
            public void run() {
                if (count <= 0) {
                    pluginUtils.sendMessageToAllAliveRpgPlayer(endText);
                    startRpgTimer();
                    Bukkit.getScheduler().cancelTask(repTaskId);
                    return;
                } else {
                    pluginUtils.sendMessageToAllAliveRpgPlayer(countText1 + count + countText2);
                    count--;
                }
            }
        }, 0, 20);
    }

    public void startRpgTimer() {
        Player p = null;
        Object[] playerz = RpgEngine.rpgRolePlayer.values().toArray();
        RPGPlayer rpP = null;
        for (int i = 0; playerz.length > i; i++) {
            rpP = (RPGPlayer) playerz[i];
            p = rpP.player;
            p.setGameMode(GameMode.ADVENTURE);
            rpgRolePlayer.get(p.getDisplayName()).getRole().getRole().start(rpP);
        }
        startTask = Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                nightTask();
                testScheudler();
                Bukkit.getScheduler().cancelTask(startTask);
            }
        }, 20 * 10);
    }
    
    public void testScheudler(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (!testRpgCont()) {
                    return;
                }
            }
        }, 0, 20*5);
    }

    public void nightTask() {
        RpgEngine.voteAllowed = false;
        RpgEngine.rounds++;
            RpgEngine.nightRpg();
        if (!testRpgCont()) {
            return;
        }
        countToTask(Preferences.nightsDuration, "night");
        nightTask = Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                dayTask();
                Bukkit.getScheduler().cancelTask(nightTask);
            }
        }, 20 * Preferences.nightsDuration);
    }

    public void dayTask() {
            RpgEngine.dayRpg();
        countToTask(Preferences.daysDuration, "day");
        dayTask = Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                voteTask();
                Bukkit.getScheduler().cancelTask(dayTask);
            }
        }, 20 * Preferences.daysDuration);
    }

    public void voteTask() {
            RpgEngine.voteRpg();
        countToTask(Preferences.voteDuration, "vote");
        voteTask = Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                nightTask();
                Bukkit.getScheduler().cancelTask(voteTask);
            }
        }, 20 * Preferences.voteDuration);
    }

    public void abortRoleplay() {
        Bukkit.getScheduler().cancelAllTasks();
    }

    public boolean testRpgCont() {
        if (RpgEngine.killerTeam.size() == 0) {
            pluginUtils.sendTitleToAllGoodRpgPlayer("§aGewonnen!", "§aDas Dorfteam gewinnt!");
            pluginUtils.sendTitleToAllBadRpgPlayer("§cVerloren!", "§cDas Dorfteam gewinnt!");
            pluginUtils.sendTitleToAllRpgPlayer("§aSpiel Beendet!", "Das Dorfteam gewinnt!");
            RpgEngine.stopRpg(false);
            abortRoleplay();
            return false;
        } else if (RpgEngine.villagerTeam.size() == 0) {
            pluginUtils.sendTitleToAllGoodRpgPlayer("§cVerloren!", "§cDas Killerteam gewinnt!");
            pluginUtils.sendTitleToAllBadRpgPlayer("§aGewonnen!", "§aDas Killerteam gewinnt!");
            pluginUtils.sendTitleToAllRpgPlayer("§aSpiel Beendet!", "Das Killerteam gewinnt!");
            RpgEngine.stopRpg(false);
            abortRoleplay();
            return false;
        } else {
            return true;
        }
    }

    public void countToTask(final int times, final String name) {
        System.out.println(Preferences.consoleDes + " started timer with name " + name + " with cout to " + times);
        coutTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            int rounds = times;
            int round = 0;
            int secounds = times;

            @Override
            public void run() {
                if (round >= rounds) {
                    Bukkit.getScheduler().cancelTask(coutTask);
                } else {
                    round++;
                    secounds--;
                    if (name.equalsIgnoreCase("day")) {
                        RpgEngine.day.setScore(secounds);                        
                    } else if (name.equalsIgnoreCase("night")) {
                        RpgEngine.night.setScore(secounds);
                    } else if (name.equalsIgnoreCase("vote")) {
                        RpgEngine.vote.setScore(secounds);
                    }
                }
            }
        }, 0, 20);
    }
}
