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
import com.neocop.roleplayplugin.roleplayCore.RPGRole;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.roleplayCore.Threads.RpgTimerThread;
import com.neocop.roleplayplugin.roleplayCore.roles.detektiv;
import com.neocop.roleplayplugin.roleplayCore.roles.killer;
import com.neocop.roleplayplugin.roleplayCore.roles.villager;
import com.neocop.roleplayplugin.utils.Preferences;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

    public void addRoles(){
        RpgEngine.rpgRoles.add(new RPGRole(new killer(), "killer", 0, 0));
        RpgEngine.rpgRoles.add(new RPGRole(new detektiv(), "deteltiv", 1, 4));
        RpgEngine.rpgRoles.add(new RPGRole(new villager(), "villager", 1, 0));
    }

    public void roleplayCountdown(final int countdown, final String endText, final String countText1, final String countText2) {
        System.out.println(Preferences.consoleDes + " Starts Roleplay");
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            int count = countdown;

            @Override
            public void run() {
                if (count <= 0) {
                    Bukkit.broadcastMessage(endText);
                    new RpgTimerThread().run();
                    Bukkit.getScheduler().cancelTask(taskID);
                } else {
                 Bukkit.broadcastMessage(countText1 + count + countText2);
                count--;
                }
            }
        }, 0, 20);
    }
}
