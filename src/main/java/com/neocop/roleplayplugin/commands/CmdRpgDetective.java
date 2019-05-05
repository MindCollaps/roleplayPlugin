/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.commands;

import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.roleplayCore.rpgUtils;
import com.neocop.roleplayplugin.utils.Preferences;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author Noah
 */
public class CmdRpgDetective implements IntCommand {
    
    @Override
    public boolean calledUser(String[] args, CommandSender sender, Command command) {
        Player current = (Player) sender;
        try {
            if (RpgEngine.rpgRunning) {
                if (rpgUtils.getRpgPlayerByName(current.getDisplayName()).isAlive()) {
                    for (int i = 0; RpgEngine.detective.size() > i; i++) {
                        if (RpgEngine.detective.get(i).getPlayer().getDisplayName().equalsIgnoreCase(current.getDisplayName())) {
                            return true;
                        }
                    }
                    sender.sendMessage(Preferences.noPermissionDetectiveRoleNeeded);
                } else {
                    sender.sendMessage(Preferences.noPermissionAlreadyDead);
                }
            } else {
                sender.sendMessage(Preferences.noRunningRpg);
                return false;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            current.sendMessage("§cError!");
        }
        sender.sendMessage(Preferences.noPermissionDetectiveRoleNeeded);
        return false;
    }
    
    @Override
    public void actionUser(String[] args, CommandSender sender, Command command) {
        Player current = (Player) sender;
        switch (args[0]) {
            case Preferences.commandModDetectiveAnalyze:
                Inventory inv = Bukkit.createInventory(null, 9 * 3, "§9Detectiv");
                
                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                
                Object[] players = RpgEngine.rpgPlayer.values().toArray();
                Player p = null;
                int count = 9;
                for (int i = 0; players.length > i; i++) {
                    p = (Player) players[i];
                    count++;
                    if (p.getDisplayName().equalsIgnoreCase(current.getName())) {
                        count--;
                    } else {
                        skullMeta.setOwner(p.getDisplayName());
                        skullMeta.setDisplayName(p.getDisplayName());
                        skull.setItemMeta(skullMeta);
                        inv.setItem(count, skull);
                    }
                }
                current.openInventory(inv);
                break;
            
            default:
            case "help":
                current.sendMessage(Preferences.helpDetectivCommon);
                break;
        }
    }
    
    @Override
    public void actionServer(String[] args, CommandSender sender, Command command) {
        System.out.println(Preferences.noPermissionCommandOnlyForUser);
    }
    
}
