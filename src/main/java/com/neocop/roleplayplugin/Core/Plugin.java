/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.Core;

import com.neocop.roleplayplugin.commands.CmdPort;
import com.neocop.roleplayplugin.commands.CmdRpg;
import com.neocop.roleplayplugin.commands.CmdRpgDetective;
import com.neocop.roleplayplugin.commands.CmdTrole;
import com.neocop.roleplayplugin.commands.CmdUtils;
import com.neocop.roleplayplugin.listener.rpgListener;
import com.neocop.roleplayplugin.listener.leaveJoinListener;
import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import static com.neocop.roleplayplugin.roleplayCore.RpgEngine.rpgRolePlayer;
import com.neocop.roleplayplugin.roleplayCore.rpgUtils;
import com.neocop.roleplayplugin.utils.Preferences;
import com.neocop.roleplayplugin.utils.pluginUtils;
import java.io.File;
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
public class Plugin extends JavaPlugin {
    
    int repTaskId = 0;
    int dayTask = 0;
    int nightTask = 0;
    int voteTask = 0;
    int startTask = 0;
    int coutTask = 0;
    int testTask = 0;
    int roleTask = 0;
    int portSaveTask = 0;
    
    @Override
    public void onEnable() {
        getLogger().info("----------Roleplay plugin starts!----------");
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            RpgEngine.onlinePlayer.put(player.getDisplayName(), player);
        }
        Preferences.filePath = new File(getDataFolder() + "").getAbsolutePath();
        addListeners();
        addCommands();
        CmdPort.loadPorts();
        startScheuduler();
        getLogger().info("----------Roleplay plugin is enabled now!----------");
    }
    
    @Override
    public void reloadConfig() {
        Bukkit.getScheduler().cancelTasks(this);
        RpgEngine.stopRpg(true);
        CmdPort.savePorts();
        getLogger().info("----------Roleplay plugin is disabled now!----------");
    }
    
    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        RpgEngine.stopRpg(true);
        CmdPort.savePorts();
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
        } else {
            if(args.length>=0){
             String argis = args[0];
            for (int i = 1; i < args.length; i++) {
                argis = argis + " " + args[i];
            }
            sender.sendMessage("§cEs trat ein Fehler während du den command §l/" + label + " " + argis + " §r§cauführen wolltest!");   
            } else {
                sender.sendMessage(command.getUsage());
            }
        }
        
        return true;
    }
    
    public void startScheuduler(){
        System.out.println("Starting NeoMC-Plugin Scheuduler!");
        portSaveTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("§7§oSaving Ports...");
                CmdPort.savePorts();
            }
        }, 0, 1200*5);
    }
    
    public void addListeners() {
        getServer().getPluginManager().registerEvents(new leaveJoinListener(), this);
        getServer().getPluginManager().registerEvents(new rpgListener(), this);
    }
    
    public void addCommands() {
        commandHandler.commands.put("rpg", new CmdRpg());
        commandHandler.commands.put("detectiv", new CmdRpgDetective());
        commandHandler.commands.put("util", new CmdUtils());
        commandHandler.commands.put("troll", new CmdTrole());
        commandHandler.commands.put("port", new CmdPort());
    }
    
    public void roleplayCountdown(final int countdown, final String endText, final String countText1, final String countText2) {
        repTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            int count = countdown;
            
            @Override
            public void run() {
                if (count <= 0) {
                    rpgUtils.sendMessageToAllAliveRpgPlayer(endText);
                    startRpgTimer();
                    Bukkit.getScheduler().cancelTask(repTaskId);
                    return;
                } else {
                    rpgUtils.sendMessageToAllAliveRpgPlayer(countText1 + count + countText2);
                    count--;
                }
            }
        }, 0, 20);
    }
    
    public void startRpgTimer() {
        System.out.println("Start Rpg Timer");
        Player p = null;
        Object[] playerz = RpgEngine.rpgRolePlayer.values().toArray();
        RPGPlayer rpP = null;
        for (int i = 0; playerz.length > i; i++) {
            rpP = (RPGPlayer) playerz[i];
            p = rpP.player;
            p.setGameMode(GameMode.ADVENTURE);
            rpgRolePlayer.get(p.getDisplayName()).getAbility().getAbility().start(rpP);
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
    
    
    private void testScheudler() {
        testTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (!testRpgCont()) {
                    Bukkit.getScheduler().cancelTask(testTask);
                }
            }
        }, 0, 20 * 5);
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
        RpgEngine.killerKilled = false;
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
        Bukkit.getScheduler().cancelTasks(this);
    }
    
    public boolean testRpgCont() {
        if (RpgEngine.killerTeam.size() == 0) {
            rpgUtils.sendTitleToAllRpgPlayer("§aSpiel Beendet!", "Das Dorfteam gewinnt!");
            RpgEngine.stopRpg(false);
            abortRoleplay();
            return false;
        } else if (RpgEngine.villagerTeam.size() == 0) {
            rpgUtils.sendTitleToAllRpgPlayer("§aSpiel Beendet!", "Das Killerteam gewinnt!");
            RpgEngine.stopRpg(false);
            abortRoleplay();
            return false;
        } else {
            return true;
        }
    }
    
    public void countToTask(final int times, final String name) {
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
