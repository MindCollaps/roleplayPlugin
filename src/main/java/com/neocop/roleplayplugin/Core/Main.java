/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.Core;

import com.neocop.roleplayplugin.commands.CmdHelloWorld;
import com.neocop.roleplayplugin.commands.CmdRpg;
import com.neocop.roleplayplugin.commands.CmdDetectiv;
import com.neocop.roleplayplugin.listener.rpgListener;
import com.neocop.roleplayplugin.listener.leaveJoinListener;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Noah
 */
public class Main extends JavaPlugin {

    private int taskID = 0;

    @Override
    public void onEnable() {
        getLogger().info("----------Roleplay plugin starts!----------");
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            RpgEngine.onlinePlayer.put(player.getDisplayName(), player);
        }
        addListeners();
        addCommands();
        getLogger().info("----------Roleplay plugin is enabled now!----------");
    }

    @Override
    public void onDisable() {
        getLogger().info("----------\nRoleplay plugin is disabled now!\n----------");
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
        commandHandler.commands.put("detectiv", new CmdDetectiv());
    }

    public void sendConsoleMessage(String text) {
        getLogger().info(text);
    }

    public void roleplayCountdown(final int countdown, final String endText, final String countText1, final String countText2) {
        System.out.println("[Poleplay Plugin]Starts Roleplay");
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            int count = countdown;

            @Override
            public void run() {
                if (count <= 0) {
                    Bukkit.broadcastMessage(endText);
                    Bukkit.getScheduler().cancelTask(taskID);
                } else {
                 Bukkit.broadcastMessage(countText1 + count + countText2);
                count--;   
                }
            }
        }, 0, 20);
    }
}
