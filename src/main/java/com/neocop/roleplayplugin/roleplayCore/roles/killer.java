/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.roles;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.utils.Preferences;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author Noah
 */
public class killer implements RPGRole {

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendTitle("Deine Rolle ist", "§cMurder!");
        player.getPlayer().sendMessage(Preferences.killer);
        RpgEngine.killer.add(player);
    }

    @Override
    public void actionNight(RPGPlayer player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 3, "§cKiller");

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        Object[] players = RpgEngine.rpgPlayer.values().toArray();
        Player p = null;
        int count = 9;
        for (int i = 0; players.length > i; i++) {
            p = (Player) players[i];
            count++;
            if(p.getDisplayName().equalsIgnoreCase(player.getPlayer().getName())){
                count--;
            }
            skullMeta.setOwner(p.getDisplayName());
            skullMeta.setDisplayName(p.getDisplayName());
            skull.setItemMeta(skullMeta);
            inv.setItem(count, skull);
        }
        player.getPlayer().openInventory(inv);
        player.getPlayer().sendMessage(Preferences.rpgKillerNightAction);
    }

    @Override
    public void actionDay(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgKillerDayAction);
    }
}
