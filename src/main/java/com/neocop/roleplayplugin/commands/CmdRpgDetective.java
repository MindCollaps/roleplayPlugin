/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.commands;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
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
        RPGPlayer rpp = RpgEngine.extraVillager.get(current.getDisplayName());
        if (rpgUtils.hasPermission(current, rpp)) {
            if (rpp.getAbility().getAbilityName().equalsIgnoreCase("detektiv")) {
                if (rpp.getAbility().getData() <= 2) {
                    RpgEngine.extraVillager.get(current.getDisplayName()).getAbility().setData(rpp.getAbility().getData() + 1);
                    return true;
                } else {
                    sender.sendMessage("§cDu hast bereits alle Lügendetektoren aufgebraucht!");
                    return false;
                }
            } else {
                sender.sendMessage(Preferences.noPermissionDetectiveRoleNeeded);
                return false;
            }
        }
        return false;
    }

    @Override
    public void actionUser(String[] args, CommandSender sender, Command command) {
        Player current = (Player) sender;
        if (args.length > 0) {
            switch (args[0]) {
                case Preferences.commandModDetectiveAnalyze:
                    Inventory inv = Bukkit.createInventory(null, 9 * 3, "§9Detectiv");

                    ItemStack skull = new ItemStack(Material.SKELETON_SKULL,1, (short) 3);
                    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

                    Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
                    Player p = null;
                    RPGPlayer rpp = null;
                    int count = 9;
                    for (int i = 0; players.length > i; i++) {
                        rpp = (RPGPlayer) players[i];
                        p = rpp.player;
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
    }

    @Override
    public void actionServer(String[] args, CommandSender sender, Command command) {
        System.out.println(Preferences.noPermissionCommandOnlyForUser);
    }

}
